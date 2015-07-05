package com.sdchang.permissionpolice;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.sdchang.permissionpolice.common.IntentUtil;
import com.sdchang.permissionpolice.lib.request.PermissionRequest;
import com.sdchang.permissionpolice.lib.request.RequestParams;
import com.sdchang.permissionpolice.missioncontrol.PermissionConfig;
import com.sdchang.permissionpolice.missioncontrol.PermissionConfigDataManager;
import timber.log.Timber;

import javax.inject.Inject;

/**
 *
 */
public class ExternalRequestReceiver extends BroadcastReceiver {

    @Inject PermissionConfigDataManager mConfigManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.wtf("got intent: " + IntentUtil.toString(intent));
        // TODO #37: Figure out how to incorporate user settings into CursorRequests
        int type = intent.getIntExtra(PermissionRequest.REQUEST_TYPE, -1);
        if (type == PermissionRequest.CURSOR_REQUEST) {
            context.startActivity(new Intent(context, ConfirmRequestActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtras(intent));
            return;
        }

        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        ProxyExecutor executor = new ProxyExecutor(context);

        // TODO #2: Validate intents - Ensure parameters are not null
        PendingIntent sender = intent.getParcelableExtra(PermissionRequest.SENDER_PACKAGE);
        String app = sender.getIntentSender().getTargetPackage();
        String clientId = intent.getStringExtra(PermissionRequest.CLIENT_ID);
        RequestParams request = intent.getParcelableExtra(PermissionRequest.REQUEST_BODY);
        ProxyOperation operation = ProxyOperation.getOperation(request);

        // TODO #38: Report "Process is bad" error to AOSP a receiver crashes with NPE
        switch (mConfigManager.getPermissionSetting(app, operation)) {
        case PermissionConfig.ALWAYS_ASK:
            context.startActivity(new Intent(context, ConfirmRequestActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtras(intent));
            break;
        case PermissionConfig.ALWAYS_ALLOW:
            executor.executeAllow(operation, request, clientId);
            break;
        case PermissionConfig.ALWAYS_DENY:
            executor.executeDeny(operation, request, clientId);
            break;
        }
    }
}
