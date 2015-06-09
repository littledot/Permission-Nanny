package com.sdchang.permissionpolice.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.sdchang.permissionpolice.BaseDialogBuilder;
import com.sdchang.permissionpolice.C;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;
import org.apache.http.protocol.HTTP;

/**
 *
 */
public class WifiRequestDialogBuilder extends BaseDialogBuilder<WifiManagerRequest> {

    @StringRes private int mDialogTitle;
    private WifiFunction mFunction;

    public WifiRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);

        for (int i = 0, len = WifiValues.operations.length; i < len; i++) {
            if (WifiValues.operations[i].equals(mRequest.opCode())) {
                mDialogTitle = WifiValues.dialogTitles[i];
                mFunction = WifiValues.functions[i];
                break;
            }
        }
        if (WifiManagerRequest.SET_WIFI_ENABLED.equals(mRequest.opCode())) {
            mDialogTitle = mRequest.boolean0() ? R.string.dialogTitle_wifiSetWifiEnabled_enable :
                    R.string.dialogTitle_wifiSetWifiEnabled_disable;
        }
    }

    @Override
    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return boldAppLabel.append(C.SPACE).append(mActivity.getText(mDialogTitle));
    }

    @Override
    protected Intent onAllowRequest() {
        WifiManager wifi = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
        Bundle response = new Bundle();
        mFunction.execute(wifi, mRequest, response);
        return super.onAllowRequest()
                .putExtra(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE)
                .putExtra(Police.ENTITY_BODY, response);
    }
}
