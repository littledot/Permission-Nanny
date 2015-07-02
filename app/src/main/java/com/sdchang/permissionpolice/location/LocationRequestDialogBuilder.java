package com.sdchang.permissionpolice.location;

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
import com.sdchang.permissionpolice.BaseDialogBuilder;
import com.sdchang.permissionpolice.C;
import com.sdchang.permissionpolice.ProxyOperation;
import com.sdchang.permissionpolice.ProxyService;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.ResponseBundle;
import com.sdchang.permissionpolice.common.BundleUtil;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.RequestParams;
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
                    .contentType(Police.APPLICATION_BUNDLE)
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
