package com.permissionnanny;

import android.app.PendingIntent;
import android.content.pm.ApplicationInfo;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public class ConfirmRequestActivity extends BaseActivity {

    @Bind(R.id.tvTitle) TextView tvTitle;
    @Bind(R.id.ivIcon) ImageView ivIcon;
    @Bind(R.id.vsStub) ViewStub vsStub;
    @Bind(R.id.btnPositive) Button btnAllow;
    @Bind(R.id.btnNegative) Button btnDeny;

    private ProxyExecutor mExecutor;
    private String mClientAddr;
    private String mAppPackage;
    private ApplicationInfo mAppInfo;
    private RequestParams mRequest;
    private Operation mOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Nanny_Light_Dialog_NoActionBar);
        super.onCreate(savedInstanceState);
        mExecutor = new ProxyExecutor(this);

        mClientAddr = getIntent().getStringExtra(Nanny.CLIENT_ADDRESS);
        Bundle entity = getIntent().getBundleExtra(Nanny.ENTITY_BODY);
        PendingIntent sender = entity.getParcelable(PermissionRequest.CLIENT_PACKAGE);
        mAppPackage = sender.getIntentSender().getTargetPackage();
        mAppInfo = Util.getApplicationInfo(this, mAppPackage);
        mRequest = entity.getParcelable(PermissionRequest.REQUEST_PARAMS);
        int type = entity.getInt(PermissionRequest.REQUEST_TYPE, -1);
        mOperation = Operation.getOperation(mRequest, type);

        setContentView(R.layout.dialog);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 11) {
            setFinishOnTouchOutside(false);
        }
        btnAllow.setText(R.string.dialog_allow);
        btnDeny.setText(R.string.dialog_deny);
        tvTitle.setText(getDialogTitle());
        Drawable icon = getDialogIcon();
        if (icon == null) {
            ivIcon.setVisibility(View.GONE);
        } else {
            ivIcon.setImageDrawable(icon);
        }
        new TextDialogStub().inflateViewStub(vsStub, entity);
    }

    private CharSequence getDialogTitle() {
        CharSequence label = mAppInfo != null ? getPackageManager().getApplicationLabel(mAppInfo) : mAppPackage;
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(label);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return boldAppLabel.append(C.SPACE).append(getText(mOperation.mDialogTitle));
    }

    private Drawable getDialogIcon() {
        return mAppInfo != null ? getPackageManager().getApplicationIcon(mAppInfo) : null;
    }

    @Override
    public void onBackPressed() {
        onDeny();
        super.onBackPressed();
    }

    @OnClick(R.id.btnPositive)
    void onAllow() {
        mExecutor.executeAllow(mOperation, mRequest, mClientAddr);
        finish();
    }

    @OnClick(R.id.btnNegative)
    void onDeny() {
        mExecutor.executeDeny(mOperation, mRequest, mClientAddr);
        finish();
    }
}
