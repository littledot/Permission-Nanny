package com.permissionnanny;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.v4.util.SimpleArrayMap;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.location.LocationEvent;
import com.permissionnanny.lib.request.location.LocationRequest;
import com.permissionnanny.location.ProxyGpsStatusListener;
import com.permissionnanny.location.ProxyLocationListener;
import com.permissionnanny.location.ProxyNmeaListener;
import io.snapdb.CryIterator;
import io.snapdb.SnapDB;
import timber.log.Timber;

import javax.inject.Inject;

/**
 *
 */
public class ProxyService extends BaseService {

    public static final String SERVER_ID = "com.permission.police.ProxyService";
    public static final String CLIENT_ID = "clientId";
    public static final String REQUEST = "request";

    private SimpleArrayMap<String, ProxyClient> mClients = new SimpleArrayMap<>();
    private AckReceiver mAckReceiver = new AckReceiver();
    private LocationManager mLocationManager;

    @Inject SnapDB mDB;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        registerReceiver(mAckReceiver, new IntentFilter(SERVER_ID));
        getActivityComponent().inject(this);
        Timber.wtf("init service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) { // Service killed by OS? Restore client state
            restoreState();
            return super.onStartCommand(intent, flags, startId);
        }
        Timber.wtf("Server started with args: " + BundleUtil.toString(intent));
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

    private void save(String clientId, RequestParams request) {
        mDB.put(clientId, request);
    }

    private void restoreState() {
        int count = 0;
        CryIterator<? extends RequestParams> iterator = mDB.iterator(RequestParams.class);
        while (iterator.moveToNext()) {
            count++;
            String client = iterator.key();
            RequestParams params = iterator.val();
            handleRequest(client, params);
        }
        iterator.close();
        Timber.wtf("restored " + count + " clients");
    }

    private void handleRequest(String clientId, RequestParams requestParams) {
        Timber.wtf("handling client=" + clientId + " req=" + requestParams);
        save(clientId, requestParams);
        switch (requestParams.opCode) {
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
        mLocationManager.requestLocationUpdates(request.long0, request.float0, request.criteria0, locationListener,
                null);
    }

    public void removeProxyClient(String clientId) {
        mClients.remove(clientId);
        mDB.del(clientId);
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
