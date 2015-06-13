package com.sdchang.permissionpolice.demo;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
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
import com.sdchang.permissionpolice.demo.location.LocationRequestFactory;
import com.sdchang.permissionpolice.demo.telephony.TelephonyRequestFactory;
import com.sdchang.permissionpolice.demo.wifi.WifiRequestFactory;
import com.sdchang.permissionpolice.lib.request.content.CursorListener;
import com.sdchang.permissionpolice.lib.request.content.CursorRequest;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    public static String FACTORY_ID = "factoryId";

    public static String[] mLabels = new String[]{
            "LocationRequestDemo",
            "TelephonyRequestDemo",
            "WifiRequestDemo",
    };
    public static DemoRequestFactory[] mFactories = new DemoRequestFactory[]{
            new LocationRequestFactory(),
            new TelephonyRequestFactory(),
            new WifiRequestFactory(),
    };

    @InjectView(R.id.rv) RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.inject(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MyAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "exchange");
        return super.onCreateOptionsMenu(menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public int getItemCount() {
            return mLabels.length;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_listitem, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv1.setText(mLabels[position]);
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, DemoActivity.class)
                            .putExtra(FACTORY_ID, position));
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
