package com.sdchang.permissionpolice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.sdchang.permissionpolice.missioncontrol.PermissionConfigDataManager;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class AppPermissionUsageReceiver extends BroadcastReceiver {

    @Inject PermissionConfigDataManager mConfigManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);

        String clientPackage = intent.getStringExtra("senderPackage");
        ArrayList<String> permissionUsage = intent.getStringArrayListExtra("permissionUsage");
        Timber.wtf("client=" + clientPackage + " usage=" + Arrays.toString(permissionUsage.toArray()));

        mConfigManager.registerApp(clientPackage, permissionUsage);
    }
}
