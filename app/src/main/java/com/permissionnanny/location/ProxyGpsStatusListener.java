package com.permissionnanny.location;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus.Listener;
import android.location.LocationManager;
import com.permissionnanny.ProxyListener;
import com.permissionnanny.ProxyService;
import com.permissionnanny.lib.request.location.GpsStatusEvent;

/**
 *
 */
public class ProxyGpsStatusListener extends ProxyListener implements Listener {

    public ProxyGpsStatusListener(ProxyService service, String clientId, String serverId) {
        super(service, clientId, serverId);
    }

    @Override
    protected void unregister(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.removeGpsStatusListener(this);
    }

    @Override
    public void onGpsStatusChanged(int event) {
        Intent intent = newResponseIntent()
                .putExtra(GpsStatusEvent.EVENT, event);
        sendBroadcast(intent);
    }
}
