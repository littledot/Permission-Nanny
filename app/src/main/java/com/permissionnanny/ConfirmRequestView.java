package com.permissionnanny;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 *
 */
public class ConfirmRequestView {
    private final Activity mActivity;
    private final TextDialogStubView mTextStub;

    private ConfirmRequestBinder mBinder;

    @Bind(R.id.tvTitle) TextView tvTitle;
    @Bind(R.id.ivIcon) ImageView ivIcon;
    @Bind(R.id.vsStub) ViewStub vsStub;
    @Bind(R.id.remember) CheckBox cbRemember;
    @Bind(R.id.btnPositive) Button btnAllow;
    @Bind(R.id.btnNegative) Button btnDeny;

    public ConfirmRequestView(Activity activity, ConfirmRequestBinder binder, TextDialogStubView stub) {
        mActivity = activity;
        mBinder = binder;
        mTextStub = stub;
    }

    public void preOnCreate(Bundle state) {
        mActivity.setTheme(R.style.Nanny_Light_Dialog_NoActionBar);
    }

    public void onCreate(Bundle state) {
        mActivity.setContentView(R.layout.dialog);
        ButterKnife.bind(this, mActivity);
        mTextStub.inflateViewStub(vsStub);
        if (Build.VERSION.SDK_INT >= 11) {
            mActivity.setFinishOnTouchOutside(false);
        }
        cbRemember.setText(R.string.dialog_remember);
    }

    public void bindViews() {
        tvTitle.setText(mBinder.getDialogTitle());
        Drawable icon = mBinder.getDialogIcon();
        ivIcon.setVisibility(icon == null ? View.GONE : View.VISIBLE);
        ivIcon.setImageDrawable(icon);
        boolean remember = mBinder.getRememberPreference();
        btnAllow.setText(remember ? R.string.dialog_always_allow : R.string.dialog_allow);
        btnDeny.setText(remember ? R.string.dialog_always_deny : R.string.dialog_deny);
        mTextStub.bindViews();
    }

    @OnCheckedChanged(R.id.remember)
    void onRememberPreferenceChanged(boolean isChecked) {
        mBinder.changeRememberPreference(isChecked);
    }

    @OnClick(R.id.btnPositive)
    void onAllow() {
        mBinder.executeAllow();
        mActivity.finish();
    }

    @OnClick(R.id.btnNegative)
    void onDeny() {
        mBinder.executeDeny();
        mActivity.finish();
    }
}
