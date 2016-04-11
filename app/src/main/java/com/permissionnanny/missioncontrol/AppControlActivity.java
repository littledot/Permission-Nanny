package com.permissionnanny.missioncontrol;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.permissionnanny.BaseActivity;
import javax.inject.Inject;

public class AppControlActivity extends BaseActivity {

    @Inject AppControlBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        mBinder.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return mBinder.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mBinder.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinder.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinder.onPause();
    }
}
