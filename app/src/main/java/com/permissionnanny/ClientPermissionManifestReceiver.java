package com.permissionnanny;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.missioncontrol.PermissionConfigDataManager;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This receiver is part of PPP. It's class name must never change.
 */
@PPP
public class ClientPermissionManifestReceiver extends BroadcastReceiver {

    @Inject PermissionConfigDataManager mConfigManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);

        // Validate feral intent
        String clientAddr = intent.getStringExtra(Nanny.CLIENT_ADDRESS);
        Bundle entity = intent.getBundleExtra(Nanny.ENTITY_BODY);
        if (entity == null) {
            badRequest(context, clientAddr, new NannyException(Err.NO_ENTITY));
            return;
        }
        PendingIntent client = entity.getParcelable(Nanny.SENDER_IDENTITY);
        if (client == null) {
            badRequest(context, clientAddr, new NannyException(Err.NO_SENDER_IDENTITY));
            return;
        }
        ArrayList<String> permissionUsage = entity.getStringArrayList(Nanny.PERMISSION_MANIFEST);
        if (permissionUsage == null) {
            badRequest(context, clientAddr, new NannyException(Err.NO_PERMISSION_MANIFEST));
            return;
        }

        String clientPackage = client.getIntentSender().getTargetPackage();
        Timber.wtf("client=" + clientPackage + " usage=" + Arrays.toString(permissionUsage.toArray()));
        mConfigManager.registerApp(clientPackage, permissionUsage);

        okRequest(context, clientAddr);
    }

    private void badRequest(Context context, String clientAddr, Throwable error) {
        Timber.wtf("err=" + error.getMessage());
        if (clientAddr != null && !clientAddr.isEmpty()) {
            Bundle args = new ResponseBundle()
                    .server(Nanny.PERMISSION_MANIFEST_SERVICE)
                    .status(Nanny.SC_BAD_REQUEST)
                    .connection(Nanny.CLOSE)
                    .error(error)
                    .build();
            Intent response = new Intent(clientAddr).putExtras(args);
            context.sendBroadcast(response);
        }
    }

    private void okRequest(Context context, String clientAddr) {
        if (clientAddr != null && !clientAddr.isEmpty()) {
            Bundle args = new ResponseBundle()
                    .server(Nanny.PERMISSION_MANIFEST_SERVICE)
                    .status(Nanny.SC_OK)
                    .connection(Nanny.CLOSE)
                    .build();
            Intent response = new Intent(clientAddr).putExtras(args);
            context.sendBroadcast(response);
        }
    }
}
