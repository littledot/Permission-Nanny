package com.permissionnanny;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.request.RequestParams;
import timber.log.Timber;

/**
 *
 */
public class ProxyListener<Listener> {
    protected final ProxyService mService;
    private final String mClientAddr;

    /**
     * Time of last broadcast.
     */
    private long mLastBroadcast;
    /**
     * Time of last ACK received.
     */
    private long mLastAck;
    /**
     * Proxy service name.
     */
    protected final String mServer;

    public Listener mListener;

    public void register(Context c, RequestParams r, Listener l) {}

    public ProxyListener(ProxyService service, String clientAddr, String server) {
        mService = service;
        mClientAddr = clientAddr;
        mServer = server;
        mLastBroadcast = mLastAck = SystemClock.elapsedRealtime();
    }

    public void register(Context context, RequestParams request) {}

    public void unregister(Context context) {}

    protected void sendBroadcast(Bundle response) {
        if (mLastBroadcast - mLastAck > 5000) { // no recent ack? assume client died
            Timber.wtf("Dead client. Removing " + mClientAddr);
            stop();

            Bundle timeoutResponse = new NannyBundle.Builder()
                    .statusCode(Nanny.SC_TIMEOUT)
                    .server(Nanny.AUTHORIZATION_SERVICE)
                    .connection(Nanny.CLOSE)
                    .build();
            mService.sendBroadcast(new Intent(mClientAddr).putExtras(timeoutResponse));
            return;
        }
        mLastBroadcast = SystemClock.elapsedRealtime();
        mService.sendBroadcast(new Intent(mClientAddr).putExtras(response));
        Timber.wtf(BundleUtil.toString(response));
    }

    public void updateAck(long time) {
        mLastAck = time;
    }

    public void stop() {
        unregister(mService);
        mService.removeProxyClient(mClientAddr);
    }

    protected Bundle okResponse(Bundle entity) {
        return ResponseFactory.newAllowResponse(mServer)
                .entity(entity)
                .ackAddress(mService.getAckAddress())
                .build();
    }

    protected Bundle errorResponse(Throwable error) {
        return ResponseFactory.newDenyResponse(Nanny.AUTHORIZATION_SERVICE)
                .error(error)
                .connection(Nanny.CLOSE)
                .build();
    }
}
