package com.sdchang.permissionnanny.lib.registry;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 *
 */
public class PermissionUsageReceiver extends BroadcastReceiver {

    private ArrayList<String> mPermissions = new ArrayList<>();

    @Override
    public final void onReceive(Context context, Intent intent) {
        setupPermissionUsage(context);

        Intent usage = new Intent("serverPermissionUsage");
        usage.putExtra("senderPackage", context.getPackageName());
        usage.putStringArrayListExtra("permissionUsage", mPermissions);
        context.sendBroadcast(usage);
    }

    /**
     * @param context
     */
    protected void setupPermissionUsage(Context context) {/* Override */}

    /**
     * @param permission
     */
    public void usesPermission(String permission) {
        mPermissions.add(permission);
    }
}
