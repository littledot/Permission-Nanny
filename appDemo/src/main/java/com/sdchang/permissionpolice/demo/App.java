package com.sdchang.permissionpolice.demo;

import android.app.Application;
import com.sdchang.permissionpolice.common.StackTraceDebugTree;
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

