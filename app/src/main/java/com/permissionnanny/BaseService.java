package com.permissionnanny;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.permissionnanny.dagger.ContextComponent;
import com.permissionnanny.dagger.DaggerContextComponent;
import timber.log.Timber;

/**
 *
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

    /**
     * @param builder
     * @return
     */
    protected DaggerContextComponent.Builder buildActivityComponent(DaggerContextComponent.Builder builder) {
        return builder.appComponent(((App) getApplication()).getAppComponent());
    }

    /**
     * @return
     */
    public ContextComponent getActivityComponent() {
        if (mComponent == null) {
            mComponent = buildActivityComponent(DaggerContextComponent.builder()).build();
        }
        return mComponent;
    }
}
