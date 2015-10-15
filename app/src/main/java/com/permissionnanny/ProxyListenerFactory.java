package com.permissionnanny;

/**
 *
 */
public interface ProxyListenerFactory {

    ProxyListener newProxyListener(ProxyService service, String clientAddr);
}
