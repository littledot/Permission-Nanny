package com.permissionnanny.simple;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.permissionnanny.ProxyListener;
import com.permissionnanny.ProxyService;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.LocationEvent;

public class ProxyLocationListener extends ProxyListener implements LocationListener {

    public ProxyLocationListener(ProxyService service, String clientAddr) {
        super(service, clientAddr);
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

        sendBroadcast(okResponse(Nanny.LOCATION_SERVICE, entity));
    }

    @Override
    public void onProviderDisabled(String provider) {
        Bundle entity = new Bundle();
        entity.putString(Nanny.TYPE, LocationEvent.ON_PROVIDER_DISABLED);
        entity.putString(LocationEvent.PROVIDER, provider);

        sendBroadcast(okResponse(Nanny.LOCATION_SERVICE, entity));
    }

    @Override
    public void onProviderEnabled(String provider) {
        Bundle entity = new Bundle();
        entity.putString(Nanny.TYPE, LocationEvent.ON_PROVIDER_ENABLED);
        entity.putString(LocationEvent.PROVIDER, provider);

        sendBroadcast(okResponse(Nanny.LOCATION_SERVICE, entity));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Bundle entity = new Bundle();
        entity.putString(Nanny.TYPE, LocationEvent.ON_STATUS_CHANGED);
        entity.putString(LocationEvent.PROVIDER, provider);
        entity.putInt(LocationEvent.STATUS, status);
        entity.putBundle(LocationEvent.EXTRAS, extras);

        sendBroadcast(okResponse(Nanny.LOCATION_SERVICE, entity));
    }

    public static class Api1 extends ProxyLocationListener {
        public Api1(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            try {
                lm.requestLocationUpdates(request.long0, request.float0, request.criteria0, this, null);
            } catch (Throwable e) {
                sendBroadcast(badRequest(Nanny.LOCATION_SERVICE, e));
            }
        }
    }
}
