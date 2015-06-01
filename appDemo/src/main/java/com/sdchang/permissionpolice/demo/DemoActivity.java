package com.sdchang.permissionpolice.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 */
public class DemoActivity extends AppCompatActivity {
    @InjectView(R.id.rv) RecyclerView rv;
    DemoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_activity);
        ButterKnife.inject(this);

        Intent src = getIntent();
        mAdapter = new DemoAdapter(MainActivity.mFactories[src.getIntExtra(MainActivity.FACTORY_ID, -1)]);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }
}
