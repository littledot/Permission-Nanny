package com.sdchang.permissionpolice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.v4.util.SimpleArrayMap;
import com.sdchang.permissionpolice.common.BundleUtil;
import com.sdchang.permissionpolice.lib.request.OpRequest;
import com.sdchang.permissionpolice.lib.request.location.LocationEvent;
import com.sdchang.permissionpolice.lib.request.location.LocationRequest;
import com.sdchang.permissionpolice.location.ProxyGpsStatusListener;
import com.sdchang.permissionpolice.location.ProxyLocationListener;
import com.sdchang.permissionpolice.location.ProxyNmeaListener;
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
        // TODO #40: java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String android.content
        // .Intent.getStringExtra(java.lang.String)' on a null object reference
        String clientId = intent.getStringExtra(CLIENT_ID);
        OpRequest opRequest = intent.getParcelableExtra(REQUEST);

        LocationRequest request;
        switch (opRequest.opCode()) {
        case LocationRequest.ADD_GPS_STATUS_LISTENER:
            ProxyGpsStatusListener gpsStatusListener = new ProxyGpsStatusListener(this, clientId, mServerId);
            mClients.put(clientId, new ProxyClient(clientId, opRequest, gpsStatusListener));
            mLocationManager.addGpsStatusListener(gpsStatusListener);
            break;
        case LocationRequest.ADD_NMEA_LISTENER:
            ProxyNmeaListener nmeaListener = new ProxyNmeaListener(this, clientId, mServerId);
            mClients.put(clientId, new ProxyClient(clientId, opRequest, nmeaListener));
            mLocationManager.addNmeaListener(nmeaListener);
            break;
        case LocationRequest.REQUEST_LOCATION_UPDATES1:
            request = (LocationRequest) opRequest;
            ProxyLocationListener locationListener = new ProxyLocationListener(this, clientId, mServerId);
            mClients.put(clientId, new ProxyClient(clientId, opRequest, locationListener));
            mLocationManager.requestLocationUpdates(request.long0(), request.float0(), request.criteria0(),
                    locationListener, null);
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
            String clientId = intent.getStringExtra(LocationEvent.CLIENT_ID);
            ProxyClient client = mClients.get(clientId);
            if (client != null) {
                client.mListener.updateAck(System.currentTimeMillis());
            }
        }
    }
}
