package com.permissionnanny;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import com.permissionnanny.dagger.ContextComponent;
import com.permissionnanny.dagger.ContextModule;
import com.permissionnanny.dagger.DaggerContextComponent;

/**
 * The root of all BroadcastReceivers.
 */
public class BaseReceiver extends BroadcastReceiver {

    private ContextComponent mComponent;

    @Override
    public void onReceive(Context context, Intent intent) {}

    @VisibleForTesting
    public void setTestComponent(ContextComponent component) {
        mComponent = component;
    }

    public ContextComponent getComponent(Context context) {
        if (mComponent == null) {
            mComponent = DaggerContextComponent.builder()
                    .appComponent(((App) context.getApplicationContext()).getAppComponent())
                    .contextModule(new ContextModule(context))
                    .build();
        }
        return mComponent;
    }
}
