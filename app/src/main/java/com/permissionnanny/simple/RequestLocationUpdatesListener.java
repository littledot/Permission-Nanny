package com.permissionnanny.simple;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import com.permissionnanny.ProxyListener;
import com.permissionnanny.ProxyService;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.LocationEvent;
import com.permissionnanny.lib.request.simple.LocationRequest;

public class RequestLocationUpdatesListener extends ProxyListener<LocationListener> implements LocationListener {

    public RequestLocationUpdatesListener(ProxyService service, String clientAddr) {
        super(service, clientAddr, Nanny.LOCATION_SERVICE);
    }

    @Override
    public void register(Context context, RequestParams request) {
        throw new UnsupportedOperationException("Must override.");
    }

    @Override
    public void unregister(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Bundle entity = new Bundle();
        entity.putString(Nanny.TYPE, LocationEvent.ON_LOCATION_CHANGED);
        entity.putParcelable(LocationEvent.LOCATION, location);

        sendBroadcast(okResponse(entity));
    }

    @Override
    public void onProviderDisabled(String provider) {
        Bundle entity = new Bundle();
        entity.putString(Nanny.TYPE, LocationEvent.ON_PROVIDER_DISABLED);
        entity.putString(LocationEvent.PROVIDER, provider);

        sendBroadcast(okResponse(entity));
    }

    @Override
    public void onProviderEnabled(String provider) {
        Bundle entity = new Bundle();
        entity.putString(Nanny.TYPE, LocationEvent.ON_PROVIDER_ENABLED);
        entity.putString(LocationEvent.PROVIDER, provider);

        sendBroadcast(okResponse(entity));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Bundle entity = new Bundle();
        entity.putString(Nanny.TYPE, LocationEvent.ON_STATUS_CHANGED);
        entity.putString(LocationEvent.PROVIDER, provider);
        entity.putInt(LocationEvent.STATUS, status);
        entity.putBundle(LocationEvent.EXTRAS, extras);

        sendBroadcast(okResponse(entity));
    }

    /**
     * {@link LocationRequest#requestLocationUpdates(long, float, Criteria, LocationListener, Looper)}
     */
    public static class Api1 extends RequestLocationUpdatesListener {
        public Api1(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(request.long0, request.float0, request.criteria0, this, null);
        }
    }

    /**
     * {@link LocationRequest#requestLocationUpdates(String, long, float, LocationListener)} and {@link
     * LocationRequest#requestLocationUpdates(String, long, float, LocationListener, Looper)}
     */
    public static class Api2 extends RequestLocationUpdatesListener {
        public Api2(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(request.string0, request.long0, request.float0, this, null);
        }
    }
}
