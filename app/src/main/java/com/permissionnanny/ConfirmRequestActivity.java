package com.permissionnanny;

import android.os.Bundle;
import com.permissionnanny.lib.NannyBundle;

/**
 *
 */
public class ConfirmRequestActivity extends BaseActivity {

    private ConfirmRequestBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mBinder = new ConfirmRequestBinder(this, new NannyBundle(getIntent().getExtras()));

        mBinder.preOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        mBinder.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mBinder.onBackPressed();
    }
}
