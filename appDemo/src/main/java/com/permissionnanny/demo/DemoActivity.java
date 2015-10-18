package com.permissionnanny.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.permissionnanny.demo.content.DemoContentRequestAdapter;
import com.permissionnanny.demo.content.DemoContentRequestFactory;

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
        mAdapter = getAdapter(src.getIntExtra(MainActivity.FACTORY_ID, -1));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }

    private Adapter getAdapter(int factoryId) {
        switch (factoryId) {
        case 1:
            return new DemoContentRequestAdapter(new DemoContentRequestFactory());
        default:
            return new DemoSimpleRequestAdapter(MainActivity.mFactories[factoryId]);
        }
    }
}
