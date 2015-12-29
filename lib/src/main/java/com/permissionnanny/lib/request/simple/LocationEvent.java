package com.permissionnanny.lib.request.simple;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.VisibleForTesting;
import com.permissionnanny.lib.Err;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.Ack;
import timber.log.Timber;

/**
 * Event filter that handles {@link Nanny#LOCATION_SERVICE} responses.
 */
public class LocationEvent implements Event {

    @PPP public static final String LOCATION = "location";
    @PPP public static final String PROVIDER = "provider";
    @PPP public static final String STATUS = "status";
    @PPP public static final String EXTRAS = "extras";

    @PPP public static final String ON_LOCATION_CHANGED = "onLocationChanged";
    @PPP public static final String ON_PROVIDER_DISABLED = "onProviderDisabled";
    @PPP public static final String ON_PROVIDER_ENABLED = "onProviderEnabled";
    @PPP public static final String ON_STATUS_CHANGED = "onStatusChanged";

    private final LocationListener mLocationListener;
    private final Handler mHandler;
    private final Ack mAck;

    public LocationEvent(LocationListener location, Handler handler) {
        this(location, handler, new Ack());
    }

    @VisibleForTesting
    LocationEvent(LocationListener locationListener, Handler handler, Ack ack) {
        mLocationListener = locationListener;
        mHandler = handler;
        mAck = ack;
    }

    @Override
    public String filter() {
        return Nanny.LOCATION_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        mAck.sendAck(context, intent);

        final Bundle entity = new NannyBundle(intent).getEntityBody();
        if (entity == null) {
            Timber.wtf(Err.NO_ENTITY);
            return;
        }

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
