package com.permissionnanny

import android.content.Context
import android.content.Intent
import android.content.pm.PermissionInfo
import com.permissionnanny.common.IntentUtil
import com.permissionnanny.data.AppPermission
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.NannyBundle
import com.permissionnanny.lib.NannyException
import com.permissionnanny.lib.PPP
import timber.log.Timber
import javax.inject.Inject

/**
 * This receiver is part of PPP. Its class name must never change.
 */
@PPP
class ClientRequestReceiver : BaseReceiver() {

    @Inject lateinit var appManager: AppPermissionManager
    @Inject lateinit var executor: ProxyExecutor

    override fun onReceive(context: Context, intent: Intent) {
        Timber.wtf("got intent: " + IntentUtil.toString(intent))
        super.onReceive(context, intent)
        getComponent(context).inject(this)

        val bundle = NannyBundle(intent.extras)

        // Validate feral request and ensure required parameters are present
        val clientAddr = bundle.clientAddress
        val clientPackage = bundle.senderIdentity
        if (clientPackage == null) {
            badRequest(context, clientAddr, NannyException(Err.NO_SENDER_IDENTITY))
            return
        }
        val request = bundle.request
        if (request == null) {
            badRequest(context, clientAddr, NannyException(Err.NO_REQUEST_PARAMS))
            return
        }
        val operation = Operation.getOperation(request)
        if (operation == null) {
            badRequest(context, clientAddr, NannyException(Err.UNSUPPORTED_OPCODE, request.opCode))
            return
        }

        // NORMAL operation? Automatically allow
        if (operation.protectionLevel == PermissionInfo.PROTECTION_NORMAL) {
            executor.executeAllow(operation, request, clientAddr)
            return
        }

        // DANGEROUS operation? Check user's config first
        val userConfig = appManager.getPermissionPrivilege(clientPackage, operation, request)
        when (userConfig) {
            AppPermission.ALWAYS_ASK -> context.startActivity(Intent(context, ConfirmRequestActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtras(intent))
            AppPermission.ALWAYS_ALLOW -> executor.executeAllow(operation, request, clientAddr)
            AppPermission.ALWAYS_DENY -> executor.executeDeny(operation, request, clientAddr)
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
