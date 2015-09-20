package com.permissionnanny.simple;

import android.content.Context;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.permissionnanny.ProxyListener;
import com.permissionnanny.ProxyService;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.NmeaEvent;

/**
 *
 */
public class ProxyNmeaListener extends ProxyListener implements NmeaListener {

    public ProxyNmeaListener(ProxyService service, String clientAddr) {
        super(service, clientAddr);
    }

    @Override
    public void register(Context context, RequestParams request) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.addNmeaListener(this);
    }

    @Override
    public void unregister(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.removeNmeaListener(this);
    }

    @Override
    public void onNmeaReceived(long timestamp, String nmea) {
        Bundle entity = new Bundle();
        entity.putLong(NmeaEvent.TIMESTAMP, timestamp);
        entity.putString(NmeaEvent.NMEA, nmea);

        sendBroadcast(okResponse(Nanny.NMEA_SERVICE, entity));
    }
}
