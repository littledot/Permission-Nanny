package com.permissionnanny.lib.request.location;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;

/**
 *
 */
public class LocationEvent implements Event {
    public static final String TYPE = "type";
    public static final String LOCATION = "location";
    public static final String PROVIDER = "provider";
    public static final String STATUS = "status";
    public static final String EXTRAS = "extras";

    public static final String ON_LOCATION_CHANGED = "onLocationChanged";
    public static final String ON_PROVIDER_DISABLED = "onProviderDisabled";
    public static final String ON_PROVIDER_ENABLED = "onProviderEnabled";
    public static final String ON_STATUS_CHANGED = "onStatusChanged";

    // experimental
    public static final String CLIENT_ID = "clientId";

    private LocationListener mLocationListener;
    private Handler mHandler;

    public LocationEvent(LocationListener location, Handler handler) {
        mLocationListener = location;
        mHandler = handler;
    }

    @Override
    public String filter() {
        return Nanny.LOCATION_SERVICE;
    }

    @Override
    public void process(Context context, final Intent intent) {
        // send ack
        String ackServerId = intent.getStringExtra(Nanny.ACK_SERVER);
        String clientId = intent.getAction();
        context.sendBroadcast(new Intent(ackServerId).putExtra(CLIENT_ID, clientId));

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
