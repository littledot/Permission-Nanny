package com.sdchang.permissionpolice.demo;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.sdchang.permissionpolice.lib.BundleListener;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.content.CursorListener;
import com.sdchang.permissionpolice.lib.request.content.CursorRequest;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyManagerRequest;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyResponse;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerResponse;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "exchange");
        menu.add(0, 100, 0, "wifi");
        menu.add(0, 200, 0, "tele");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == 0) {
            CursorRequest.newBuilder()
                    .uri(ContactsContract.Contacts.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .startRequest(this, "We want to upload your contacts to our servers", new CursorListener() {
                        @Override
                        public void callback(Cursor data) {
                            Timber.wtf(DatabaseUtils.dumpCursorToString(data));
                        }
                    });
        } else if (id == 100) {
            WifiManagerRequest.getConnectionInfo().startRequest(this, "We want to add sniff ur wifi",
                    new BundleListener() {
                        @Override
                        public void onResult(Bundle results) {
                            WifiManagerResponse response = new WifiManagerResponse(results.getBundle(Police.RESPONSE));
                            Timber.wtf("ans=" + response.wifiInfo());
                        }
                    });
        } else if (id == 200) {
            TelephonyManagerRequest.getDeviceId().startRequest(this, "we want ur device software ver",
                    new BundleListener() {
                        @Override
                        public void onResult(Bundle results) {
                            TelephonyResponse response = new TelephonyResponse(results.getBundle(Police.RESPONSE));
                            Timber.wtf("ans=" + response.deviceId());
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }
}
