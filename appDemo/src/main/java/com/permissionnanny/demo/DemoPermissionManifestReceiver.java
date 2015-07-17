package com.permissionnanny.demo;

import android.Manifest;
import android.content.Context;
import com.permissionnanny.lib.manifest.PermissionManifestReceiver;

/**
 *
 */
public class DemoPermissionManifestReceiver extends PermissionManifestReceiver {

    @Override
    protected void setupPermissionUsage(Context context) {
        usesPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        usesPermission(Manifest.permission.READ_CONTACTS);
        usesPermission(Manifest.permission.READ_PHONE_STATE);
        usesPermission(Manifest.permission.READ_CALENDAR);
        usesPermission(Manifest.permission.READ_SMS);
        usesPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        usesPermission(Manifest.permission.WRITE_CALENDAR);
    }
}