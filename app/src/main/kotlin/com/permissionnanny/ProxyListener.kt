package com.permissionnanny

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import com.permissionnanny.common.BundleUtil
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.NannyBundle
import com.permissionnanny.lib.request.RequestParams
import timber.log.Timber

/**

 */
open class ProxyListener<Listener>(
        protected val service: ProxyService,
        private val clientAddr: String,
        /** Proxy service name.  */
        protected val server: String) {

    /** Time of last broadcast.  */
    private var lastBroadcast: Long = 0
    /** Time of last ACK received.  */
    private var lastAck: Long = 0

    var listener: Listener? = null

    fun register(c: Context, r: RequestParams, l: Listener) {
    }

    init {
        lastAck = SystemClock.elapsedRealtime()
        lastBroadcast = lastAck
    }

    open fun register(context: Context, request: RequestParams) {
    }

    open fun unregister(context: Context) {
    }

    protected fun sendBroadcast(response: Bundle) {
        if (lastBroadcast - lastAck > 5000) { // no recent ack? assume client died
            Timber.wtf("Dead client. Removing " + clientAddr)
            stop()

            val timeoutResponse = NannyBundle.Builder()
                    .statusCode(Nanny.SC_TIMEOUT)
                    .server(Nanny.AUTHORIZATION_SERVICE)
                    .connection(Nanny.CLOSE)
                    .build()
            service.sendBroadcast(Intent(clientAddr).putExtras(timeoutResponse))
            return
        }
        lastBroadcast = SystemClock.elapsedRealtime()
        service.sendBroadcast(Intent(clientAddr).putExtras(response))
        Timber.wtf(BundleUtil.toString(response))
    }

    fun updateAck(time: Long) {
        lastAck = time
    }

    fun stop() {
        unregister(service)
        service.removeProxyClient(clientAddr)
    }

    protected fun okResponse(entity: Bundle): Bundle {
        return ResponseFactory.newAllowResponse(server)
                .entity(entity)
                .ackAddress(service.ackAddress)
                .build()
    }
}
