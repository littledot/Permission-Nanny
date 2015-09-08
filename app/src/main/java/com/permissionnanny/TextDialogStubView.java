package com.permissionnanny;

import android.view.ViewStub;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class TextDialogStubView {

    private final ConfirmRequestBinder mBinder;

    @Bind(R.id.tvReason) TextView tvReason;

    public TextDialogStubView(ConfirmRequestBinder binder) {
        mBinder = binder;
    }

    public void inflateViewStub(ViewStub stub) {
        stub.setLayoutResource(R.layout.dialog_text);
        ButterKnife.bind(this, stub.inflate());
    }

    public void bindViews() {
        tvReason.setText(mBinder.getDialogBody());
    }
}
