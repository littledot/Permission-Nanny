package com.permissionnanny.lib.request.simple;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import com.permissionnanny.lib.Err;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.Ack;
import timber.log.Timber;

/**
 *
 */
public class NmeaEvent implements Event {

    @PPP public static final String TIMESTAMP = "timestamp";
    @PPP public static final String NMEA = "nmea";

    private final NmeaListener mListener;
    private final Ack mAck;

    public NmeaEvent(NmeaListener listener) {
        this(listener, new Ack());
    }

    @VisibleForTesting
    NmeaEvent(NmeaListener listener, Ack ack) {
        mListener = listener;
        mAck = ack;
    }

    @Override
    public String filter() {
        return Nanny.NMEA_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        mAck.sendAck(context, intent);

        Bundle entity = new NannyBundle(intent).getEntityBody();
        if (entity == null) {
            Timber.wtf(Err.NO_ENTITY);
            return;
        }
        long timestamp = entity.getLong(TIMESTAMP, -1);
        String nmea = entity.getString(NMEA);
        mListener.onNmeaReceived(timestamp, nmea);
    }
}
