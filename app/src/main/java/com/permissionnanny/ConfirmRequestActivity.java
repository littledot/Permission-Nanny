package com.permissionnanny;

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
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.content.CursorRequestDialogBuilder;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.location.LocationRequestDialogBuilder;
import com.permissionnanny.sms.SmsRequestDialogBuilder;
import com.permissionnanny.telephony.TelephonyRequestDialogBuilder;
import com.permissionnanny.wifi.WifiRequestDialogBuilder;
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
        mClientId = mArgs.getString(PermissionRequest.CLIENT_ID);

        int type = mArgs.getInt(PermissionRequest.REQUEST_TYPE, 0);
        if (type == PermissionRequest.CURSOR_REQUEST) {
            builder = new CursorRequestDialogBuilder(this, mArgs);
        } else if (type == PermissionRequest.WIFI_REQUEST) {
            builder = new WifiRequestDialogBuilder(this, mArgs);
        } else if (type == PermissionRequest.TELEPHONY_REQUEST) {
            builder = new TelephonyRequestDialogBuilder(this, mArgs);
        } else if (type == PermissionRequest.SMS_REQUEST) {
            builder = new SmsRequestDialogBuilder(this, mArgs);
        } else if (type == PermissionRequest.LOCATION_REQUEST) {
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
        onDeny();
        super.onBackPressed();
    }

    @OnClick(R.id.btnPositive)
    void onAllow() {
        sendResults(builder.onAllowRequest().build());
    }

    @OnClick(R.id.btnNegative)
    void onDeny() {
        sendResults(builder.onDenyRequest().build());
    }

    private void sendResults(Bundle response) {
        if (response != null && mClientId != null) {
            Timber.d("server broadcasting=" + BundleUtil.toString(response));
            Intent intent = new Intent(mClientId).putExtras(response);
            sendBroadcast(intent);
        }
        finish();
    }
}
