package com.sdchang.permissionpolice.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.util.SimpleArrayMap;
import com.sdchang.permissionpolice.BaseService;
import com.sdchang.permissionpolice.common.BundleUtil;
import com.sdchang.permissionpolice.lib.request.location.LocationReceiver;
import com.sdchang.permissionpolice.lib.request.location.LocationRequest;
import timber.log.Timber;

import java.security.SecureRandom;

/**
 *
 */
public class LocationServerService extends BaseService {

    static final String CLIENT_ID = "clientId";
    static final String REQUEST = "request";

    private LocationManager mLocationManager;
    private SimpleArrayMap<String, LocationClient> mClients = new SimpleArrayMap<>();
    private AckReceiver mAckReceiver = new AckReceiver();
    private String mServerId;

    class LocationClient {
        public final String mClientId;
        public final LocationRequest mRequest;
        public final ProxyLocationListener mListener;

        public LocationClient(String clientId, LocationRequest request, ProxyLocationListener listener) {
            mClientId = clientId;
            mRequest = request;
            mListener = listener;
        }
    }

    class AckReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String clientId = intent.getStringExtra(LocationReceiver.CLIENT_ID);
            LocationClient client = mClients.get(clientId);
            if (client != null) {
                client.mListener.mLastAck = System.currentTimeMillis();
            }
        }
    }

    class ProxyLocationListener implements LocationListener {

        private String mClientId;
        /** Time of last broadcast. */
        private long mLastBroadcast;
        /** Time of last ACK received. */
        private long mLastAck;

        public ProxyLocationListener(String clientId) {
            mClientId = clientId;
        }

        private void verifyClientIsAlive(Intent intent) {
            if (mLastAck - mLastBroadcast > 5000) { // no recent ack? assume client died
                Timber.wtf("Dead client. Removing " + mClientId);
                mClients.remove(mClientId);
                return;
            }
            mLastBroadcast = System.currentTimeMillis();
            sendBroadcast(intent);
        }

        @Override
        public void onLocationChanged(Location location) {
            Intent intent = new Intent(mClientId)
                    .putExtra(LocationReceiver.ACK_SERVER, mServerId)
                    .putExtra(LocationReceiver.TYPE, LocationReceiver.ON_LOCATION_CHANGED)
                    .putExtra(LocationReceiver.LOCATION, location);
            Timber.wtf(BundleUtil.toString(intent));
            verifyClientIsAlive(intent);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Intent intent = new Intent(mClientId)
                    .putExtra(LocationReceiver.ACK_SERVER, mServerId)
                    .putExtra(LocationReceiver.TYPE, LocationReceiver.ON_PROVIDER_DISABLED)
                    .putExtra(LocationReceiver.PROVIDER, provider);
            Timber.wtf(BundleUtil.toString(intent));
            verifyClientIsAlive(intent);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Intent intent = new Intent(mClientId)
                    .putExtra(LocationReceiver.ACK_SERVER, mServerId)
                    .putExtra(LocationReceiver.TYPE, LocationReceiver.ON_PROVIDER_ENABLED)
                    .putExtra(LocationReceiver.PROVIDER, provider);
            Timber.wtf(BundleUtil.toString(intent));
            verifyClientIsAlive(intent);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Intent intent = new Intent(mClientId)
                    .putExtra(LocationReceiver.ACK_SERVER, mServerId)
                    .putExtra(LocationReceiver.TYPE, LocationReceiver.ON_STATUS_CHANGED)
                    .putExtra(LocationReceiver.STATUS, status)
                    .putExtra(LocationReceiver.EXTRAS, extras);
            Timber.wtf(BundleUtil.toString(intent));
            verifyClientIsAlive(intent);
        }
    }

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
        LocationRequest request = intent.getParcelableExtra(REQUEST);
        switch (request.opCode()) {
        case LocationRequest.REQUEST_LOCATION_UPDATES1:
            Timber.wtf("Requesting location updates.");
            ProxyLocationListener listener = new ProxyLocationListener(clientId);
            LocationClient client = new LocationClient(clientId, request, listener);
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
}
