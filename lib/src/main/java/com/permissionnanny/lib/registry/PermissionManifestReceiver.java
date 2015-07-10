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
public class PermissionManifestReceiver extends BroadcastReceiver {

    private ArrayList<String> mPermissions = new ArrayList<>();

    @Override
    public final void onReceive(Context context, Intent intent) {
        Bundle entity = intent.getBundleExtra(Nanny.ENTITY_BODY);
        if (entity == null) {
            return;
        }
        PendingIntent sender = entity.getParcelable(Nanny.SENDER_IDENTITY);
        if (sender == null) {
            return;
        }
        String senderPackage = sender.getIntentSender().getTargetPackage();
        if (!Nanny.SERVER_APP_ID.equals(senderPackage)) {
            return;
        }

        setupPermissionUsage(context);

        entity = new Bundle();
        entity.putParcelable(Nanny.SENDER_IDENTITY, PendingIntent.getBroadcast(context, 0, C.EMPTY_INTENT, 0));
        entity.putStringArrayList(Nanny.PERMISSION_MANIFEST, mPermissions);

        Intent usage = new Intent()
                .setClassName(Nanny.SERVER_APP_ID, Nanny.CLIENT_PERMISSION_MANIFEST_RECEIVER)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
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
