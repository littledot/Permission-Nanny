package com.permissionnanny;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import com.permissionnanny.common.IntentUtil;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.data.AppPermission;
import com.permissionnanny.data.AppPermissionManager;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * This receiver is part of PPP. Its class name must never change.
 */
@PPP
public class ClientRequestReceiver extends BaseReceiver {

    @Inject AppPermissionManager mAppManager;
    @Inject ProxyExecutor mExecutor;

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.wtf("got intent: " + IntentUtil.toString(intent));
        super.onReceive(context, intent);
        getComponent(context).inject(this);

        NannyBundle bundle = new NannyBundle(intent.getExtras());

        // Validate feral request and ensure required parameters are present
        String clientAddr = bundle.getClientAddress();
        String clientPackage = bundle.getSenderIdentity();
        if (clientPackage == null) {
            badRequest(context, clientAddr, new NannyException(Err.NO_SENDER_IDENTITY));
            return;
        }
        RequestParams request = bundle.getRequest();
        if (request == null) {
            badRequest(context, clientAddr, new NannyException(Err.NO_REQUEST_PARAMS));
            return;
        }
        Operation operation = Operation.getOperation(request);
        if (operation == null) {
            badRequest(context, clientAddr, new NannyException(Err.UNSUPPORTED_OPCODE, request.opCode));
            return;
        }

        // NORMAL operation? Automatically allow
        if (operation.mProtectionLevel == PermissionInfo.PROTECTION_NORMAL) {
            mExecutor.executeAllow(operation, request, clientAddr);
            return;
        }

        // DANGEROUS operation? Check user's config first
        int userConfig = mAppManager.getPermissionPrivilege(clientPackage, operation, request);
        switch (userConfig) {
        case AppPermission.ALWAYS_ASK:
            context.startActivity(new Intent(context, ConfirmRequestActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtras(intent));
            break;
        case AppPermission.ALWAYS_ALLOW:
            mExecutor.executeAllow(operation, request, clientAddr);
            break;
        case AppPermission.ALWAYS_DENY:
            mExecutor.executeDeny(operation, request, clientAddr);
            break;
        }
    }

    private void badRequest(Context context, String clientAddr, Throwable error) {
        Timber.wtf("err=" + error.getMessage());
        if (clientAddr != null && !clientAddr.isEmpty()) {
            Bundle args = ResponseFactory.newBadRequestResponse(Nanny.AUTHORIZATION_SERVICE, error).build();
            Intent response = new Intent(clientAddr).putExtras(args);
            context.sendBroadcast(response);
        }
    }
}
