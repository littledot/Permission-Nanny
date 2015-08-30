package com.permissionnanny;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.content.ContentOperation;
import com.permissionnanny.content.ProxyContentProvider;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.content.ContentRequest;
import com.permissionnanny.simple.SimpleOperation;
import timber.log.Timber;

import javax.inject.Inject;
import java.security.SecureRandom;

/**
 *
 */
public class ProxyExecutor {

    private Context mContext;

    @Inject
    public ProxyExecutor(Context context) {
        mContext = context;
    }

    public void executeAllow(Operation operation, RequestParams request, String clientId) {
        if (operation instanceof SimpleOperation) {
            executeAllow((SimpleOperation) operation, request, clientId);
        } else if (operation instanceof ContentOperation) {
            executeAllow((ContentOperation) operation, request, clientId);
        }
    }

    private void executeAllow(ContentOperation operation, RequestParams request, String clientId) {
        Bundle response = executeContentOperation(request).build();
        if (response != null && clientId != null) {
            Timber.d("server broadcasting=" + BundleUtil.toString(response));
            Intent intent = new Intent(clientId).putExtras(response);
            mContext.sendBroadcast(intent);
        }
    }

    private ResponseBundle executeContentOperation(RequestParams request) {
        Bundle entity = new Bundle();
        switch (request.opCode) {
        case ContentRequest.SELECT:
            long nonce = new SecureRandom().nextLong();
            Timber.wtf("nonce=" + nonce);

            // cache request params
            ProxyContentProvider.approvedRequests.put(nonce, request);

            // return nonce to client
            entity.putLong(request.opCode, nonce);
            break;
        case ContentRequest.INSERT:
            Uri uri = mContext.getContentResolver()
                    .insert(request.uri0, request.contentValues0);
            entity.putParcelable(request.opCode, uri);
            break;
        case ContentRequest.UPDATE:
            int updated = mContext.getContentResolver()
                    .update(request.uri0, request.contentValues0, request.string0, request.stringArray1);
            entity.putInt(request.opCode, updated);
            break;
        case ContentRequest.DELETE:
            int deleted = mContext.getContentResolver()
                    .delete(request.uri0, request.string0, request.stringArray1);
            entity.putInt(request.opCode, deleted);
            break;
        }
        return ResponseFactory.newAllowResponse()
                .connection(Nanny.CLOSE)
                .body(entity);
    }

    private void executeAllow(SimpleOperation operation, RequestParams request, String clientId) {
        Bundle response = executeSimpleOperation(operation, request, clientId).build();
        if (response != null && clientId != null) {
            Timber.d("server broadcasting=" + BundleUtil.toString(response));
            Intent intent = new Intent(clientId).putExtras(response);
            mContext.sendBroadcast(intent);
        }
    }

    private ResponseBundle executeSimpleOperation(SimpleOperation operation, RequestParams request, String clientId) {
        if (operation.mFunction != null) { // one-shot request
            Bundle response = new Bundle();
            try {
                operation.mFunction.execute(mContext, request, response);
            } catch (Throwable error) {
                return ResponseFactory.newBadRequestResponse(error);
            }
            return ResponseFactory.newAllowResponse()
                    .connection(Nanny.CLOSE)
                    .body(response);
        }

        // ongoing request
        Intent server = new Intent(mContext, ProxyService.class);
        server.putExtra(ProxyService.CLIENT_ADDR, clientId);
        server.putExtra(ProxyService.REQUEST_PARAMS, request);
        Timber.wtf("Operation.function is null, starting service with args: " + BundleUtil.toString(server));
        mContext.startService(server);
        return ResponseFactory.newAllowResponse();
    }

    public void executeDeny(Operation operation, RequestParams request, String clientId) {
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
