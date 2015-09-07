package com.permissionnanny;

import android.os.Bundle;
import com.permissionnanny.lib.NannyBundle;

/**
 *
 */
public class ConfirmRequestActivity extends BaseActivity {

    private ConfirmRequestView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConfirmRequestBinder binder = new ConfirmRequestBinder(this, new NannyBundle(getIntent().getExtras()));
        mView = new ConfirmRequestView(this, binder, new TextDialogStub(binder));
        mView.preOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        mView.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        mView.preOnBackPressed();
        super.onBackPressed();
    }
}
