package com.sdchang.permissionpolice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import timber.log.Timber;

/**
 *
 */
public class BaseService extends Service {

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
        Timber.wtf("");
        super.onDestroy();
    }
}
