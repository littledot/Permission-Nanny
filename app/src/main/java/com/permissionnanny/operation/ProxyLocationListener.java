package com.permissionnanny.operation;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.permissionnanny.ProxyListener;
import com.permissionnanny.ProxyService;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.location.LocationEvent;
import timber.log.Timber;

public class ProxyLocationListener extends ProxyListener implements LocationListener {

    public ProxyLocationListener(ProxyService service, String clientId, String serverId) {
        super(service, clientId, serverId);
    }

    @Override
    protected void unregister(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Intent intent = newResponseIntent()
                .putExtra(Nanny.SERVER, Nanny.LOCATION_SERVICE)
                .putExtra(LocationEvent.TYPE, LocationEvent.ON_LOCATION_CHANGED)
                .putExtra(LocationEvent.LOCATION, location);
        Timber.wtf(BundleUtil.toString(intent));
        sendBroadcast(intent);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = newResponseIntent()
                .putExtra(Nanny.SERVER, Nanny.LOCATION_SERVICE)
                .putExtra(LocationEvent.TYPE, LocationEvent.ON_PROVIDER_DISABLED)
                .putExtra(LocationEvent.PROVIDER, provider);
        Timber.wtf(BundleUtil.toString(intent));
        sendBroadcast(intent);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Intent intent = newResponseIntent()
                .putExtra(Nanny.SERVER, Nanny.LOCATION_SERVICE)
                .putExtra(LocationEvent.TYPE, LocationEvent.ON_PROVIDER_ENABLED)
                .putExtra(LocationEvent.PROVIDER, provider);
        Timber.wtf(BundleUtil.toString(intent));
        sendBroadcast(intent);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Intent intent = newResponseIntent()
                .putExtra(Nanny.SERVER, Nanny.LOCATION_SERVICE)
                .putExtra(LocationEvent.TYPE, LocationEvent.ON_STATUS_CHANGED)
                .putExtra(LocationEvent.STATUS, status)
                .putExtra(LocationEvent.EXTRAS, extras);
        Timber.wtf(BundleUtil.toString(intent));
        sendBroadcast(intent);
    }
}
