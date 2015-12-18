package com.permissionnanny.lib.request;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import java.util.Map;

/**
 * This is the main BroadcastReceiver that will receive responses from the server.
 * <p/>
 * Based on the value of {@link Nanny#SERVER}, this receiver will route the response to the appropriate event filter
 * for processing.
 * <p/>
 * Responses are dropped if the no event filters match {@link Nanny#SERVER}.
 */
public class PermissionReceiver extends BroadcastReceiver {

    private Map<String, Event> mEventFilters = new ArrayMap<>();

    public PermissionReceiver addFilter(Event event) {
        mEventFilters.put(event.filter(), event);
        return this;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Nanny.CLOSE.equals(intent.getStringExtra(Nanny.CONNECTION))) {
            context.unregisterReceiver(this);
        }
        String server = intent.getStringExtra(Nanny.SERVER);
        mEventFilters.get(server).process(context, intent);
    }
}
