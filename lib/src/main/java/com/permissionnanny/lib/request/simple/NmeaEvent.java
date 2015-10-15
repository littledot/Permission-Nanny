package com.permissionnanny.lib.request.simple;

import android.content.Context;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.BaseEvent;

/**
 *
 */
public class NmeaEvent extends BaseEvent {
    @PPP public static final String TIMESTAMP = "timestamp";
    @PPP public static final String NMEA = "nmea";

    private NmeaListener mListener;

    public NmeaEvent(NmeaListener listener) {
        mListener = listener;
    }

    @Override
    public String filter() {
        return Nanny.NMEA_SERVICE;
    }

    @Override
    public void processEntity(Context context, Bundle entity) {
        long timestamp = entity.getLong(TIMESTAMP, -1);
        String nmea = entity.getString(NMEA);
        mListener.onNmeaReceived(timestamp, nmea);
    }
}
