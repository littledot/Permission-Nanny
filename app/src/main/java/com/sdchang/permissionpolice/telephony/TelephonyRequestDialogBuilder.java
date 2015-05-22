package com.sdchang.permissionpolice.telephony;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.sdchang.permissionpolice.BaseDialogBuilder;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyManagerRequest;

/**
 *
 */
public class TelephonyRequestDialogBuilder extends BaseDialogBuilder<TelephonyManagerRequest> {

    @StringRes private int mDialogTitle;
    private TelephonyFunction mFunction;

    public TelephonyRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
        for (int i = 0, len = TelephonyValues.operations.length; i < len; i++) {
            if (TelephonyValues.operations[i].equals(mRequest.opCode())) {
                mDialogTitle = TelephonyValues.dialogTitles[i];
                mFunction = TelephonyValues.functions[i];
                break;
            }
        }
    }

    @Override
    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return boldAppLabel.append(mActivity.getText(mDialogTitle));
    }

    @Override
    protected Intent onAllowRequest() {
        super.onAllowRequest();
        TelephonyManager tele = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
        Bundle response = new Bundle();
        mFunction.execute(tele, mRequest, response);
        return new Intent().putExtra(Police.APPROVED, true)
                .putExtra(Police.RESPONSE, response);
    }

    @Override
    protected Intent onDenyRequest() {
        super.onDenyRequest();
        return new Intent().putExtra(Police.APPROVED, false);
    }
}
