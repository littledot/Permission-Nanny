package com.sdchang.permissionpolice;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewStub;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
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

        mAppPackage = args.getString(BaseRequest.SENDER_PACKAGE);
        mReason = args.getString(BaseRequest.REQUEST_REASON);
        mClientId = args.getString(BaseRequest.CLIENT_ID);
        mRequest = args.getParcelable(BaseRequest.REQUEST_BODY);
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
                .server(Police.AUTHORIZATION_SERVICE)
                .status(HttpStatus.SC_OK);
    }

    public final ResponseBundle newDenyResponse() {
        return new ResponseBundle()
                .server(Police.AUTHORIZATION_SERVICE)
                .status(HttpStatus.SC_UNAUTHORIZED)
                .connection(HTTP.CONN_CLOSE);
    }

    public final ResponseBundle newBadRequestResponse(Throwable error) {
        return new ResponseBundle()
                .server(Police.AUTHORIZATION_SERVICE)
                .status(HttpStatus.SC_BAD_REQUEST)
                .connection(HTTP.CONN_CLOSE)
                .contentType(Police.APPLICATION_SERIALIZABLE)
                .error(error);
    }
}
