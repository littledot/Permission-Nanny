package com.sdchang.permissionpolice.lib.request.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 *
 */
public class WifiManagerHandshakeReceiver extends BroadcastReceiver {
    public static final String ACTION_FILTER = "WifiManagerHandshakeReceiver";
    public static final String RESPONSE = "response";

    WifiManagerResponseListener mListener;

    public WifiManagerHandshakeReceiver(WifiManagerResponseListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.unregisterReceiver(this);
        WifiManagerResponse response = intent.getParcelableExtra(RESPONSE);
        mListener.onResult(response);
    }
}
