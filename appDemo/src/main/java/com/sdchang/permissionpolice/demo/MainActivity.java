package com.sdchang.permissionpolice.demo;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.sdchang.permissionpolice.demo.wifi.WifiRequestDemoActivity;
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

    @InjectView(R.id.rv) RecyclerView rv;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.inject(this);

        mAdapter = new MyAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
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

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        Class[] mActivities = new Class[]{WifiRequestDemoActivity.class};

        @Override
        public int getItemCount() {
            return mActivities.length;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_listitem, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final Class activity = mActivities[position];
            holder.tv1.setText(activity.getSimpleName());
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, activity));
                }
            });
        }
    }

    class MyViewHolder extends ViewHolder {
        @InjectView(R.id.tv1) TextView tv1;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
