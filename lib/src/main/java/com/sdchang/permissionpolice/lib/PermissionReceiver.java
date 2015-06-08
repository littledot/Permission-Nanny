package com.sdchang.permissionpolice.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.SimpleArrayMap;
import org.apache.http.protocol.HTTP;

/**
 *
 */
public class PermissionReceiver extends BroadcastReceiver {

    private SimpleArrayMap<String, Event> mEventFilters = new SimpleArrayMap<>();

    public PermissionReceiver addFilter(Event event) {
        mEventFilters.put(event.filter(), event);
        return this;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (HTTP.CONN_CLOSE.equals(intent.getStringExtra(HTTP.CONN_DIRECTIVE))) {
            context.unregisterReceiver(this);
        }
        String server = intent.getStringExtra(Police.SERVER);
        mEventFilters.get(server).process(context, intent);
    }
}
