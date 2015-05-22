package com.sdchang.permissionpolice.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.sdchang.permissionpolice.BaseDialogBuilder;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;

/**
 *
 */
public class WifiRequestDialogBuilder extends BaseDialogBuilder<WifiManagerRequest> {

    public WifiRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
    }

    @Override
    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int string = 0;

        if (WifiManagerRequest.SET_WIFI_ENABLED.equals(mRequest.opCode())) {
            string = mRequest.bool() ? R.string.dialogTitle_wifiSetWifiEnabled_enable :
                    R.string.dialogTitle_wifiSetWifiEnabled_disable;
        } else {
            for (int i = 0, len = WifiValues.operations.length; i < len; i++) {
                if (WifiValues.operations[i].equals(mRequest.opCode())) {
                    string = WifiValues.dialogTitles[i];
                    break;
                }
            }
        }

        return boldAppLabel.append(mActivity.getText(string));
    }

    @Override
    protected Intent onAllowRequest() {
        super.onAllowRequest();
        WifiManager wifi = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
        Bundle response = new Bundle();

        for (int i = 0, len = WifiValues.operations.length; i < len; i++) {
            if (WifiValues.operations[i].equals(mRequest.opCode())) {
                WifiValues.functions[i].execute(wifi, mRequest, response);
                break;
            }
        }

        return new Intent().putExtra(Police.APPROVED, true)
                .putExtra(Police.RESPONSE, response);
    }

    @Override
    protected Intent onDenyRequest() {
        super.onDenyRequest();
        return new Intent().putExtra(Police.APPROVED, false);
    }
}
