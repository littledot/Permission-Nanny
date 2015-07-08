package com.permissionnanny.lib.registry;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.permissionnanny.lib.Nanny;

import java.util.ArrayList;

/**
 *
 */
public class PermissionUsageReceiver extends BroadcastReceiver {

    private ArrayList<String> mPermissions = new ArrayList<>();

    @Override
    public final void onReceive(Context context, Intent intent) {
        setupPermissionUsage(context);

        Intent usage = new Intent()
                .setClassName(Nanny.SERVER_APP_ID, Nanny.CLIENT_PERMISSION_USAGE_RECEIVER)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_1_0)
                .putExtra(Nanny.CONTENT_TYPE, Nanny.TYPE_STRING_ARRAY_LIST)
                .putExtra(Nanny.CONTENT_ENCODING, Nanny.ENCODING_STRING_ARRAY_LIST);
        // TODO #40: Use PendingIntent to validate identity
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
