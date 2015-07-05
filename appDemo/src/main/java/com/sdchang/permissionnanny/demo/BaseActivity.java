package com.sdchang.permissionnanny.demo;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 *
 */
public class BaseActivity extends AppCompatActivity {
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
        }
        return super.onOptionsItemSelected(item);
    }
}
