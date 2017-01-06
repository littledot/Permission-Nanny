package com.permissionnanny

import com.permissionnanny.lib.request.RequestParams

internal class ProxyClient(
        val clientAddr: String,
        val requestParams: RequestParams,
        val listener: ProxyListener<*>)
