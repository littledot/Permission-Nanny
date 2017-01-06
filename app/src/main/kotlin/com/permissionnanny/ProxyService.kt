package com.permissionnanny

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.util.ArrayMap
import com.permissionnanny.common.BundleUtil
import com.permissionnanny.data.OngoingRequestDB
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.simple.AccountRequest
import com.permissionnanny.lib.request.simple.LocationRequest
import com.permissionnanny.simple.*
import timber.log.Timber
import java.security.SecureRandom
import javax.inject.Inject

/**

 */
class ProxyService : BaseService() {

    private val clients = ArrayMap<String, ProxyClient>()
    private val ackReceiver = AckReceiver()
    var ackAddress: String? = null
        private set

    @Inject internal lateinit var db: OngoingRequestDB

    override fun onCreate() {
        super.onCreate()
        getComponent().inject(this)
        ackAddress = java.lang.Long.toString(SecureRandom().nextLong())
        registerReceiver(ackReceiver, IntentFilter(ackAddress))
        Timber.wtf("init service")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent == null) { // Service killed by OS? Restore client state
            restoreState()
            return super.onStartCommand(intent, flags, startId)
        }
        Timber.wtf("Server started with args: " + BundleUtil.toString(intent))
        val clientId = intent.getStringExtra(CLIENT_ADDR)
        val requestParams = intent.getParcelableExtra<RequestParams>(REQUEST_PARAMS)
        handleRequest(clientId, requestParams, true)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(ackReceiver)
    }

    private fun restoreState() {
        val count = 0
        val requests = db.ongoingRequests
        var i = 0
        val len = requests.size
        while (i < len) {
            handleRequest(requests.keyAt(i), requests.valueAt(i), false)
            i++
        }
        Timber.wtf("restored $count clients")
    }

    /**
     * @param clientAddr    Client address
     * *
     * @param requestParams Client request
     * *
     * @param cacheRequest  Flag that controls caching request to disk
     */
    private fun handleRequest(clientAddr: String, requestParams: RequestParams, cacheRequest: Boolean) {
        Timber.wtf("handling client=$clientAddr req=$requestParams")
        val listener: ProxyListener<*>
        when (requestParams.opCode) {
            AccountRequest.ADD_ON_ACCOUNTS_UPDATED_LISTENER -> listener = ProxyOnAccountsUpdateListener<Any>(this, clientAddr)
            AccountRequest.GET_ACCOUNTS_BY_TYPE_AND_FEATURES -> listener = ProxyAccountManagerListener.GetAccountsByTypeAndFeatures(this, clientAddr)
            AccountRequest.GET_AUTH_TOKEN1 -> listener = ProxyAccountManagerListener.GetAuthToken1(this, clientAddr)
            AccountRequest.GET_AUTH_TOKEN2 -> listener = ProxyAccountManagerListener.GetAuthToken2(this, clientAddr)
            AccountRequest.HAS_FEATURES -> listener = ProxyAccountManagerListener.HasFeatures(this, clientAddr)
            AccountRequest.REMOVE_ACCOUNT -> listener = ProxyAccountManagerListener.RemoveAccount(this, clientAddr)
            AccountRequest.RENAME_ACCOUNT -> listener = ProxyAccountManagerListener.RenameAccount(this, clientAddr)
            LocationRequest.ADD_GPS_STATUS_LISTENER -> listener = ProxyGpsStatusListener<Any>(this, clientAddr)
            LocationRequest.ADD_NMEA_LISTENER -> listener = ProxyNmeaListener<Any>(this, clientAddr)
            LocationRequest.REQUEST_LOCATION_UPDATES1 -> listener = RequestLocationUpdatesListener.Api1(this, clientAddr)
            LocationRequest.REQUEST_LOCATION_UPDATES2 -> listener = RequestLocationUpdatesListener.Api2(this, clientAddr)
            LocationRequest.REQUEST_SINGLE_UPDATE -> listener = RequestSingleUpdateListener.Api(this, clientAddr)
            LocationRequest.REQUEST_SINGLE_UPDATE1 -> listener = RequestSingleUpdateListener.Api1(this, clientAddr)
            else -> throw UnsupportedOperationException("Unsupported opcode " + requestParams.opCode)
        }

        val response = startRequest(clientAddr, requestParams, listener, cacheRequest)
        val intent = Intent(clientAddr).putExtras(response)
        sendBroadcast(intent)
    }

    /**
     * @param clientAddr   Client address
     * *
     * @param params       Client request
     * *
     * @param listener
     * *
     * @param cacheRequest `true` to cache request to disk after registration succeeds
     * *
     * @return Response bundle to return to the client
     */
    private fun startRequest(clientAddr: String, params: RequestParams, listener: ProxyListener<*>, cacheRequest: Boolean): Bundle {
        try {
            listener.register(this, params)
        } catch (error: Throwable) {
            return ResponseFactory.newBadRequestResponse(Nanny.AUTHORIZATION_SERVICE, error).build()
        }

        // Good request? Cache request to memory and disk
        clients.put(clientAddr, ProxyClient(clientAddr, params, listener))
        if (cacheRequest) {
            db.putOngoingRequest(clientAddr, params)
        }
        return ResponseFactory.newAllowResponse(Nanny.AUTHORIZATION_SERVICE).build()
    }

    fun removeProxyClient(clientAddr: String) {
        clients.remove(clientAddr)
        db.delOngoingRequest(clientAddr)
        if (clients.isEmpty) { // no more clients? kill service
            stopSelf()
        }
    }

    internal inner class AckReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // TODO: validate
            val clientAddr = intent.getStringExtra(Nanny.CLIENT_ADDRESS)
            val client = clients[clientAddr]
            if (client != null) {
                client.listener.updateAck(SystemClock.elapsedRealtime())
            }
        }
    }

    companion object {

        val CLIENT_ADDR = "clientAddr"
        val REQUEST_PARAMS = "requestParams"
    }
}
