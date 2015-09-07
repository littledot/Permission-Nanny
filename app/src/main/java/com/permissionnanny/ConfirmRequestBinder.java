package com.permissionnanny;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.request.RequestParams;

import javax.inject.Inject;

/**
 *
 */
public class ConfirmRequestBinder extends BaseBinder {
    private Context mContext;
    private PackageManager mPackageMgr;
    private NannyBundle mBundle;
    private String mClientAddr;
    private String mAppPackage;
    private ApplicationInfo mAppInfo;
    private RequestParams mRequest;
    private Operation mOperation;

    @Inject ProxyExecutor mExecutor;

    public ConfirmRequestBinder(Context context, NannyBundle bundle) {
        getComponent(context).inject(this);
        mContext = context;
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

    public void executeAllow() {
        mExecutor.executeAllow(mOperation, mRequest, mClientAddr);
    }

    public void executeDeny() {
        mExecutor.executeDeny(mOperation, mRequest, mClientAddr);
    }
}
