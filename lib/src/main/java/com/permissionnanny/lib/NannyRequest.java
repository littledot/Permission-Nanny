package com.permissionnanny.lib;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.request.PermissionReceiver;
import java.security.SecureRandom;

/**
 *
 */
public abstract class NannyRequest {
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
        Bundle entity = new Bundle();
        entity.putSerializable(Nanny.ENTITY_ERROR, new NannyException("Permission Nanny is not installed."));
        return new Intent()
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_NOT_FOUND)
                .putExtra(Nanny.SERVER, Nanny.AUTHORIZATION_SERVICE)
                .putExtra(Nanny.ENTITY_BODY, entity);
    }
}
