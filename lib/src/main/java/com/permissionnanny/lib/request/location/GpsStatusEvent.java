package com.permissionnanny.lib.request.location;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus.Listener;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;

/**
 *
 */
public class GpsStatusEvent implements Event {
    public static final String EVENT = "event";

    private Listener mListener;

    public GpsStatusEvent(Listener listener) {
        mListener = listener;
    }

    @Override
    public String filter() {
        return Nanny.GPS_STATUS_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        int event = intent.getIntExtra(EVENT, -1);
        mListener.onGpsStatusChanged(event);
    }
}
