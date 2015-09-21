package com.permissionnanny.demo;

import android.content.Context;
import android.content.Intent;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.lib.manifest.PermissionManifestReceiver;

/**
 *
 */
public class DemoPermissionManifestReceiver extends PermissionManifestReceiver {
    @Override
    protected boolean validateIntent(Intent intent) throws NannyException {
        return true;
    }

    @Override
    protected void setupPermissionUsage(Context context) {
        for (DemoPermissionManifestActivity.Value value : DemoPermissionManifestActivity.data) {
            if (value.send) {
                usesPermission(value.permissionName);
            }
        }
    }
}
