package com.permissionnanny.lib.request.simple;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.BaseEvent;

/**
 *
 */
public class GpsStatusEvent extends BaseEvent {

    @PPP public static final String EVENT = "event";

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
        sendAck(context, intent);

        Bundle entity = intent.getBundleExtra(Nanny.ENTITY_BODY);
        int event = entity.getInt(EVENT, -1);
        mListener.onGpsStatusChanged(event);
    }
}
