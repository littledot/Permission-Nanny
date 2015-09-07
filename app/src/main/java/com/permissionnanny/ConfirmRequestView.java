package com.permissionnanny;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class ConfirmRequestView {
    private final Activity mActivity;
    private final ConfirmRequestBinder mBinder;
    private final TextDialogStub mTextStub;

    @Bind(R.id.tvTitle) TextView tvTitle;
    @Bind(R.id.ivIcon) ImageView ivIcon;
    @Bind(R.id.vsStub) ViewStub vsStub;
    @Bind(R.id.btnPositive) Button btnAllow;
    @Bind(R.id.btnNegative) Button btnDeny;

    public ConfirmRequestView(Activity activity, ConfirmRequestBinder binder, TextDialogStub stub) {
        mActivity = activity;
        mBinder = binder;
        mTextStub = stub;
    }

    public void preOnCreate(Bundle saveInstanceState) {
        mActivity.setTheme(R.style.Nanny_Light_Dialog_NoActionBar);
    }

    public void onCreate(Bundle saveInstanceState) {
        mActivity.setContentView(R.layout.dialog);
        ButterKnife.bind(this, mActivity);
        mTextStub.inflateViewStub(vsStub);
        if (Build.VERSION.SDK_INT >= 11) {
            mActivity.setFinishOnTouchOutside(false);
        }
        btnAllow.setText(R.string.dialog_allow);
        btnDeny.setText(R.string.dialog_deny);

        bindViews();
    }

    public void bindViews() {
        tvTitle.setText(mBinder.getDialogTitle());
        Drawable icon = mBinder.getDialogIcon();
        ivIcon.setVisibility(icon == null ? View.GONE : View.VISIBLE);
        ivIcon.setImageDrawable(icon);
        mTextStub.bindViews();
    }

    public void preOnBackPressed() {
        onDeny();
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
