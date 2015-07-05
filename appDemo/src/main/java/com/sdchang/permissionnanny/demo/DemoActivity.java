package com.sdchang.permissionnanny.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.sdchang.permissionnanny.demo.content.CursorDemoAdapter;
import com.sdchang.permissionnanny.demo.content.CursorRequestFactory;
import com.sdchang.permissionnanny.demo.location.LocationDemoAdapter;

/**
 *
 */
public class DemoActivity extends BaseActivity {
    @InjectView(R.id.rv) RecyclerView rv;
    Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_activity);
        ButterKnife.inject(this);

        Intent src = getIntent();
        int factoryId = src.getIntExtra(MainActivity.FACTORY_ID, -1);
        switch (factoryId) {
        case 0:
            mAdapter = new CursorDemoAdapter(new CursorRequestFactory());
            break;
        case 1:
            mAdapter = new LocationDemoAdapter();
            break;
        default:
            mAdapter = new DemoAdapter(MainActivity.mFactories[factoryId]);
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }
}
