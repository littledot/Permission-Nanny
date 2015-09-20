package com.permissionnanny;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.request.RequestParams;
import timber.log.Timber;

/**
 *
 */
public class ProxyListener {
    protected final ProxyService mService;
    private final String mClientAddr;

    /** Time of last broadcast. */
    private long mLastBroadcast;
    /** Time of last ACK received. */
    private long mLastAck;
    /** Proxy service name. */
    private String mServer;

    public ProxyListener(ProxyService service, String clientAddr, String server) {
        mService = service;
        mClientAddr = clientAddr;
        mServer = server;
    }

    public void updateAck(long time) {
        mLastAck = time;
    }

    protected void sendBroadcast(Bundle response) {
        if (mLastAck - mLastBroadcast > 5000) { // no recent ack? assume client died
            Timber.wtf("Dead client. Removing " + mClientAddr);
            unregister(mService);
            mService.removeProxyClient(mClientAddr);

            Bundle timeoutResponse = new NannyBundle.Builder()
                    .statusCode(Nanny.SC_TIMEOUT)
                    .server(Nanny.AUTHORIZATION_SERVICE)
                    .connection(Nanny.CLOSE)
                    .build();
            mService.sendBroadcast(new Intent(mClientAddr).putExtras(timeoutResponse));
            return;
        }
        mLastBroadcast = System.currentTimeMillis();
        mService.sendBroadcast(new Intent(mClientAddr).putExtras(response));
        Timber.wtf(BundleUtil.toString(response));
    }

    public void register(Context context, RequestParams request) {}

    public void unregister(Context context) {}

    protected Bundle okResponse(Bundle entity) {
        return ResponseFactory.newAllowResponse(mServer)
                .body(entity)
                .ackAddress(mService.getAckAddress())
                .build();
    }

    protected Bundle badRequestResponse(Throwable error) {
        return ResponseFactory.newBadRequestResponse(mServer, error)
                .build();
    }
}
