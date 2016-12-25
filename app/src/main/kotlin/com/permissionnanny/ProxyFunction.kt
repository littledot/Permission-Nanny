package com.permissionnanny

import android.content.Context
import android.os.Bundle
import com.permissionnanny.lib.request.RequestParams

/**

 */
interface ProxyFunction {

    fun execute(context: Context, request: RequestParams, response: Bundle)
}
