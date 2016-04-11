package com.permissionnanny;

import android.os.Bundle;
import javax.inject.Inject;

/**
 *
 */
public class ConfirmRequestActivity extends BaseActivity {

    @Inject ConfirmRequestBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getComponent().inject(this);
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
