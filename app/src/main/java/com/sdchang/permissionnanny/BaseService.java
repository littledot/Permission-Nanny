package com.sdchang.permissionnanny;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.sdchang.permissionnanny.dagger.ActivityComponent;
import com.sdchang.permissionnanny.dagger.DaggerActivityComponent;
import timber.log.Timber;

/**
 *
 */
public class BaseService extends Service {

    private ActivityComponent mActivityComponent;

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
    protected DaggerActivityComponent.Builder buildActivityComponent(DaggerActivityComponent.Builder builder) {
        return builder.appComponent(((App) getApplication()).getAppComponent());
    }

    /**
     * @return
     */
    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = buildActivityComponent(DaggerActivityComponent.builder()).build();
        }
        return mActivityComponent;
    }
}
