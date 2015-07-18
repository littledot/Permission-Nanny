package com.permissionnanny.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.permissionnanny.lib.NannyClient;
import timber.log.Timber;

/**
 *
 */
public class BaseActivity extends AppCompatActivity {

    private NannyClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient = new NannyClient(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.opt_longText).setChecked(Config.longReason);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.opt_longText:
            Config.longReason = !Config.longReason;
            item.setChecked(Config.longReason);
            return true;
        case R.id.opt_serverCheck:
            Timber.wtf("server installed=" + mClient.isPermissionNannyInstalled());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
