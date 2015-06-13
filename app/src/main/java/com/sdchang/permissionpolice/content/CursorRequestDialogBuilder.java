package com.sdchang.permissionpolice.content;

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
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.request.content.CursorEvent;
import com.sdchang.permissionpolice.lib.request.content.CursorRequest;
import org.apache.http.protocol.HTTP;
import timber.log.Timber;

import java.security.SecureRandom;

/**
 *
 */
public class CursorRequestDialogBuilder extends BaseDialogBuilder<CursorRequest> {

    private ContentOperation mOperation;

    @InjectView(R.id.tvReason) TextView tvReason;

    public CursorRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
        for (ContentOperation operation : ContentOperation.operations) {
            if (mRequest.uri().toString().startsWith(operation.mUri.toString())) {
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
    public void inflateViewStub(ViewStub stub) {
        stub.setLayoutResource(R.layout.dialog_text);
        View view = stub.inflate();
        ButterKnife.inject(this, view);
        tvReason.setText(mReason);
    }

    @Override
    protected Intent onAllowRequest() {
        long nonce = new SecureRandom().nextLong();
        Timber.wtf("nonce=" + nonce);

        // cache request params
        CursorContentProvider.approvedRequests.put(nonce, mRequest);

        // return nonce to client
        return super.onAllowRequest()
                .putExtra(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE)
                .putExtra(CursorEvent.NONCE, nonce);
    }
}
