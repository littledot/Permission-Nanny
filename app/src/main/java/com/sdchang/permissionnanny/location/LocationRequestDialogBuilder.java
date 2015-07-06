package com.sdchang.permissionnanny.location;

import android.app.Activity;
import android.content.Intent;
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
import com.sdchang.permissionnanny.ProxyService;
import com.sdchang.permissionnanny.R;
import com.sdchang.permissionnanny.ResponseBundle;
import com.sdchang.permissionnanny.common.BundleUtil;
import com.sdchang.permissionnanny.lib.Nanny;
import com.sdchang.permissionnanny.lib.request.RequestParams;
import org.apache.http.protocol.HTTP;
import timber.log.Timber;

/**
 *
 */
public class LocationRequestDialogBuilder extends BaseDialogBuilder<RequestParams> {

    private ProxyOperation mOperation;

    @InjectView(R.id.tvReason) TextView tvReason;

    public LocationRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
        mOperation = LocationOperation.getOperation(mRequest.opCode);
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
        if (mOperation.mFunction != null) { // one-shot request
            Bundle response = new Bundle();
            try {
                mOperation.mFunction.execute(mActivity, mRequest, response);
            } catch (Throwable error) {
                return newBadRequestResponse(error);
            }
            return newAllowResponse()
                    .connection(HTTP.CONN_CLOSE)
                    .contentEncoding(Nanny.ENCODING_BUNDLE)
                    .body(response);
        }

        // ongoing request
        Intent server = new Intent(mActivity, ProxyService.class);
        server.putExtra(ProxyService.CLIENT_ID, mClientId);
        server.putExtra(ProxyService.REQUEST, mRequest);
        Timber.wtf("Operation.function is null, starting service with args: " + BundleUtil.toString(server));
        mActivity.startService(server);
        return newAllowResponse();
    }
}
