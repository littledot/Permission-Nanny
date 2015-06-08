package com.sdchang.permissionpolice;

import com.sdchang.permissionpolice.lib.request.BaseRequest;

class ProxyClient {
    public final String mClientId;
    public final BaseRequest mRequest;
    public final ProxyListener mListener;

    public ProxyClient(String clientId, BaseRequest request, ProxyListener listener) {
        mClientId = clientId;
        mRequest = request;
        mListener = listener;
    }
}
