package com.sdchang.permissionpolice.lib.request;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import com.sdchang.permissionpolice.lib.BaseHandshakeReceiver;
import com.sdchang.permissionpolice.lib.BundleListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.SecureRandom;

/**
 *
 */
public abstract class BaseRequest implements Parcelable {

    public static final String ACTION = "ppAction";
    public static final String REQUEST_TYPE = "requestType";
    public static final String REQUEST_BODY = "requestBody";
    public static final String REQUEST_REASON = "requestReason";
    public static final String SENDER_PACKAGE = "senderPackage";
    public static final String CLIENT_RECEIVER_INTENT_FILTER = "clientReceiverIntentFilter";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CURSOR_REQUEST, WIFI_REQUEST, TELEPHONY_REQUEST})
    public @interface RequestType {}

    public static final int CURSOR_REQUEST = 1;
    public static final int WIFI_REQUEST = 2;
    public static final int TELEPHONY_REQUEST = 3;

    @RequestType
    public abstract int getRequestType();

    public abstract String getIntentFilter();

    private Intent intent;

    public Intent newIntent(Context context, String reason, String clientFilter) {
        if (intent == null) {
            intent = newBroadcastIntent()
                    .putExtra(SENDER_PACKAGE, context.getPackageName())
                    .putExtra(REQUEST_TYPE, getRequestType())
                    .putExtra(REQUEST_BODY, this)
                    .putExtra(REQUEST_REASON, reason);
            if (clientFilter != null) {
                intent.putExtra(CLIENT_RECEIVER_INTENT_FILTER, clientFilter);
            }
        }
        return intent;
    }

    public Intent newBroadcastIntent() {
        return new Intent(ACTION).setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    }

    public Intent newBindServiceIntent() {
        return new Intent().setClassName("com.sdchang.permissionpolice",
                "com.sdchang.permissionpolice.ExternalRequestService");
    }

    public void startRequest(Context context, String reason) {
        context.sendBroadcast(newIntent(context, reason, null));
    }

    public void startRequest(Context context, String reason, BundleListener listener) {
        String nonce = Long.toString(new SecureRandom().nextLong());
        context.registerReceiver(new BaseHandshakeReceiver(listener), new IntentFilter(nonce));
        context.sendBroadcast(newIntent(context, reason, nonce));
    }
}
