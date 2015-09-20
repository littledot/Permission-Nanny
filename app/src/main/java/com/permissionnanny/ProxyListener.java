package com.permissionnanny;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.lib.Nanny;
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

    public ProxyListener(ProxyService service, String clientAddr) {
        mService = service;
        mClientAddr = clientAddr;
    }

    public void updateAck(long time) {
        mLastAck = time;
    }

    protected void sendBroadcast(Intent intent) {
        if (mLastAck - mLastBroadcast > 5000) { // no recent ack? assume client died
            Timber.wtf("Dead client. Removing " + mClientAddr);
            unregister(mService);
            mService.removeProxyClient(mClientAddr);
            return;
        }
        mLastBroadcast = System.currentTimeMillis();
        mService.sendBroadcast(intent);
    }

    public void register(Context context, RequestParams request) {}

    public void unregister(Context context) {}

    protected Intent newResponseIntent(String server, Bundle entity) {
        entity.putString(Nanny.ACK_SERVER_ADDRESS, mService.getAckAddress());

        Intent response = new Intent(mClientAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.SERVER, server)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_OK)
                .putExtra(Nanny.ENTITY_BODY, entity);
        Timber.wtf(BundleUtil.toString(response));
        return response;
    }
}
