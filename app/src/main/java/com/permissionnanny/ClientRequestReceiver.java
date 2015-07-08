package com.permissionnanny;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.common.IntentUtil;
import com.permissionnanny.lib.InvalidRequestException;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.missioncontrol.PermissionConfig;
import com.permissionnanny.missioncontrol.PermissionConfigDataManager;
import com.permissionnanny.operation.ProxyOperation;
import timber.log.Timber;

import javax.inject.Inject;

/**
 *
 */
public class ClientRequestReceiver extends BroadcastReceiver {

    private static final String NO_ENTITY = "ENTITY_BODY is missing.";
    private static final String NO_CLIENT_PACKAGE = "CLIENT_PACKAGE is missing.";
    private static final String NO_REQUEST_BODY = "REQUEST_PARAMS is missing";
    private static final String UNSUPPORTED_OPCODE = "Requested operation [%s] is unsupported.";

    @Inject PermissionConfigDataManager mConfigManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.wtf("got intent: " + IntentUtil.toString(intent));
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        ProxyExecutor executor = new ProxyExecutor(context);

        // Validate requests and ensure required parameters are present
        String clientAddr = intent.getStringExtra(Nanny.CLIENT_ADDRESS);
        Bundle entity = intent.getBundleExtra(Nanny.ENTITY_BODY);
        if (entity == null) {
            badRequest(context, clientAddr, new InvalidRequestException(NO_ENTITY));
            return;
        }
        PendingIntent sender = entity.getParcelable(PermissionRequest.CLIENT_PACKAGE);
        if (sender == null) {
            badRequest(context, clientAddr, new InvalidRequestException(NO_CLIENT_PACKAGE));
            return;
        }
        RequestParams request = entity.getParcelable(PermissionRequest.REQUEST_PARAMS);
        if (request == null) {
            badRequest(context, clientAddr, new InvalidRequestException(NO_REQUEST_BODY));
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
            badRequest(context, clientAddr, new InvalidRequestException(UNSUPPORTED_OPCODE, request.opCode));
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
            executor.executeAllow(operation, request, clientAddr);
            break;
        case PermissionConfig.ALWAYS_DENY:
            executor.executeDeny(operation, request, clientAddr);
            break;
        }
    }

    private void badRequest(Context context, String clientId, Throwable error) {
        Timber.wtf("err=" + error.getMessage());
        if (clientId != null && !clientId.isEmpty()) {
            Bundle args = ResponseFactory.newBadRequestResponse(error).build();
            Intent response = new Intent(clientId).putExtras(args);
            context.sendBroadcast(response);
        }
    }
}
