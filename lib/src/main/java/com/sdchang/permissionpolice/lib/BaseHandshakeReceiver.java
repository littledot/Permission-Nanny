package com.sdchang.permissionpolice.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.apache.http.protocol.HTTP;

/**
 *
 */
public class BaseHandshakeReceiver extends BroadcastReceiver {

    private BundleListener mListener;

    public BaseHandshakeReceiver(BundleListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (HTTP.CONN_CLOSE.equals(intent.getStringExtra(HTTP.CONN_DIRECTIVE))) {
            context.unregisterReceiver(this);
        }
        Bundle response = intent.getExtras();
        mListener.onResult(response);
    }
}
