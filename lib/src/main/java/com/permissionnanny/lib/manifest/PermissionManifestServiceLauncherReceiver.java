package com.permissionnanny.lib.manifest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyException;

/**
 *
 */
public class PermissionManifestServiceLauncherReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (!validateIntent(intent)) {
                return;
            }
        } catch (NannyException e) {
            // TODO #72: Think of a good error reporting strategy
            e.printStackTrace();
        }

        Intent service = new Intent(context, PermissionManifestService.class);
        service.putExtras(intent);
        context.startService(service);
    }

    /**
     * @param intent
     * @return
     * @throws NannyException
     */
    protected boolean validateIntent(Intent intent) throws NannyException {
        return Nanny.isIntentFromPermissionNanny(intent);
    }
}
