package com.permissionnanny.demo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.lib.manifest.PermissionManifestReceiver;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DemoPermissionManifestReceiver extends PermissionManifestReceiver {

    public static final List<Value> data = new ArrayList<>();

    static {
        data.add(new Value(Manifest.permission.ACCESS_FINE_LOCATION));
        data.add(new Value(Manifest.permission.ACCESS_COARSE_LOCATION));
        data.add(new Value(Manifest.permission.READ_CONTACTS));
        data.add(new Value(Manifest.permission.READ_CALENDAR));
        data.add(new Value(Manifest.permission.WRITE_CALENDAR));
        data.add(new Value(Manifest.permission.READ_PHONE_STATE));
        data.add(new Value(Manifest.permission.ACCESS_WIFI_STATE));
    }

    @Override
    protected boolean validateIntent(Intent intent) throws NannyException {
        return true;
    }

    @Override
    protected void setupPermissionUsage(Context context) {
        for (Value value : data) {
            if (value.send) {
                usesPermission(value.permissionName);
            }
        }
    }

    public static class Value {
        String permissionName;
        boolean send = true;

        public Value(String permissionName) {
            this.permissionName = permissionName;
        }
    }
}
