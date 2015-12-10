package com.permissionnanny;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.common.IntentUtil;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.missioncontrol.AppControlActivity;
import timber.log.Timber;

/**
 * This receiver is part of PPP. It's class name must never change.
 */
@PPP
public class ClientDeepLinkReceiver extends BaseReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.wtf("got intent: " + IntentUtil.toString(intent));
        super.onReceive(context, intent);

        NannyBundle bundle = new NannyBundle(intent.getExtras());

        // Validate feral request and ensure required parameters are present
        String clientAddr = bundle.getClientAddress();
        String clientPackage = bundle.getSenderIdentity();
        if (clientPackage == null) {
            badRequest(context, clientAddr, new NannyException(Err.NO_SENDER_IDENTITY));
            return;
        }

        switch (bundle.getDeepLinkTarget()) {
            case Nanny.MANAGE_APPLICATIONS_SETTINGS:
                context.startActivity(new Intent(context, AppControlActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            default:
                badRequest(context, clientAddr, new NannyException(Err.UNSUPPORTED_DEEP_LINK_TARGET));
                return;
        }
        okRequest(context, clientAddr);
    }

    private void okRequest(Context context, String clientAddr) {
        if (clientAddr != null && !clientAddr.isEmpty()) {
            Bundle args = ResponseFactory.newAllowResponse(Nanny.AUTHORIZATION_SERVICE).connection(Nanny.CLOSE).build();
            Intent response = new Intent(clientAddr).putExtras(args);
            context.sendBroadcast(response);
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
