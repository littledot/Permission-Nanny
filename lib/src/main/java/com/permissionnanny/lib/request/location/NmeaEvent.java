package com.permissionnanny.lib.request.location;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.BaseEvent;

/**
 *
 */
public class NmeaEvent extends BaseEvent {
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
        sendAck(context, intent);

        Bundle entity = intent.getBundleExtra(Nanny.ENTITY_BODY);
        long timestamp = entity.getLong(TIMESTAMP, -1);
        String nmea = entity.getString(NMEA);
        mListener.onNmeaReceived(timestamp, nmea);
    }
}
