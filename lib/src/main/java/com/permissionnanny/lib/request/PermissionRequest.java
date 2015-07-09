package com.permissionnanny.lib.request;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.BundleEvent;
import com.permissionnanny.lib.BundleListener;
import com.permissionnanny.lib.C;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.PermissionReceiver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.SecureRandom;

/**
 *
 */
public abstract class PermissionRequest {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SIMPLE_REQUEST, CONTENT_REQUEST})
    public @interface RequestType {}

    public static final int SIMPLE_REQUEST = 1;
    public static final int CONTENT_REQUEST = 2;

    protected PermissionReceiver mReceiver;
    protected RequestParams mParams;

    public PermissionRequest(RequestParams params) {
        mParams = params;
    }

    @RequestType
    public abstract int getRequestType();

    public Intent decorateIntent(Intent intent, Context context, String reason, @Nullable String clientFilter) {
        Bundle entity = new Bundle();
        entity.putParcelable(Nanny.CLIENT_PACKAGE, PendingIntent.getBroadcast(context, 0, C.EMPTY_INTENT, 0));
        entity.putInt(Nanny.REQUEST_TYPE, getRequestType());
        entity.putParcelable(Nanny.REQUEST_PARAMS, mParams);
        entity.putString(Nanny.REQUEST_REASON, reason);

        if (clientFilter != null) {
            intent.putExtra(Nanny.CLIENT_ADDRESS, clientFilter);
        }
        return intent.putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_1_0)
                .putExtra(Nanny.CONTENT_TYPE, Bundle.class.getCanonicalName())
                .putExtra(Nanny.CONTENT_ENCODING, Nanny.ENCODING_BUNDLE)
                .putExtra(Nanny.ENTITY_BODY, entity);
    }

    public Intent newBroadcastIntent(Context context, String reason, @Nullable String clientFilter) {
        Intent intent = new Intent()
                .setClassName(Nanny.SERVER_APP_ID, Nanny.CLIENT_REQUEST_RECEIVER)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        return decorateIntent(intent, context, reason, clientFilter);
    }

    public Intent newBindServiceIntent() {
        return new Intent().setClassName("com.sdchang.permissionpolice",
                "com.sdchang.permissionpolice.ExternalRequestService");
    }

    public PermissionRequest listener(BundleListener listener) {
        return addFilter(new BundleEvent(listener));
    }

    protected PermissionRequest addFilter(Event event) {
        if (mReceiver == null) {
            mReceiver = new PermissionReceiver();
        }
        mReceiver.addFilter(event);
        return this;
    }

    public PermissionRequest startRequest(Context context, String reason) {
        String clientId = null;
        if (mReceiver != null) {
            clientId = Long.toString(new SecureRandom().nextLong());
            context.registerReceiver(mReceiver, new IntentFilter(clientId));
        }
        context.sendBroadcast(newBroadcastIntent(context, reason, clientId));
        return this;
    }
}
