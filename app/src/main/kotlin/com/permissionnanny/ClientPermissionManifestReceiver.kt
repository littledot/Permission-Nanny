package com.permissionnanny

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.NannyException
import com.permissionnanny.lib.PPP
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * This receiver is part of PPP. Its class name must never change.
 */
@PPP
class ClientPermissionManifestReceiver : BaseReceiver() {

    @Inject lateinit var appManager: AppPermissionManager

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        getComponent(context).inject(this)

        // Validate feral request and ensure required parameters are present
        val clientAddr = intent.getStringExtra(Nanny.CLIENT_ADDRESS)
        val entity = intent.getBundleExtra(Nanny.ENTITY_BODY)
        if (entity == null) {
            badRequest(context, clientAddr, NannyException(Err.NO_ENTITY))
            return
        }
        val client = entity.getParcelable<PendingIntent>(Nanny.SENDER_IDENTITY)
        if (client == null) {
            badRequest(context, clientAddr, NannyException(Err.NO_SENDER_IDENTITY))
            return
        }
        val permissionUsage = entity.getStringArrayList(Nanny.PERMISSION_MANIFEST)
        if (permissionUsage == null) {
            badRequest(context, clientAddr, NannyException(Err.NO_PERMISSION_MANIFEST))
            return
        }

        val clientPackage = client.intentSender.targetPackage
        Timber.wtf("client=" + clientPackage + " usage=" + Arrays.toString(permissionUsage.toTypedArray()))
        appManager.readPermissionManifest(clientPackage, permissionUsage)

        okRequest(context, clientAddr)
    }

    private fun badRequest(context: Context, clientAddr: String?, error: Throwable) {
        Timber.wtf("err=" + error.message)
        if (clientAddr != null && !clientAddr.isEmpty()) {
            val payload = ResponseFactory.newBadRequestResponse(Nanny.PERMISSION_MANIFEST_SERVICE, error).build()
            val response = Intent(clientAddr).putExtras(payload)
            context.sendBroadcast(response)
        }
    }

    private fun okRequest(context: Context, clientAddr: String?) {
        if (clientAddr != null && !clientAddr.isEmpty()) {
            val payload = ResponseFactory.newAllowResponse(Nanny.PERMISSION_MANIFEST_SERVICE)
            payload.mConnection = Nanny.CLOSE
            val response = Intent(clientAddr).putExtras(payload.build())
            context.sendBroadcast(response)
        }
    }
}
