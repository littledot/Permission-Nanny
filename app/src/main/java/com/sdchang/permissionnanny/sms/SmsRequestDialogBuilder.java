package com.sdchang.permissionnanny.sms;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.sdchang.permissionnanny.BaseDialogBuilder;
import com.sdchang.permissionnanny.C;
import com.sdchang.permissionnanny.ProxyOperation;
import com.sdchang.permissionnanny.R;
import com.sdchang.permissionnanny.ResponseBundle;
import com.sdchang.permissionnanny.lib.Nanny;
import com.sdchang.permissionnanny.lib.request.RequestParams;
import org.apache.http.protocol.HTTP;

/**
 *
 */
public class SmsRequestDialogBuilder extends BaseDialogBuilder<RequestParams> {

    private ProxyOperation mOperation;

    @InjectView(R.id.tvReason) TextView tvReason;

    public SmsRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
        mOperation = SmsOperation.getOperation(mRequest.opCode);
    }

    @Override
    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return boldAppLabel.append(C.SPACE).append(mActivity.getText(mOperation.mDialogTitle));
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
        Bundle response = new Bundle();
        try {
            mOperation.mFunction.execute(mActivity, mRequest, response);
        } catch (Throwable error) {
            return newBadRequestResponse(error);
        }
        return newAllowResponse()
                .connection(HTTP.CONN_CLOSE)
                .contentType(Nanny.APPLICATION_BUNDLE)
                .body(response);
    }
}
