package com.permissionnanny;

import android.content.Context;
import android.content.Intent;
import com.permissionnanny.missioncontrol.PermissionConfigDataManager;

import javax.inject.Inject;

/**
 *
 */
public class UninstallReceiver extends BaseReceiver {

    @Inject PermissionConfigDataManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        getComponent(context).inject(this);

        if (intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) { // Upgrade flow
            return;
        }

        String appPackage = intent.getData().getSchemeSpecificPart();
        manager.removeApp(appPackage);
    }
}
