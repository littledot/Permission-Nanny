package com.sdchang.permissionpolice.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
        context.unregisterReceiver(this);
        Bundle response = intent.getBundleExtra(Police.RESPONSE);
        mListener.onResult(response);
    }
}
