package com.sdchang.permissionpolice.request;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.VerificationService;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
public abstract class BaseRequest implements Parcelable {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CURSOR_REQUEST})
    public @interface RequestType {}

    public static final int CURSOR_REQUEST = 1;

    @RequestType
    public abstract int getRequestType();

    private Intent intent;

    public Intent newIntent(Context context) {
        if (intent == null) {
            intent = new Intent(context.getString(R.string.sdkAction))
                    .putExtra(VerificationService.REQUEST_TYPE, getRequestType())
                    .putExtra(VerificationService.REQUEST, this);
        }
        return intent;
    }
}
