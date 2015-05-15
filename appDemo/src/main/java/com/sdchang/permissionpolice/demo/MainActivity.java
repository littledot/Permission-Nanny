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
import com.sdchang.permissionpolice.lib.BundleListener;
import com.sdchang.permissionpolice.lib.request.content.CursorListener;
import com.sdchang.permissionpolice.lib.request.content.CursorRequest;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;
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
        menu.add(0, 4, 0, "exchange");
        menu.add(0, 5, 0, "wifi");
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
        } else if (id == 5) {
            WifiConfiguration wc = new WifiConfiguration();
            wc.SSID = "\"SSID_NAME\""; //IMP! This should be in Quotes!!
            wc.hiddenSSID = true;
            wc.status = WifiConfiguration.Status.DISABLED;
            wc.priority = 40;
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);

            wc.wepKeys[0] = "\"aaabbb1234\""; //This is the WEP Password
            wc.wepTxKeyIndex = 0;

            WifiManagerRequest.newAddNetworkRequest(wc).startRequest(this, "We want to add new wifi",
                    new BundleListener() {
                        @Override
                        public void onResult(Bundle results) {
                            Timber.wtf(results.toString());
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }
}
