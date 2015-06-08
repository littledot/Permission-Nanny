package com.sdchang.permissionpolice.lib.request.location;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus.Listener;
import com.sdchang.permissionpolice.lib.Event;
import com.sdchang.permissionpolice.lib.Police;

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
        return Police.GPS_STATUS_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        int event = intent.getIntExtra(EVENT, -1);
        mListener.onGpsStatusChanged(event);
    }
}
