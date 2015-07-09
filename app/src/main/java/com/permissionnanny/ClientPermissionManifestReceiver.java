package com.permissionnanny;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.missioncontrol.PermissionConfigDataManager;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class ClientPermissionManifestReceiver extends BroadcastReceiver {

    @Inject PermissionConfigDataManager mConfigManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);

        // TODO #54: Return success/error to clients
        Bundle entity = intent.getBundleExtra(Nanny.ENTITY_BODY);
        if (entity == null) {
            return;
        }
        PendingIntent client = entity.getParcelable(Nanny.CLIENT_PACKAGE);
        if (client == null) {
            return;
        }
        ArrayList<String> permissionUsage = entity.getStringArrayList(Nanny.PERMISSION_MANIFEST);
        if (permissionUsage == null) {
            return;
        }

        String clientPackage = client.getIntentSender().getTargetPackage();
        Timber.wtf("client=" + clientPackage + " usage=" + Arrays.toString(permissionUsage.toArray()));
        mConfigManager.registerApp(clientPackage, permissionUsage);
    }
}
