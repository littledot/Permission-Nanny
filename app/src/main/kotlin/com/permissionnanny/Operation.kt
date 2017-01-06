package com.permissionnanny

import android.support.annotation.StringRes
import com.permissionnanny.content.ContentOperation
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.content.ContentRequest
import com.permissionnanny.simple.SimpleOperation

/**

 */
open class Operation(
        @StringRes val dialogTitle: Int,
        val minSdk: Int,
        val protectionLevel: Int) {

    companion object {
        fun getOperation(request: RequestParams): Operation? {
            when (request.opCode) {
                ContentRequest.SELECT,
                ContentRequest.INSERT,
                ContentRequest.UPDATE,
                ContentRequest.DELETE -> return ContentOperation.getOperation(request)
                else -> return SimpleOperation.getOperation(request)
            }
        }
    }
}
