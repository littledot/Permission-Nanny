package com.sdchang.permissionpolice.lib.request.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import timber.log.Timber;

/**
 *
 */
public class LocationReceiver extends BroadcastReceiver {

    public static final String TYPE = "type";
    public static final String LOCATION = "location";
    public static final String PROVIDER = "provider";
    public static final String STATUS = "status";
    public static final String EXTRAS = "extras";
    public static final String ACK_SERVER = "ackServer";
    public static final String CLIENT_ID = "clientId";

    public static final String ON_LOCATION_CHANGED = "onLocationChanged";
    public static final String ON_PROVIDER_DISABLED = "onProviderDisabled";
    public static final String ON_PROVIDER_ENABLED = "onProviderEnabled";
    public static final String ON_STATUS_CHANGED = "onStatusChanged";

    private LocationListener mListener;
    private Handler mHandler;

    public LocationReceiver(LocationListener listener, Handler handler) {
        mListener = listener;
        mHandler = handler;
    }

    @Override
    public void onReceive(Context context, final Intent intent) {
        // send ack
        String ackServerId = intent.getStringExtra(ACK_SERVER);
        String clientId = intent.getAction();
        context.sendBroadcast(new Intent(ackServerId).putExtra(CLIENT_ID, clientId));

        // handle response
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Timber.wtf("");
                String type = intent.getStringExtra(TYPE);
                if (ON_LOCATION_CHANGED.equals(type)) {
                    Location location = intent.getParcelableExtra(LOCATION);
                    mListener.onLocationChanged(location);
                } else if (ON_PROVIDER_DISABLED.equals(type)) {
                    String provider = intent.getStringExtra(PROVIDER);
                    mListener.onProviderDisabled(provider);
                } else if (ON_PROVIDER_ENABLED.equals(type)) {
                    String provider = intent.getStringExtra(PROVIDER);
                    mListener.onProviderEnabled(provider);
                } else if (ON_STATUS_CHANGED.equals(type)) {
                    String provider = intent.getStringExtra(PROVIDER);
                    int status = intent.getIntExtra(STATUS, -1);
                    Bundle extras = intent.getBundleExtra(EXTRAS);
                    mListener.onStatusChanged(provider, status, extras);
                }
            }
        });
    }
}
