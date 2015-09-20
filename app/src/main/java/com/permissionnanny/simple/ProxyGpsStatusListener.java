package com.permissionnanny.simple;

import android.content.Context;
import android.location.GpsStatus.Listener;
import android.location.LocationManager;
import android.os.Bundle;
import com.permissionnanny.ProxyListener;
import com.permissionnanny.ProxyService;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.GpsStatusEvent;

/**
 *
 */
public class ProxyGpsStatusListener extends ProxyListener implements Listener {

    public ProxyGpsStatusListener(ProxyService service, String clientAddr) {
        super(service, clientAddr);
    }

    @Override
    public void register(Context context, RequestParams request) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.addGpsStatusListener(this);
    }

    @Override
    public void unregister(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.removeGpsStatusListener(this);
    }

    @Override
    public void onGpsStatusChanged(int event) {
        Bundle entity = new Bundle();
        entity.putInt(GpsStatusEvent.EVENT, event);

        sendBroadcast(okResponse(Nanny.GPS_STATUS_SERVICE, entity));
    }
}
