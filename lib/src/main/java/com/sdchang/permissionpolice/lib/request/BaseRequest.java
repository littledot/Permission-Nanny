package com.sdchang.permissionpolice.lib.request;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.IntDef;

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

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CURSOR_REQUEST})
    public @interface RequestType {}

    public static final int CURSOR_REQUEST = 1;

    @RequestType
    public abstract int getRequestType();

    private Intent intent;

    public Intent newIntent(Context context, String reason) {
        if (intent == null) {
            intent = new Intent(ACTION)
                    .putExtra(REQUEST_TYPE, getRequestType())
                    .putExtra(REQUEST_BODY, this)
                    .putExtra(REQUEST_REASON, reason);
        }
        return intent;
    }

    public void startRequest(Context context, String reason) {
        context.sendBroadcast(newIntent(context, reason));
    }
}
