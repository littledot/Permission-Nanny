package com.permissionnanny;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import com.permissionnanny.dagger.ContextComponent;

/**
 * The root of all BroadcastReceivers.
 */
public class BaseReceiver extends BroadcastReceiver {

    private ContextComponent mComponent;

    @Override
    public void onReceive(Context context, Intent intent) {}

    @VisibleForTesting
    void setComponent(ContextComponent component) {
        mComponent = component;
    }

    public ContextComponent getComponent(Context context) {
        if (mComponent == null) {
            mComponent = ((App) context.getApplicationContext()).getContextComponent(context);
        }
        return mComponent;
    }
}
