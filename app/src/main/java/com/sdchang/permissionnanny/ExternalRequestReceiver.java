package com.sdchang.permissionnanny;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.sdchang.permissionnanny.common.IntentUtil;
import com.sdchang.permissionnanny.lib.request.PermissionRequest;
import com.sdchang.permissionnanny.lib.request.RequestParams;
import com.sdchang.permissionnanny.missioncontrol.PermissionConfig;
import com.sdchang.permissionnanny.missioncontrol.PermissionConfigDataManager;
import timber.log.Timber;

import javax.inject.Inject;

/**
 *
 */
public class ExternalRequestReceiver extends BroadcastReceiver {

    private static final String NO_SENDER_PACKAGE = "SENDER_PACKAGE is missing.";
    private static final String NO_REQUEST_BODY = "REQUEST_BODY is missing";
    private static final String UNSUPPORTED_OPCODE = "Requested operation [%s] is unsupported.";

    @Inject PermissionConfigDataManager mConfigManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.wtf("got intent: " + IntentUtil.toString(intent));
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        ProxyExecutor executor = new ProxyExecutor(context);

        // Validate requests and ensure required parameters are present
        String clientId = intent.getStringExtra(PermissionRequest.CLIENT_ID);
        RequestParams request = intent.getParcelableExtra(PermissionRequest.REQUEST_BODY);
        if (request == null) {
            badRequest(context, clientId, new InvalidRequestException(NO_REQUEST_BODY));
            return;
        }
        PendingIntent sender = intent.getParcelableExtra(PermissionRequest.SENDER_PACKAGE);
        if (sender == null) {
            badRequest(context, clientId, new InvalidRequestException(NO_SENDER_PACKAGE));
            return;
        }
        String clientPackage = sender.getIntentSender().getTargetPackage();

        // TODO #37: Figure out how to incorporate user settings into CursorRequests
        int type = intent.getIntExtra(PermissionRequest.REQUEST_TYPE, -1);
        if (type == PermissionRequest.CURSOR_REQUEST) {
            context.startActivity(new Intent(context, ConfirmRequestActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtras(intent));
            return;
        }

        ProxyOperation operation = ProxyOperation.getOperation(request);
        if (operation == null) {
            badRequest(context, clientId, new InvalidRequestException(UNSUPPORTED_OPCODE, request.opCode));
            return;
        }

        // TODO #38: Report "Process is bad" error to AOSP a receiver crashes with NPE
        switch (mConfigManager.getPermissionSetting(clientPackage, operation)) {
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

    private void badRequest(Context context, String clientId, Throwable error) {
        if (clientId != null && !clientId.isEmpty()) {
            Bundle args = ResponseFactory.newBadRequestResponse(error).build();
            Intent response = new Intent(clientId).putExtras(args);
            context.sendBroadcast(response);
        }
    }
}
