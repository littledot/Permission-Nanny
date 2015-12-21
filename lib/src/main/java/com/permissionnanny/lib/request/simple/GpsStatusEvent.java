package com.permissionnanny.lib.request.simple;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
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
 * Event filter that handles {@link Nanny#GPS_STATUS_SERVICE} responses.
 */
public class GpsStatusEvent implements Event {

    @PPP public static final String EVENT = "event";

    private final GpsStatus.Listener mListener;
    private final Ack mAck;

    public GpsStatusEvent(GpsStatus.Listener listener) {
        this(listener, new Ack());
    }

    @VisibleForTesting
    GpsStatusEvent(GpsStatus.Listener listener, Ack ack) {
        mListener = listener;
        mAck = ack;
    }

    @Override
    public String filter() {
        return Nanny.GPS_STATUS_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        mAck.sendAck(context, intent);

        Bundle entity = new NannyBundle(intent).getEntityBody();
        if (entity == null) {
            Timber.wtf(Err.NO_ENTITY);
            return;
        }
        int event = entity.getInt(EVENT, -1);
        mListener.onGpsStatusChanged(event);
    }
}
