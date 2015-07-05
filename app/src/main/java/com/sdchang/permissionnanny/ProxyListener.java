package com.sdchang.permissionnanny;

import android.content.Context;
import android.content.Intent;
import com.sdchang.permissionnanny.lib.Nanny;
import timber.log.Timber;

/**
 *
 */
public class ProxyListener {
    private final ProxyService mService;
    private final String mClientId;
    private final String mServerId;

    /** Time of last broadcast. */
    private long mLastBroadcast;
    /** Time of last ACK received. */
    private long mLastAck;

    public ProxyListener(ProxyService service, String clientId, String serverId) {
        mService = service;
        mClientId = clientId;
        mServerId = serverId;
    }

    public void updateAck(long time) {
        mLastAck = time;
    }

    protected void sendBroadcast(Intent intent) {
        if (mLastAck - mLastBroadcast > 5000) { // no recent ack? assume client died
            Timber.wtf("Dead client. Removing " + mClientId);
            unregister(mService);
            mService.removeProxyClient(mClientId);
            return;
        }
        mLastBroadcast = System.currentTimeMillis();
        mService.sendBroadcast(intent);
    }

    protected void unregister(Context context) {}

    protected Intent newResponseIntent() {
        return new Intent(mClientId)
                .putExtra(Nanny.ACK_SERVER, mServerId);
    }
}
