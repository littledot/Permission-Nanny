package com.permissionnanny;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import com.permissionnanny.common.StackTraceDebugTree;
import com.permissionnanny.dagger.AppComponent;
import com.permissionnanny.dagger.AppModule;
import com.permissionnanny.dagger.DaggerAppComponent;
import com.permissionnanny.dagger.DaggerAppComponent.Builder;
import timber.log.Timber;

/**
 *
 */
public class App extends Application {
    public static PendingIntent IDENTITY;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        PRNGFixes.apply();
        Timber.plant(new StackTraceDebugTree());
        IDENTITY = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
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
