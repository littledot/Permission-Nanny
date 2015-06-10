package com.sdchang.permissionpolice.telephony;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.sdchang.permissionpolice.BaseDialogBuilder;
import com.sdchang.permissionpolice.C;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyManagerRequest;
import org.apache.http.protocol.HTTP;

/**
 *
 */
public class TelephonyRequestDialogBuilder extends BaseDialogBuilder<TelephonyManagerRequest> {

    private TelephonyOperation mOperation;

    public TelephonyRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
        for (TelephonyOperation operation : TelephonyOperation.operations) {
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
        return boldAppLabel.append(C.SPACE).append(mActivity.getText(mOperation.mDialogTitle));
    }

    @Override
    protected Intent onAllowRequest() {
        TelephonyManager tele = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
        Bundle response = new Bundle();
        mOperation.mFunction.execute(tele, mRequest, response);
        return super.onAllowRequest()
                .putExtra(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE)
                .putExtra(Police.ENTITY_BODY, response);
    }
}
