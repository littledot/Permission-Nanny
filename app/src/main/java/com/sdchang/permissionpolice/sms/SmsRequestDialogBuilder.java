package com.sdchang.permissionpolice.sms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.telephony.SmsManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.sdchang.permissionpolice.BaseDialogBuilder;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.sms.SmsRequest;
import org.apache.http.protocol.HTTP;

/**
 *
 */
public class SmsRequestDialogBuilder extends BaseDialogBuilder<SmsRequest> {

    @StringRes private int mDialogTitle;
    private SmsFunction mFunction;

    public SmsRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
        for (int i = 0, len = SmsValues.operations.length; i < len; i++) {
            if (SmsValues.operations[i].equals(mRequest.opCode())) {
                mDialogTitle = SmsValues.dialogTitles[i];
                mFunction = SmsValues.functions[i];
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
        SmsManager sms = SmsManager.getDefault();
        Bundle response = new Bundle();
        mFunction.execute(sms, mRequest, response);
        return super.onAllowRequest()
                .putExtra(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE)
                .putExtra(Police.ENTITY_BODY, response);
    }
}
