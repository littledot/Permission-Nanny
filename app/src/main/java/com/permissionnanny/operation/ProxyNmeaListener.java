package com.permissionnanny.operation;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationManager;
import com.permissionnanny.ProxyListener;
import com.permissionnanny.ProxyService;
import com.permissionnanny.lib.request.location.NmeaEvent;

/**
 *
 */
public class ProxyNmeaListener extends ProxyListener implements NmeaListener {

    public ProxyNmeaListener(ProxyService service, String clientId, String serverId) {
        super(service, clientId, serverId);
    }

    @Override
    protected void unregister(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.removeNmeaListener(this);
    }

    @Override
    public void onNmeaReceived(long timestamp, String nmea) {
        Intent intent = newResponseIntent()
                .putExtra(NmeaEvent.TIMESTAMP, timestamp)
                .putExtra(NmeaEvent.NMEA, nmea);
        sendBroadcast(intent);
    }
}
