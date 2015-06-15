package com.sdchang.permissionpolice;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.sdchang.permissionpolice.content.CursorRequestDialogBuilder;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.location.LocationRequestDialogBuilder;
import com.sdchang.permissionpolice.sms.SmsRequestDialogBuilder;
import com.sdchang.permissionpolice.telephony.TelephonyRequestDialogBuilder;
import com.sdchang.permissionpolice.wifi.WifiRequestDialogBuilder;
import timber.log.Timber;

/**
 *
 */
public class ConfirmRequestActivity extends BaseActivity {
    private Bundle mArgs;
    /** Action string client is filtering to receive broadcast intents. */
    private String mClientId;

    @InjectView(R.id.tvTitle) TextView tvTitle;
    @InjectView(R.id.ivIcon) ImageView ivIcon;
    @InjectView(R.id.vsStub) ViewStub vsStub;
    @InjectView(R.id.btnPositive) Button btnAllow;
    @InjectView(R.id.btnNegative) Button btnDeny;
    BaseDialogBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Nanny_Light_Dialog_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        ButterKnife.inject(this);
        if (Build.VERSION.SDK_INT >= 11) {
            setFinishOnTouchOutside(false);
        }

        mArgs = getIntent().getExtras();
        mClientId = mArgs.getString(BaseRequest.CLIENT_ID);

        int type = mArgs.getInt(BaseRequest.REQUEST_TYPE, 0);
        if (type == BaseRequest.CURSOR_REQUEST) {
            builder = new CursorRequestDialogBuilder(this, mArgs);
        } else if (type == BaseRequest.WIFI_REQUEST) {
            builder = new WifiRequestDialogBuilder(this, mArgs);
        } else if (type == BaseRequest.TELEPHONY_REQUEST) {
            builder = new TelephonyRequestDialogBuilder(this, mArgs);
        } else if (type == BaseRequest.SMS_REQUEST) {
            builder = new SmsRequestDialogBuilder(this, mArgs);
        } else if (type == BaseRequest.LOCATION_REQUEST) {
            builder = new LocationRequestDialogBuilder(this, mArgs);
        }

        btnAllow.setText(R.string.dialog_allow);
        btnDeny.setText(R.string.dialog_deny);
        tvTitle.setText(builder.getTitle());
        ivIcon.setImageDrawable(builder.getIcon());
        builder.inflateViewStub(vsStub);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDeny();
    }

    @OnClick(R.id.btnPositive)
    void onAllow() {
        sendResults(builder.onAllowRequest());
    }

    @OnClick(R.id.btnNegative)
    void onDeny() {
        sendResults(builder.onDenyRequest());
    }

    private void sendResults(Intent response) {
        if (response != null && mClientId != null) {
            response.setAction(mClientId);
            Timber.d("server broadcasting=" + response);
            sendBroadcast(response);
        }
        finish();
    }
}
