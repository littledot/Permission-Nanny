package com.permissionnanny.lib.request.location;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.BaseEvent;

/**
 *
 */
public class LocationEvent extends BaseEvent implements Event {

    @PPP public static final String LOCATION = "location";
    @PPP public static final String PROVIDER = "provider";
    @PPP public static final String STATUS = "status";
    @PPP public static final String EXTRAS = "extras";

    @PPP public static final String ON_LOCATION_CHANGED = "onLocationChanged";
    @PPP public static final String ON_PROVIDER_DISABLED = "onProviderDisabled";
    @PPP public static final String ON_PROVIDER_ENABLED = "onProviderEnabled";
    @PPP public static final String ON_STATUS_CHANGED = "onStatusChanged";

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
    public void process(Context context, Intent intent) {
        sendAck(context, intent);

        final Bundle entity = intent.getBundleExtra(Nanny.ENTITY_BODY);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String type = entity.getString(Nanny.TYPE);
                if (ON_LOCATION_CHANGED.equals(type)) {
                    Location location = entity.getParcelable(LOCATION);
                    mLocationListener.onLocationChanged(location);
                } else if (ON_PROVIDER_DISABLED.equals(type)) {
                    String provider = entity.getString(PROVIDER);
                    mLocationListener.onProviderDisabled(provider);
                } else if (ON_PROVIDER_ENABLED.equals(type)) {
                    String provider = entity.getString(PROVIDER);
                    mLocationListener.onProviderEnabled(provider);
                } else if (ON_STATUS_CHANGED.equals(type)) {
                    String provider = entity.getString(PROVIDER);
                    int status = entity.getInt(STATUS, -1);
                    Bundle extras = entity.getBundle(EXTRAS);
                    mLocationListener.onStatusChanged(provider, status, extras);
                }
            }
        });
    }
}
