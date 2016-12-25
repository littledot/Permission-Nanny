package com.permissionnanny

/**

 */
interface ProxyListenerFactory {

    fun newProxyListener(service: ProxyService, clientAddr: String): ProxyListener<*>
}
