package com.permissionnanny;

import android.app.PendingIntent;
import android.content.Intent;
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
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.operation.ProxyOperation;
import org.apache.http.protocol.HTTP;
import timber.log.Timber;

/**
 *
 */
public class ConfirmRequestActivity extends BaseActivity {

    @InjectView(R.id.tvTitle) TextView tvTitle;
    @InjectView(R.id.ivIcon) ImageView ivIcon;
    @InjectView(R.id.vsStub) @Optional ViewStub vsStub;
    @InjectView(R.id.btnPositive) Button btnAllow;
    @InjectView(R.id.btnNegative) Button btnDeny;

    private String mClientAddr;
    private String mAppPackage;
    private ApplicationInfo mAppInfo;
    private RequestParams mRequest;
    private ProxyOperation mOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Nanny_Light_Dialog_NoActionBar);
        super.onCreate(savedInstanceState);

        mClientAddr = getIntent().getStringExtra(Nanny.CLIENT_ADDRESS);
        Bundle args = getIntent().getBundleExtra(Nanny.ENTITY_BODY);
        PendingIntent sender = args.getParcelable(PermissionRequest.CLIENT_PACKAGE);
        mAppPackage = sender.getIntentSender().getTargetPackage();
        mAppInfo = Util.getApplicationInfo(this, mAppPackage);
        mRequest = args.getParcelable(PermissionRequest.REQUEST_PARAMS);
        mOperation = ProxyOperation.getOperation(mRequest);

        setContentView(R.layout.dialog);
        ButterKnife.inject(this);
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
        new TextDialogStub().inflateViewStub(vsStub, args);
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
        sendResults(onAllowRequest().build());
    }

    private ResponseBundle onAllowRequest() {
        if (mOperation.mFunction != null) { // one-shot request
            Bundle response = new Bundle();
            try {
                mOperation.mFunction.execute(this, mRequest, response);
            } catch (Throwable error) {
                return ResponseFactory.newBadRequestResponse(error);
            }
            return ResponseFactory.newAllowResponse()
                    .connection(HTTP.CONN_CLOSE)
                    .contentEncoding(Nanny.ENCODING_BUNDLE)
                    .body(response);
        }

        // ongoing request
        Intent server = new Intent(this, ProxyService.class);
        server.putExtra(ProxyService.CLIENT_ID, mClientAddr);
        server.putExtra(ProxyService.REQUEST, mRequest);
        Timber.wtf("Operation.function is null, starting service with args: " + BundleUtil.toString(server));
        startService(server);
        return ResponseFactory.newAllowResponse();
    }

    @OnClick(R.id.btnNegative)
    void onDeny() {
        sendResults(onDenyRequest().build());
    }

    private ResponseBundle onDenyRequest() {
        return new ResponseBundle()
                .server(Nanny.AUTHORIZATION_SERVICE)
                .status(Nanny.SC_FORBIDDEN)
                .connection(Nanny.CLOSE);
    }

    private void sendResults(Bundle response) {
        if (response != null && mClientAddr != null) {
            Timber.d("server broadcasting=" + BundleUtil.toString(response));
            Intent intent = new Intent(mClientAddr).putExtras(response);
            sendBroadcast(intent);
        }
        finish();
    }
}
