package com.permissionnanny.lib;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.request.PermissionReceiver;
import java.security.SecureRandom;

/**
 * The root class of all requests.
 */
public class NannyRequest {

    protected final String mClientAddr;
    @Nullable private PermissionReceiver mReceiver;
    @Nullable private Intent mPaylaod;

    public NannyRequest() {
        mClientAddr = Long.toString(new SecureRandom().nextLong());
    }

    protected NannyRequest addFilter(@Nullable Event event) {
        if (event == null) {
            return this;
        }
        if (mReceiver == null) {
            mReceiver = new PermissionReceiver();
        }
        mReceiver.addFilter(event);
        return this;
    }

    protected boolean hasReceiver() {
        return mReceiver != null;
    }

    protected void setPayload(Intent payload) {
        mPaylaod = payload;
    }

    protected void startRequest(Context context) {
        if (!Nanny.isPermissionNannyInstalled(context)) {
            if (mReceiver != null) {
                mReceiver.onReceive(context, newNotFoundIntent());
            }
            return;
        }
        if (mPaylaod == null) {
            throw new IllegalStateException("No payload.");
        }
        if (mReceiver != null) {
            context.registerReceiver(mReceiver, new IntentFilter(mClientAddr));
        }
        context.sendBroadcast(mPaylaod);
    }

    protected void stop(Context context) {
        if (mReceiver != null) {
            context.unregisterReceiver(mReceiver);
        }
    }

    private Intent newNotFoundIntent() {
        return new Intent().putExtras(new NannyBundle.Builder()
                .statusCode(Nanny.SC_NOT_FOUND)
                .server(Nanny.AUTHORIZATION_SERVICE)
                .error(new NannyException("Permission Nanny is not installed."))
                .build());
    }
}
