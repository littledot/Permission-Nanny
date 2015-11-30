package com.permissionnanny;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.permissionnanny.common.StackTraceDebugTree;
import com.permissionnanny.dagger.AppComponent;
import com.permissionnanny.dagger.AppModule;
import com.permissionnanny.dagger.ContextComponent;
import com.permissionnanny.dagger.ContextModule;
import com.permissionnanny.dagger.DaggerAppComponent;
import com.permissionnanny.dagger.DaggerContextComponent;
import io.fabric.sdk.android.Fabric;
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
        // Crashlytics must always be first
        CrashlyticsCore crashlytics = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(crashlytics).build());
        PRNGFixes.apply();
        Timber.plant(new StackTraceDebugTree());

        // https://github.com/dain/leveldb/issues/43
        System.setProperty("sun.arch.data.model", "32");
        System.setProperty("leveldb.mmap", "false");

        IDENTITY = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
    }

    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return mAppComponent;
    }

    public ContextComponent getContextComponent(Context context) {
        return DaggerContextComponent.builder()
                .appComponent(getAppComponent())
                .contextModule(new ContextModule(context))
                .build();
    }

    public static ContextComponent newContextComponent(Context context) {
        return ((App) context.getApplicationContext()).getContextComponent(context);
    }
}
