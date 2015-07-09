package com.permissionnanny.lib.registry;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.lib.C;
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

        Bundle entity = new Bundle();
        entity.putParcelable(Nanny.CLIENT_PACKAGE, PendingIntent.getBroadcast(context, 0, C.EMPTY_INTENT, 0));
        entity.putStringArrayList(Nanny.PERMISSION_MANIFEST, mPermissions);

        Intent usage = new Intent()
                .setClassName(Nanny.SERVER_APP_ID, Nanny.CLIENT_PERMISSION_USAGE_RECEIVER)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_1_0)
                .putExtra(Nanny.CONTENT_TYPE, Bundle.class.getCanonicalName())
                .putExtra(Nanny.CONTENT_ENCODING, Nanny.ENCODING_BUNDLE)
                .putExtra(Nanny.ENTITY_BODY, entity);
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
