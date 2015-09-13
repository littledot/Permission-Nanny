package com.permissionnanny.missioncontrol;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.permissionnanny.BaseActivity;

public class AppControlActivity extends BaseActivity {

    private AppControlBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = new AppControlBinder(this);
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
