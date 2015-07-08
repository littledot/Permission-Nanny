package com.permissionnanny.content;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.permissionnanny.BaseDialogBuilder;
import com.permissionnanny.C;
import com.permissionnanny.R;
import com.permissionnanny.ResponseBundle;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.content.CursorEvent;
import timber.log.Timber;

import java.security.SecureRandom;

/**
 *
 */
public class CursorRequestDialogBuilder extends BaseDialogBuilder<RequestParams> {

    private ContentOperation mOperation;

    @Bind(R.id.tvReason) TextView tvReason;

    public CursorRequestDialogBuilder(Activity activity, Bundle args, String clientAddr) {
        super(activity, args, clientAddr);
        mOperation = ContentOperation.getOperation(mRequest.uri0);
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
        ButterKnife.bind(this, view);
        tvReason.setText(mReason);
    }

    @Override
    protected ResponseBundle onAllowRequest() {
        long nonce = new SecureRandom().nextLong();
        Timber.wtf("nonce=" + nonce);

        // cache request params
        CursorContentProvider.approvedRequests.put(nonce, mRequest);

        // return nonce to client
        Bundle response = new Bundle();
        response.putLong(CursorEvent.NONCE, nonce);
        return newAllowResponse()
                .connection(Nanny.CLOSE)
                .contentEncoding(Nanny.ENCODING_BUNDLE)
                .body(response);
    }
}
