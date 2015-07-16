package com.permissionnanny;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.common.IntentUtil;
import com.permissionnanny.lib.InvalidRequestException;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.missioncontrol.PermissionConfig;
import com.permissionnanny.missioncontrol.PermissionConfigDataManager;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * This receiver is part of PPP. It's class name must never change.
 */
@PPP
public class ClientRequestReceiver extends BroadcastReceiver {

    private static final String NO_ENTITY = "ENTITY_BODY is missing.";
    private static final String NO_SENDER_IDENTITY = "SENDER_IDENTITY is missing.";
    private static final String NO_REQUEST_BODY = "REQUEST_PARAMS is missing";
    private static final String UNSUPPORTED_OPCODE = "Requested operation [%s] is unsupported.";

    @Inject PermissionConfigDataManager mConfigManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.wtf("got intent: " + IntentUtil.toString(intent));
        ((App) context.getApplicationContext()).getAppComponent().inject(this);

        // Validate requests and ensure required parameters are present
        String clientAddr = intent.getStringExtra(Nanny.CLIENT_ADDRESS);
        Bundle entity = intent.getBundleExtra(Nanny.ENTITY_BODY);
        if (entity == null) {
            badRequest(context, clientAddr, new InvalidRequestException(NO_ENTITY));
            return;
        }
        PendingIntent client = entity.getParcelable(Nanny.SENDER_IDENTITY);
        if (client == null) {
            badRequest(context, clientAddr, new InvalidRequestException(NO_SENDER_IDENTITY));
            return;
        }
        RequestParams request = entity.getParcelable(Nanny.REQUEST_PARAMS);
        if (request == null) {
            badRequest(context, clientAddr, new InvalidRequestException(NO_REQUEST_BODY));
            return;
        }
        Operation operation = Operation.getOperation(request);
        if (operation == null) {
            badRequest(context, clientAddr, new InvalidRequestException(UNSUPPORTED_OPCODE, request.opCode));
            return;
        }

        String clientPackage = client.getIntentSender().getTargetPackage();
        int userConfig = mConfigManager.getPermissionSetting(clientPackage, operation, request);
        ProxyExecutor executor = new ProxyExecutor(context);
        switch (userConfig) {
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
