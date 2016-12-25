package com.permissionnanny;

import android.content.Context;
import android.content.Intent;
import com.permissionnanny.data.AppPermissionManager;
import javax.inject.Inject;
import timber.log.Timber;

/**
 *
 */
public class UninstallReceiver extends BaseReceiver {

    @Inject AppPermissionManager mAppManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        getComponent(context).inject(this);

        if (intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) { // Upgrade flow
            return;
        }

        Timber.wtf("removed package=" + intent.getData());
        String appPackage = intent.getData().getSchemeSpecificPart();
        mAppManager.removeApp(appPackage);
    }
}
