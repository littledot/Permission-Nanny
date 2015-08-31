package com.permissionnanny;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.permissionnanny.dagger.ContextComponent;

/**
 *
 */
public class BaseReceiver extends BroadcastReceiver {

    private ContextComponent mComponent;

    @Override
    public void onReceive(Context context, Intent intent) {}

    public ContextComponent getComponent(Context context) {
        if (mComponent == null) {
            mComponent = ((App) context.getApplicationContext()).getContextComponent(context);
        }
        return mComponent;
    }
}
