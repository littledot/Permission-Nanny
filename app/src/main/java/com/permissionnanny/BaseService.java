package com.permissionnanny;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.VisibleForTesting;
import com.permissionnanny.dagger.ContextComponent;
import com.permissionnanny.dagger.ContextModule;
import com.permissionnanny.dagger.DaggerContextComponent;
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

    @VisibleForTesting
    public void setTestComponent(ContextComponent component) {
        mComponent = component;
    }

    public ContextComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerContextComponent.builder()
                    .appComponent(((App) getApplicationContext()).getAppComponent())
                    .contextModule(new ContextModule(this))
                    .build();
        }
        return mComponent;
    }
}
