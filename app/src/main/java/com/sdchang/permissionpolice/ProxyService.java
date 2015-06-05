package com.sdchang.permissionpolice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.v4.util.SimpleArrayMap;
import com.sdchang.permissionpolice.common.BundleUtil;
import com.sdchang.permissionpolice.lib.request.OpRequest;
import com.sdchang.permissionpolice.lib.request.location.LocationReceiver;
import com.sdchang.permissionpolice.lib.request.location.LocationRequest;
import com.sdchang.permissionpolice.location.ProxyLocationListener;
import timber.log.Timber;

import java.security.SecureRandom;

/**
 *
 */
public class ProxyService extends BaseService {

    public static final String CLIENT_ID = "clientId";
    public static final String REQUEST = "request";

    private LocationManager mLocationManager;
    private SimpleArrayMap<String, ProxyClient> mClients = new SimpleArrayMap<>();
    private AckReceiver mAckReceiver = new AckReceiver();
    private String mServerId;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mServerId = "LocationServerService" + new SecureRandom().nextLong();
        registerReceiver(mAckReceiver, new IntentFilter(mServerId));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.wtf("Server started with args: " + BundleUtil.toString(intent));
        String clientId = intent.getStringExtra(CLIENT_ID);
        OpRequest opRequest = intent.getParcelableExtra(REQUEST);

        switch (opRequest.opCode()) {
        case LocationRequest.REQUEST_LOCATION_UPDATES1:
            Timber.wtf("Requesting location updates.");
            LocationRequest request = (LocationRequest) opRequest;
            ProxyLocationListener listener = new ProxyLocationListener(this, clientId, mServerId);
            ProxyClient client = new ProxyClient(clientId, opRequest, listener);
            mClients.put(clientId, client);
            mLocationManager.requestLocationUpdates(request.long0(), request.float0(), request.criteria0(), listener,
                    null);
            break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mAckReceiver);
    }

    public void removeProxyClient(String clientId) {
        mClients.remove(clientId);
        if (mClients.isEmpty()) { // no more clients? kill service
            stopSelf();
        }
    }

    class AckReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String clientId = intent.getStringExtra(LocationReceiver.CLIENT_ID);
            ProxyClient client = mClients.get(clientId);
            if (client != null) {
                client.mListener.updateAck(System.currentTimeMillis());
            }
        }
    }
}
