package com.permissionnanny.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.permissionnanny.demo.account.AccountDemoAdapter;
import com.permissionnanny.demo.content.DemoContentRequestAdapter;
import com.permissionnanny.demo.content.DemoContentRequestFactory;
import com.permissionnanny.demo.location.LocationDemoAdapter;

/**
 *
 */
public class DemoActivity extends BaseActivity {
    @Bind(R.id.rv) RecyclerView rv;
    Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_activity);
        ButterKnife.bind(this);

        Intent src = getIntent();
        int factoryId = src.getIntExtra(MainActivity.FACTORY_ID, -1);
        switch (factoryId) {
        case 1:
            mAdapter = new DemoContentRequestAdapter(new DemoContentRequestFactory());
            break;
        case 2:
            mAdapter = new LocationDemoAdapter();
            break;
        case 5:
            mAdapter = new AccountDemoAdapter();
            break;
        default:
            mAdapter = new DemoSimpleRequestAdapter(MainActivity.mFactories[factoryId]);
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }
}
