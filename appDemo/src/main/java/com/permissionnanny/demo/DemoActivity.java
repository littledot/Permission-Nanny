package com.permissionnanny.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.permissionnanny.demo.content.ContentDemoAdapter;
import com.permissionnanny.demo.content.ContentRequestFactory;
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
        case 0:
            mAdapter = new ContentDemoAdapter(new ContentRequestFactory());
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
