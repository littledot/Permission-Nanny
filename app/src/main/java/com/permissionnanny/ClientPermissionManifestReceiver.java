package com.permissionnanny;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.lib.PPP;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This receiver is part of PPP. It's class name must never change.
 */
@PPP
public class ClientPermissionManifestReceiver extends BaseReceiver {

    @Inject AppPermissionManager mAppManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        getComponent(context).inject(this);

        // Validate feral request and ensure required parameters are present
        String clientAddr = intent.getStringExtra(Nanny.CLIENT_ADDRESS);
        Bundle entity = intent.getBundleExtra(Nanny.ENTITY_BODY);
        if (entity == null) {
            badRequest(context, clientAddr, new NannyException(Err.NO_ENTITY));
            return;
        }
        PendingIntent client = entity.getParcelable(Nanny.SENDER_IDENTITY);
        if (client == null) {
            badRequest(context, clientAddr, new NannyException(Err.NO_SENDER_IDENTITY));
            return;
        }
        ArrayList<String> permissionUsage = entity.getStringArrayList(Nanny.PERMISSION_MANIFEST);
        if (permissionUsage == null) {
            badRequest(context, clientAddr, new NannyException(Err.NO_PERMISSION_MANIFEST));
            return;
        }

        String clientPackage = client.getIntentSender().getTargetPackage();
        Timber.wtf("client=" + clientPackage + " usage=" + Arrays.toString(permissionUsage.toArray()));
        mAppManager.registerApp(clientPackage, permissionUsage);

        okRequest(context, clientAddr);
    }

    private void badRequest(Context context, String clientAddr, Throwable error) {
        Timber.wtf("err=" + error.getMessage());
        if (clientAddr != null && !clientAddr.isEmpty()) {
            Bundle payload = ResponseFactory.newBadRequestResponse(Nanny.PERMISSION_MANIFEST_SERVICE, error).build();
            Intent response = new Intent(clientAddr).putExtras(payload);
            context.sendBroadcast(response);
        }
    }

    private void okRequest(Context context, String clientAddr) {
        if (clientAddr != null && !clientAddr.isEmpty()) {
            NannyBundle.Builder payload = ResponseFactory.newAllowResponse(Nanny.PERMISSION_MANIFEST_SERVICE);
            payload.mConnection = Nanny.CLOSE;
            Intent response = new Intent(clientAddr).putExtras(payload.build());
            context.sendBroadcast(response);
        }
    }
}
