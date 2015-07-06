package com.sdchang.permissionnanny;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewStub;
import com.sdchang.permissionnanny.lib.Nanny;
import com.sdchang.permissionnanny.lib.request.PermissionRequest;
import timber.log.Timber;

/**
 *
 */
public class BaseDialogBuilder<T extends Parcelable> {

    protected final Activity mActivity;
    private final PackageManager mPM;
    private final String mAppPackage;
    ApplicationInfo mAppInfo;
    protected final String mReason;
    /** Action string client is filtering to receive broadcast Intents. */
    protected final String mClientId;
    protected final T mRequest;

    public BaseDialogBuilder(Activity activity, Bundle args) {
        mActivity = activity;
        mPM = mActivity.getPackageManager();

        PendingIntent sender = args.getParcelable(PermissionRequest.SENDER_PACKAGE);
        mAppPackage = sender.getIntentSender().getTargetPackage();
        mReason = args.getString(PermissionRequest.REQUEST_REASON);
        mClientId = args.getString(PermissionRequest.CLIENT_ID);
        mRequest = args.getParcelable(PermissionRequest.REQUEST_BODY);
        Timber.d("clientIntentFilter=" + mClientId);

        try {
            mAppInfo = mPM.getApplicationInfo(mAppPackage, 0);
        } catch (NameNotFoundException e) {
            Timber.e(e, "senderPackage not found=%s", mAppPackage);
        }
    }

    public CharSequence getTitle() {
        CharSequence label = mAppInfo != null ? mPM.getApplicationLabel(mAppInfo) : mAppPackage;
        return buildDialogTitle(label);
    }

    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        return appLabel;
    }

    public Drawable getIcon() {
        return mAppInfo != null ? mPM.getApplicationIcon(mAppInfo) : null;
    }

    public void inflateViewStub(ViewStub stub) {/* Nothing to see here. */}

    protected ResponseBundle onAllowRequest() {
        return null;
    }

    protected ResponseBundle onDenyRequest() {
        return newDenyResponse();
    }

    public final ResponseBundle newAllowResponse() {
        return new ResponseBundle()
                .server(Nanny.AUTHORIZATION_SERVICE)
                .status(Nanny.SC_OK);
    }

    public final ResponseBundle newDenyResponse() {
        return new ResponseBundle()
                .server(Nanny.AUTHORIZATION_SERVICE)
                .status(Nanny.SC_UNAUTHORIZED)
                .connection(Nanny.CLOSE);
    }

    public final ResponseBundle newBadRequestResponse(Throwable error) {
        return new ResponseBundle()
                .server(Nanny.AUTHORIZATION_SERVICE)
                .status(Nanny.SC_BAD_REQUEST)
                .connection(Nanny.CLOSE)
                .contentEncoding(Nanny.ENCODING_SERIALIZABLE)
                .error(error);
    }
}
