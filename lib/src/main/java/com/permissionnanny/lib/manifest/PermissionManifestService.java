package com.permissionnanny.lib.manifest;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.os.IBinder;
import com.permissionnanny.lib.C;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.PermissionReceiver;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 *
 */
public class PermissionManifestService extends Service {

    private ArrayList<String> mPermissions;

    private String mClientAddr;
    private PermissionReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mPermissions = new ArrayList<>();
        mClientAddr = Long.toString(new SecureRandom().nextLong());
        mReceiver = new PermissionReceiver().addFilter(new ManifestEvent());
        registerReceiver(mReceiver, new IntentFilter(mClientAddr));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupPermissionUsage(this);

        Bundle entity = new Bundle();
        entity.putParcelable(Nanny.SENDER_IDENTITY, PendingIntent.getBroadcast(this, 0, C.EMPTY_INTENT, 0));
        entity.putStringArrayList(Nanny.PERMISSION_MANIFEST, mPermissions);

        Intent manifest = new Intent()
                .setClassName(Nanny.getServerAppId(), Nanny.CLIENT_PERMISSION_MANIFEST_RECEIVER)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.CLIENT_ADDRESS, mClientAddr)
                .putExtra(Nanny.ENTITY_BODY, entity);
        sendBroadcast(manifest);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * Override this method and invoke {@link #usesPermission(String)} to declare permissions your app plans to use.
     * Permission Nanny will display requested permissions whose protection level is {@link
     * PermissionInfo#PROTECTION_DANGEROUS} in mission control, where users can toggle 'Always Ask', 'Always Allow' and
     * 'Always Deny'.
     *
     * @param context Service context
     */
    protected void setupPermissionUsage(Context context) {/* Override */}

    /**
     * @param intent
     */
    protected void onResponse(Intent intent) {/* Override */}

    /**
     * @param permission
     */
    public void usesPermission(String permission) {
        mPermissions.add(permission);
    }

    private class ManifestEvent implements Event {
        @Override
        public String filter() {
            return "ManifestEvent";
        }

        @Override
        public void process(Context context, Intent intent) {
            onResponse(intent);
        }
    }
}
