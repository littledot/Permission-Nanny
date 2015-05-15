package com.sdchang.permissionpolice.demo;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.sdchang.permissionpolice.lib.request.content.CursorListener;
import com.sdchang.permissionpolice.lib.request.content.CursorRequest;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 4, 0, "exchange");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == 4) {
            CursorRequest.newBuilder()
                    .uri(ContactsContract.Contacts.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .startRequest(this, "We want to upload your contacts to our servers", new CursorListener() {
                        @Override
                        public void callback(Cursor data) {
                            Timber.wtf(DatabaseUtils.dumpCursorToString(data));
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }
}
