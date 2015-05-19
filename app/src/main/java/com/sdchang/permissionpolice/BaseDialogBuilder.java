package com.sdchang.permissionpolice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import timber.log.Timber;

/**
 *
 */
public class BaseDialogBuilder<T extends Parcelable> implements DialogInterface.OnClickListener, DialogInterface
        .OnDismissListener {

    protected final Activity mActivity;
    private final String mReason;
    private final CharSequence mTitle;
    protected final T mRequest;

    public BaseDialogBuilder(Activity activity, Bundle args) {
        mActivity = activity;
        String appPackage = args.getString(BaseRequest.SENDER_PACKAGE);
        mReason = args.getString(BaseRequest.REQUEST_REASON);
        mRequest = args.getParcelable(BaseRequest.REQUEST_BODY);

        CharSequence appLabel;
        try {
            ApplicationInfo senderInfo = activity.getPackageManager().getApplicationInfo(appPackage, 0);
            appLabel = activity.getPackageManager().getApplicationLabel(senderInfo);
        } catch (NameNotFoundException e) {
            Timber.e(e, "senderPackage=%s", appPackage);
            appLabel = appPackage;
        }
        mTitle = buildDialogTitle(appLabel);
    }

    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        return appLabel;
    }

    public AlertDialog build() {
        return new AlertDialog.Builder(mActivity)
                .setTitle(mTitle)
                .setMessage(mReason)
                .setPositiveButton(R.string.dialog_allow, this)
                .setNegativeButton(R.string.dialog_deny, this)
                .setOnDismissListener(this)
                .setCancelable(false)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            onAllowRequest();
            return;
        }
        onDenyRequest();
    }

    protected void onAllowRequest() {
    }

    protected void onDenyRequest() {
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mActivity.finish();
    }
}
