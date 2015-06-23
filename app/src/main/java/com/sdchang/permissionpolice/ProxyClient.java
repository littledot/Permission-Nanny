package com.sdchang.permissionpolice;

import com.sdchang.permissionpolice.lib.request.RequestParams;

class ProxyClient {
    public final String mClientId;
    public final RequestParams mRequest;
    public final ProxyListener mListener;

    public ProxyClient(String clientId, RequestParams request, ProxyListener listener) {
        mClientId = clientId;
        mRequest = request;
        mListener = listener;
    }
}
