package com.permissionnanny.demo;

import android.app.Application;
import com.permissionnanny.common.StackTraceDebugTree;
import com.permissionnanny.lib.Nanny;
import timber.log.Timber;

/**
 *
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Nanny.configureServer(BuildConfig.DEBUG);
        Timber.plant(new StackTraceDebugTree());
    }
}

