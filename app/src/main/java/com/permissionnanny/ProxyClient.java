package com.permissionnanny;

import com.permissionnanny.lib.request.RequestParams;

class ProxyClient {
    public final String mClientAddr;
    public final RequestParams mRequestParams;
    public final ProxyListener mListener;

    public ProxyClient(String clientAddr, RequestParams requestParams, ProxyListener listener) {
        mClientAddr = clientAddr;
        mRequestParams = requestParams;
        mListener = listener;
    }
}
