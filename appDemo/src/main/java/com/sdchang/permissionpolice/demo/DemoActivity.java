package com.sdchang.permissionpolice.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.sdchang.permissionpolice.demo.content.CursorRequestFactory;

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
        if (factoryId == 0) {
            mAdapter = new CursorDemoAdapter(new CursorRequestFactory());
        } else {
            mAdapter = new DemoAdapter(MainActivity.mFactories[factoryId]);
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }
}
