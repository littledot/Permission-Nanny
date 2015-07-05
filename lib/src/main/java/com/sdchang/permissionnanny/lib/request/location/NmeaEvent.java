package com.sdchang.permissionnanny.lib.request.location;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus.NmeaListener;
import com.sdchang.permissionnanny.lib.Event;
import com.sdchang.permissionnanny.lib.Nanny;

/**
 *
 */
public class NmeaEvent implements Event {
    public static final String TIMESTAMP = "timestamp";
    public static final String NMEA = "nmea";
    private NmeaListener mListener;

    public NmeaEvent(NmeaListener listener) {
        mListener = listener;
    }

    @Override
    public String filter() {
        return Nanny.NMEA_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        long timestamp = intent.getLongExtra(TIMESTAMP, -1);
        String nmea = intent.getStringExtra(NMEA);
        mListener.onNmeaReceived(timestamp, nmea);
    }
}
