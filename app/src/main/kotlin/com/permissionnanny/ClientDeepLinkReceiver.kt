package com.permissionnanny

import android.content.Context
import android.content.Intent
import com.permissionnanny.common.IntentUtil
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.NannyBundle
import com.permissionnanny.lib.NannyException
import com.permissionnanny.lib.PPP
import com.permissionnanny.lib.deeplink.DeepLinkRequest
import com.permissionnanny.missioncontrol.AppControlActivity
import timber.log.Timber

/**
 * This receiver is part of PPP. Its class name must never change.
 *
 *
 * Receiver that allows clients to navigate users directly into Permission Nanny. Please see [DeepLinkRequest] for
 * documentation.
 */
@PPP
class ClientDeepLinkReceiver : BaseReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.wtf("got intent: " + IntentUtil.toString(intent))
        super.onReceive(context, intent)

        val bundle = NannyBundle(intent.extras)

        // Validate feral request and ensure required parameters are present
        val clientAddr = bundle.clientAddress
        val clientPackage = bundle.senderIdentity
        if (clientPackage == null) {
            badRequest(context, clientAddr, NannyException(Err.NO_SENDER_IDENTITY))
            return
        }

        val deepLinkTarget = bundle.deepLinkTarget
        when (deepLinkTarget) {
            Nanny.MANAGE_APPLICATIONS_SETTINGS -> context.startActivity(Intent(context, AppControlActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            else -> {
                badRequest(context, clientAddr, NannyException(Err.UNSUPPORTED_DEEP_LINK_TARGET, deepLinkTarget))
                return
            }
        }
        okRequest(context, clientAddr)
    }

    private fun okRequest(context: Context, clientAddr: String?) {
        if (clientAddr != null && !clientAddr.isEmpty()) {
            val args = ResponseFactory.newAllowResponse(Nanny.AUTHORIZATION_SERVICE).connection(Nanny.CLOSE).build()
            val response = Intent(clientAddr).putExtras(args)
            context.sendBroadcast(response)
        }
    }

    private fun badRequest(context: Context, clientAddr: String?, error: Throwable) {
        Timber.wtf("err=" + error.message)
        if (clientAddr != null && !clientAddr.isEmpty()) {
            val args = ResponseFactory.newBadRequestResponse(Nanny.AUTHORIZATION_SERVICE, error).build()
            val response = Intent(clientAddr).putExtras(args)
            context.sendBroadcast(response)
        }
    }
}
