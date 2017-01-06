package com.permissionnanny

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.permissionnanny.common.BundleUtil
import com.permissionnanny.content.ContentOperation
import com.permissionnanny.content.ProxyContentProvider
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.NannyBundle
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.content.ContentRequest
import com.permissionnanny.simple.SimpleOperation
import timber.log.Timber
import java.security.SecureRandom

/**

 */
open class ProxyExecutor(private val context: Context) {

    open fun executeAllow(operation: Operation, request: RequestParams, clientId: String?) {
        if (operation is SimpleOperation) {
            executeAllowSimple(operation, request, clientId)
        } else if (operation is ContentOperation) {
            executeAllowContent(operation, request, clientId)
        }
    }

    private fun executeAllowContent(operation: ContentOperation, request: RequestParams, clientId: String?) {
        val response = executeContentOperation(request).build()
        if (response != null && clientId != null) {
            Timber.d("server broadcasting=" + BundleUtil.toString(response))
            val intent = Intent(clientId).putExtras(response)
            context.sendBroadcast(intent)
        }
    }

    private fun executeContentOperation(request: RequestParams): NannyBundle.Builder {
        val entity = Bundle()
        when (request.opCode) {
            ContentRequest.SELECT -> {
                val nonce = SecureRandom().nextLong()
                Timber.wtf("nonce=" + nonce)

                // cache request params
                ProxyContentProvider.approvedRequests.put(nonce, request)

                // return nonce to client
                entity.putLong(request.opCode, nonce)
            }
            ContentRequest.INSERT -> {
                val uri = context.contentResolver
                        .insert(request.uri0, request.contentValues0)
                entity.putParcelable(request.opCode, uri)
            }
            ContentRequest.UPDATE -> {
                val updated = context.contentResolver
                        .update(request.uri0, request.contentValues0, request.string0, request.stringArray1)
                entity.putInt(request.opCode, updated)
            }
            ContentRequest.DELETE -> {
                val deleted = context.contentResolver
                        .delete(request.uri0, request.string0, request.stringArray1)
                entity.putInt(request.opCode, deleted)
            }
        }

        val response = ResponseFactory.newAllowResponse(Nanny.AUTHORIZATION_SERVICE)
        response.mConnection = Nanny.CLOSE
        response.mEntity = entity
        return response
    }

    private fun executeAllowSimple(operation: SimpleOperation, request: RequestParams, clientId: String?) {
        val response = executeSimpleOperation(operation, request, clientId)
        if (response != null && clientId != null) {
            Timber.d("server broadcasting=" + BundleUtil.toString(response))
            val intent = Intent(clientId).putExtras(response)
            context.sendBroadcast(intent)
        }
    }

    private fun executeSimpleOperation(operation: SimpleOperation,
                                       request: RequestParams,
                                       clientId: String?): Bundle? {
        if (operation.proxyFunction != null) { // one-shot request
            val entity = Bundle()
            try {
                operation.proxyFunction.invoke(context, request, entity)
            } catch (error: Throwable) {
                return ResponseFactory.newBadRequestResponse(Nanny.AUTHORIZATION_SERVICE, error).build()
            }

            return ResponseFactory.newAllowResponse(Nanny.AUTHORIZATION_SERVICE)
                    .connection(Nanny.CLOSE)
                    .entity(entity)
                    .build()
        }

        // ongoing request
        val server = Intent(context, ProxyService::class.java)
        server.putExtra(ProxyService.CLIENT_ADDR, clientId)
        server.putExtra(ProxyService.REQUEST_PARAMS, request)
        Timber.wtf("Operation.function is null, starting service with args: " + BundleUtil.toString(server))
        context.startService(server)
        return null
    }

    open fun executeDeny(operation: Operation, request: RequestParams, clientId: String?) {
        if (clientId != null) {
            val response = ResponseFactory.newDenyResponse(Nanny.AUTHORIZATION_SERVICE).build()
            if (response != null) {
                Timber.d("server broadcasting=" + BundleUtil.toString(response))
                val intent = Intent(clientId).putExtras(response)
                context.sendBroadcast(intent)
            }
        }
    }
}
