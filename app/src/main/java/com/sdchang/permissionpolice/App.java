package com.sdchang.permissionpolice;

import android.app.Application;
import com.sdchang.permissionpolice.common.StackTraceDebugTree;
import com.sdchang.permissionpolice.dagger.AppComponent;
import com.sdchang.permissionpolice.dagger.AppModule;
import com.sdchang.permissionpolice.dagger.DaggerAppComponent;
import com.sdchang.permissionpolice.dagger.DaggerAppComponent.Builder;
import timber.log.Timber;

/**
 *
 */
public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new StackTraceDebugTree());
    }

    /**
     * @param builder
     * @return
     */
    protected Builder buildAppComponent(Builder builder) {
        return builder.appModule(new AppModule(this));
    }

    /**
     * @return
     */
    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = buildAppComponent(DaggerAppComponent.builder()).build();
        }
        return mAppComponent;
    }
}
