package com.sdchang.permissionpolice;

import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.location.ProxyLocationListener;

class ProxyClient {
    public final String mClientId;
    public final BaseRequest mRequest;
    public final ProxyLocationListener mListener;

    public ProxyClient(String clientId, BaseRequest request, ProxyLocationListener listener) {
        mClientId = clientId;
        mRequest = request;
        mListener = listener;
    }
}
