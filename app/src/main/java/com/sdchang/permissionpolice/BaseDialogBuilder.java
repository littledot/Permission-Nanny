package com.sdchang.permissionpolice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import timber.log.Timber;

/**
 *
 */
public class BaseDialogBuilder<T extends Parcelable> implements DialogInterface.OnClickListener, DialogInterface
        .OnDismissListener {

    protected final Activity mActivity;
    private final String mAppPackage;
    private final String mReason;
    /** Action string client is filtering to receive broadcast Intents. */
    protected final String mClientId;
    protected final T mRequest;

    public BaseDialogBuilder(Activity activity, Bundle args) {
        mActivity = activity;
        mAppPackage = args.getString(BaseRequest.SENDER_PACKAGE);
        mReason = args.getString(BaseRequest.REQUEST_REASON);
        mClientId = args.getString(BaseRequest.CLIENT_ID);
        mRequest = args.getParcelable(BaseRequest.REQUEST_BODY);
        Timber.d("clientIntentFilter=" + mClientId);
    }

    public AlertDialog build() {
        CharSequence appLabel;
        try {
            ApplicationInfo senderInfo = mActivity.getPackageManager().getApplicationInfo(mAppPackage, 0);
            appLabel = mActivity.getPackageManager().getApplicationLabel(senderInfo);
        } catch (NameNotFoundException e) {
            Timber.e(e, "senderPackage not found=%s", mAppPackage);
            appLabel = mAppPackage;
        }

        return new AlertDialog.Builder(mActivity)
                .setTitle(buildDialogTitle(appLabel))
                .setMessage(mReason)
                .setPositiveButton(R.string.dialog_allow, this)
                .setNegativeButton(R.string.dialog_deny, this)
                .setOnDismissListener(this)
                .setCancelable(false)
                .create();
    }

    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        return appLabel;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent response;
        if (which == DialogInterface.BUTTON_POSITIVE) {
            response = onAllowRequest();
        } else {
            response = onDenyRequest();
        }

        if (response != null && mClientId != null) {
            response.setAction(mClientId);
            Timber.d("server broadcasting=" + response);
            mActivity.sendBroadcast(response);
        }
    }

    protected Intent onAllowRequest() {
        return new Intent().putExtra(Police.HTTP_VERSION, Police.HTTP_1_1)
                .putExtra(Police.SERVER, Police.AUTHORIZATION_SERVICE)
                .putExtra(Police.STATUS_CODE, HttpStatus.SC_OK);
    }

    protected Intent onDenyRequest() {
        return new Intent().putExtra(Police.HTTP_VERSION, Police.HTTP_1_1)
                .putExtra(Police.SERVER, Police.AUTHORIZATION_SERVICE)
                .putExtra(Police.STATUS_CODE, HttpStatus.SC_UNAUTHORIZED)
                .putExtra(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mActivity.finish();
    }
}
