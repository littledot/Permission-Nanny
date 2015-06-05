package com.sdchang.permissionpolice.lib.request.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import com.sdchang.permissionpolice.lib.BundleListener;
import com.sdchang.permissionpolice.lib.Police;
import org.apache.http.protocol.HTTP;

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

    private BundleListener mBundleListener;
    private LocationListener mLocationListener;
    private Handler mHandler;

    public LocationReceiver(BundleListener bundle, LocationListener location, Handler handler) {
        mBundleListener = bundle;
        mLocationListener = location;
        mHandler = handler;
    }

    @Override
    public void onReceive(Context context, final Intent intent) {
        if (HTTP.CONN_CLOSE.equals(intent.getStringExtra(HTTP.CONN_DIRECTIVE))) {
            context.unregisterReceiver(this);
        }

        String server = intent.getStringExtra(Police.SERVER);
        if (Police.AUTHENTICATION_SERVICE.equals(server)) {
            Bundle response = intent.getExtras();
            mBundleListener.onResult(response);
        } else if (Police.LOCATION_SERVICE.equals(server)) {
            // send ack
            String ackServerId = intent.getStringExtra(ACK_SERVER);
            String clientId = intent.getAction();
            context.sendBroadcast(new Intent(ackServerId).putExtra(CLIENT_ID, clientId));

            // handle response
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String type = intent.getStringExtra(TYPE);
                    if (ON_LOCATION_CHANGED.equals(type)) {
                        Location location = intent.getParcelableExtra(LOCATION);
                        mLocationListener.onLocationChanged(location);
                    } else if (ON_PROVIDER_DISABLED.equals(type)) {
                        String provider = intent.getStringExtra(PROVIDER);
                        mLocationListener.onProviderDisabled(provider);
                    } else if (ON_PROVIDER_ENABLED.equals(type)) {
                        String provider = intent.getStringExtra(PROVIDER);
                        mLocationListener.onProviderEnabled(provider);
                    } else if (ON_STATUS_CHANGED.equals(type)) {
                        String provider = intent.getStringExtra(PROVIDER);
                        int status = intent.getIntExtra(STATUS, -1);
                        Bundle extras = intent.getBundleExtra(EXTRAS);
                        mLocationListener.onStatusChanged(provider, status, extras);
                    }
                }
            });
        }
    }
}
