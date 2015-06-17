package com.sdchang.permissionpolice.wifi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.sdchang.permissionpolice.BaseDialogBuilder;
import com.sdchang.permissionpolice.C;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.ResponseBundle;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;
import org.apache.http.protocol.HTTP;

/**
 *
 */
public class WifiRequestDialogBuilder extends BaseDialogBuilder<WifiManagerRequest> {

    private WifiOperation mOperation;

    @InjectView(R.id.tvReason) TextView tvReason;

    public WifiRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
        for (WifiOperation operation : WifiOperation.operations) {
            if (operation.mOpCode.equals(mRequest.opCode())) {
                mOperation = operation;
                break;
            }
        }
    }

    @Override
    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int dialogTitle = mOperation.mDialogTitle;
        if (WifiManagerRequest.SET_WIFI_ENABLED.equals(mRequest.opCode())) {
            dialogTitle = mRequest.boolean0() ? R.string.dialogTitle_wifiSetWifiEnabled_enable :
                    R.string.dialogTitle_wifiSetWifiEnabled_disable;
        }
        return boldAppLabel.append(C.SPACE).append(mActivity.getText(dialogTitle));
    }

    @Override
    public void inflateViewStub(ViewStub stub) {
        stub.setLayoutResource(R.layout.dialog_text);
        View view = stub.inflate();
        ButterKnife.inject(this, view);
        tvReason.setText(mReason);
    }

    @Override
    protected ResponseBundle onAllowRequest() {
        WifiManager wifi = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
        Bundle response = new Bundle();
        try {
            mOperation.mFunction.execute(wifi, mRequest, response);
        } catch (Throwable error) {
            return newBadRequestResponse(error);
        }
        return newAllowResponse()
                .connection(HTTP.CONN_CLOSE)
                .contentType(Police.APPLICATION_BUNDLE)
                .body(response);
    }
}
