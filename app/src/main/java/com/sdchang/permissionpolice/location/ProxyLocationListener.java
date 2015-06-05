package com.sdchang.permissionpolice.location;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.sdchang.permissionpolice.ProxyListener;
import com.sdchang.permissionpolice.ProxyService;
import com.sdchang.permissionpolice.common.BundleUtil;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.location.LocationReceiver;
import timber.log.Timber;

public class ProxyLocationListener extends ProxyListener implements LocationListener {

    public ProxyLocationListener(ProxyService service, String clientId, String serverId) {
        super(service, clientId, serverId);
    }

    @Override
    public void onLocationChanged(Location location) {
        Intent intent = newResponseIntent()
                .putExtra(Police.SERVER, Police.LOCATION_SERVICE)
                .putExtra(LocationReceiver.TYPE, LocationReceiver.ON_LOCATION_CHANGED)
                .putExtra(LocationReceiver.LOCATION, location);
        Timber.wtf(BundleUtil.toString(intent));
        sendBroadcast(intent);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = newResponseIntent()
                .putExtra(Police.SERVER, Police.LOCATION_SERVICE)
                .putExtra(LocationReceiver.TYPE, LocationReceiver.ON_PROVIDER_DISABLED)
                .putExtra(LocationReceiver.PROVIDER, provider);
        Timber.wtf(BundleUtil.toString(intent));
        sendBroadcast(intent);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Intent intent = newResponseIntent()
                .putExtra(Police.SERVER, Police.LOCATION_SERVICE)
                .putExtra(LocationReceiver.TYPE, LocationReceiver.ON_PROVIDER_ENABLED)
                .putExtra(LocationReceiver.PROVIDER, provider);
        Timber.wtf(BundleUtil.toString(intent));
        sendBroadcast(intent);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Intent intent = newResponseIntent()
                .putExtra(Police.SERVER, Police.LOCATION_SERVICE)
                .putExtra(LocationReceiver.TYPE, LocationReceiver.ON_STATUS_CHANGED)
                .putExtra(LocationReceiver.STATUS, status)
                .putExtra(LocationReceiver.EXTRAS, extras);
        Timber.wtf(BundleUtil.toString(intent));
        sendBroadcast(intent);
    }
}
