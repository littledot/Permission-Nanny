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

/**
 *
 */
public abstract class BaseRequest implements Parcelable {

    public static final String ACTION = "ppAction";
    public static final String REQUEST_TYPE = "requestType";
    public static final String REQUEST_BODY = "requestBody";
    public static final String REQUEST_REASON = "requestReason";
    public static final String SENDER_PACKAGE = "senderPackage";

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

    public Intent newIntent(Context context, String reason) {
        if (intent == null) {
            intent = new Intent(ACTION)
                    .putExtra(SENDER_PACKAGE, context.getPackageName())
                    .putExtra(REQUEST_TYPE, getRequestType())
                    .putExtra(REQUEST_BODY, this)
                    .putExtra(REQUEST_REASON, reason);
        }
        return intent;
    }

    public void startRequest(Context context, String reason) {
        context.sendBroadcast(newIntent(context, reason));
    }

    public void startRequest(Context context, String reason, BundleListener listener) {
        context.registerReceiver(new BaseHandshakeReceiver(listener), new IntentFilter(getIntentFilter()));
        startRequest(context, reason);
    }
}
