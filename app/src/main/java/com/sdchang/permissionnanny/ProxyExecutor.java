package com.sdchang.permissionnanny;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.sdchang.permissionnanny.common.BundleUtil;
import com.sdchang.permissionnanny.lib.Nanny;
import com.sdchang.permissionnanny.lib.request.RequestParams;
import org.apache.http.protocol.HTTP;
import timber.log.Timber;

/**
 *
 */
public class ProxyExecutor {

    private Context mContext;

    public ProxyExecutor(Context context) {
        mContext = context;
    }

    public void executeAllow(ProxyOperation operation, RequestParams request, String clientId) {
        Bundle response = execute(operation, request, clientId).build();
        if (response != null && clientId != null) {
            Timber.d("server broadcasting=" + BundleUtil.toString(response));
            Intent intent = new Intent(clientId).putExtras(response);
            mContext.sendBroadcast(intent);
        }
    }

    private ResponseBundle execute(ProxyOperation operation, RequestParams request, String clientId) {
        if (operation.mFunction != null) { // one-shot request
            Bundle response = new Bundle();
            try {
                operation.mFunction.execute(mContext, request, response);
            } catch (Throwable error) {
                return ResponseFactory.newBadRequestResponse(error);
            }
            return ResponseFactory.newAllowResponse()
                    .connection(HTTP.CONN_CLOSE)
                    .contentType(Nanny.APPLICATION_BUNDLE)
                    .body(response);
        }

        // ongoing request
        Intent server = new Intent(mContext, ProxyService.class);
        server.putExtra(ProxyService.CLIENT_ID, clientId);
        server.putExtra(ProxyService.REQUEST, request);
        Timber.wtf("Operation.function is null, starting service with args: " + BundleUtil.toString(server));
        mContext.startService(server);
        return ResponseFactory.newAllowResponse();
    }

    public void executeDeny(ProxyOperation operation, RequestParams request, String clientId) {
        if (clientId != null) {
            Bundle response = ResponseFactory.newDenyResponse().build();
            if (response != null) {
                Timber.d("server broadcasting=" + BundleUtil.toString(response));
                Intent intent = new Intent(clientId).putExtras(response);
                mContext.sendBroadcast(intent);
            }
        }
    }
}
