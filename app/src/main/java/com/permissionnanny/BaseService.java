package com.permissionnanny;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.permissionnanny.dagger.ContextComponent;
import timber.log.Timber;

/**
 * The root of all Services.
 */
public class BaseService extends Service {

    private ContextComponent mComponent;

    @Override
    public void onCreate() {
        Timber.wtf("");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.wtf("");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Timber.wtf("");
        return null;
    }

    @Override
    public void onDestroy() {
        Timber.wtf("destroy");
        super.onDestroy();
    }

    public ContextComponent getComponent(Context context) {
        if (mComponent == null) {
            mComponent = ((App) context.getApplicationContext()).getContextComponent(context);
        }
        return mComponent;
    }
}
