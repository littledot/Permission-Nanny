package com.permissionnanny.lib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 *
 */
public class BundleEvent implements Event {

    private BundleListener mListener;

    public BundleEvent(BundleListener listener) {
        mListener = listener;
    }

    @Override
    public String filter() {
        return Nanny.AUTHORIZATION_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        Bundle response = intent.getExtras();
        mListener.onResult(response);
    }
}
