package com.permissionnanny;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.missioncontrol.PermissionConfigDataManager;

import javax.inject.Inject;

/**
 *
 */
public class ConfirmRequestBinder extends BaseBinder {

    @VisibleForTesting ConfirmRequestView mView;

    private Context mContext;
    private PackageManager mPackageMgr;
    private NannyBundle mBundle;
    private String mClientAddr;
    private String mAppPackage;
    private ApplicationInfo mAppInfo;
    private RequestParams mRequest;
    private Operation mOperation;

    private boolean mRemember;

    @Inject ProxyExecutor mExecutor;
    @Inject PermissionConfigDataManager mConfig;

    public ConfirmRequestBinder(Activity activity, NannyBundle bundle) {
        getComponent(activity).inject(this);
        mView = new ConfirmRequestView(activity, this, new TextDialogStubView(this));
        mContext = activity;
        mPackageMgr = mContext.getPackageManager();
        mBundle = bundle;
        mClientAddr = mBundle.getClientAddress();
        mAppPackage = mBundle.getSenderIdentity();
        mAppInfo = Util.getApplicationInfo(mContext, mAppPackage);
        mRequest = mBundle.getRequest();
        mOperation = Operation.getOperation(mRequest);
    }

    public Spanned getDialogTitle() {
        CharSequence label = mAppInfo != null && (label = mPackageMgr.getApplicationLabel(mAppInfo)) != null ?
                label : mAppPackage;
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(label);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return boldAppLabel.append(' ').append(mContext.getText(mOperation.mDialogTitle));
    }

    public Drawable getDialogIcon() {
        return mAppInfo != null ? mPackageMgr.getApplicationIcon(mAppInfo) : null;
    }

    public CharSequence getDialogBody() {
        return mBundle.getRequestRationale();
    }

    public boolean getRememberPreference() {
        return mRemember;
    }

    public void preOnCreate(Bundle state) {
        mView.preOnCreate(state);
    }

    public void onCreate(Bundle state) {
        mView.onCreate(state);
        mView.bindViews();
    }

    public void onBackPressed() {
        executeDeny();
    }

    public void changeRememberPreference(boolean remember) {
        mRemember = remember;
        mView.bindViews();
    }

    public void executeAllow() {
        mExecutor.executeAllow(mOperation, mRequest, mClientAddr);
    }

    public void executeDeny() {
        mExecutor.executeDeny(mOperation, mRequest, mClientAddr);
    }
}
