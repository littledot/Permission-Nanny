package com.sdchang.permissionpolice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.v4.util.SimpleArrayMap;
import com.sdchang.permissionpolice.common.BundleUtil;
import com.sdchang.permissionpolice.lib.request.RequestParams;
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
        RequestParams requestParams = intent.getParcelableExtra(REQUEST);
        handleRequest(clientId, requestParams);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mAckReceiver);
    }
    private void handleRequest(String clientId, RequestParams requestParams) {
        Timber.wtf("handling client=" + clientId + " req=" + requestParams);
        switch (requestParams.opCode()) {
        case LocationRequest.ADD_GPS_STATUS_LISTENER:
            handleAddGpsStatusListenerRequest(requestParams, clientId);
            break;
        case LocationRequest.ADD_NMEA_LISTENER:
            handleAddNmeaListener(requestParams, clientId);
            break;
        case LocationRequest.REQUEST_LOCATION_UPDATES1:
            handleRequestLocationUpdates1(requestParams, clientId);
            break;
        }
    }

    private void handleAddGpsStatusListenerRequest(RequestParams request, String clientId) {
        ProxyGpsStatusListener gpsStatusListener = new ProxyGpsStatusListener(this, clientId, SERVER_ID);
        mClients.put(clientId, new ProxyClient(clientId, request, gpsStatusListener));
        mLocationManager.addGpsStatusListener(gpsStatusListener);
    }

    private void handleAddNmeaListener(RequestParams request, String clientId) {
        ProxyNmeaListener nmeaListener = new ProxyNmeaListener(this, clientId, SERVER_ID);
        mClients.put(clientId, new ProxyClient(clientId, request, nmeaListener));
        mLocationManager.addNmeaListener(nmeaListener);
    }

    private void handleRequestLocationUpdates1(RequestParams request, String clientId) {
        ProxyLocationListener locationListener = new ProxyLocationListener(this, clientId, SERVER_ID);
        mClients.put(clientId, new ProxyClient(clientId, request, locationListener));
        mLocationManager.requestLocationUpdates(request.long0(), request.float0(), request.criteria0(),
                locationListener, null);
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
