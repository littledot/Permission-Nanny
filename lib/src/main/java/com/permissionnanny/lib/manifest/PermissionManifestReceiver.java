package com.permissionnanny.lib.manifest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.lib.C;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyException;

import java.util.ArrayList;

/**
 * @deprecated Use {@link PermissionManifestService} and {@link PermissionManifestServiceLauncherReceiver} instead.
 */
@Deprecated
public class PermissionManifestReceiver extends BroadcastReceiver {

    private ArrayList<String> mPermissions = new ArrayList<>();

    @Override
    public final void onReceive(Context context, Intent intent) {
        // Validate Intent is from Permission Nanny
        try {
            if (!validateIntent(intent)) {
                return;
            }
        } catch (NannyException e) {
            // TODO #72: Think of a good error reporting strategy
            e.printStackTrace();
        }

        setupPermissionUsage(context);

        Bundle entity = new Bundle();
        entity.putParcelable(Nanny.SENDER_IDENTITY, PendingIntent.getBroadcast(context, 0, C.EMPTY_INTENT, 0));
        entity.putStringArrayList(Nanny.PERMISSION_MANIFEST, mPermissions);

        Intent usage = new Intent()
                .setClassName(Nanny.getServerAppId(), Nanny.CLIENT_PERMISSION_MANIFEST_RECEIVER)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.ENTITY_BODY, entity);
        context.sendBroadcast(usage);
    }

    /**
     * @param intent
     * @return
     * @throws NannyException
     */
    protected boolean validateIntent(Intent intent) throws NannyException {
        return Nanny.isIntentFromPermissionNanny(intent);
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
