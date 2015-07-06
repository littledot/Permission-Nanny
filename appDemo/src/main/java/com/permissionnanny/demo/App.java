package com.permissionnanny.demo;

import android.app.Application;
import com.permissionnanny.common.StackTraceDebugTree;
import timber.log.Timber;

/**
 *
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new StackTraceDebugTree());
    }
}

