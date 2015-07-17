package com.permissionnanny.missioncontrol;

import android.os.Bundle;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.permissionnanny.BaseActivity;
import com.permissionnanny.R;
import com.permissionnanny.dagger.AppModule;
import net.engio.mbassy.listener.Handler;

import javax.inject.Inject;

public class AppListActivity extends BaseActivity {
    @Bind(R.id.toolbar) Toolbar tBar;
    @Bind(R.id.list) RecyclerView rvAppList;
    @Bind(R.id.empty) ViewGroup vgEmpty;

    @Inject PermissionConfigDataManager mConfigManager;
    @Inject AppModule.Bus mBus;
    private AppListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_app_list);
        ButterKnife.bind(this);
        setSupportActionBar(tBar);
        getActivityComponent().inject(this);

        mAdapter = new AppListAdapter(this, mConfigManager);
        rvAppList.setLayoutManager(new LinearLayoutManager(this));
        rvAppList.setAdapter(mAdapter);
        rvAppList.addItemDecoration(new SpacesItemDecoration(this, TypedValue.COMPLEX_UNIT_DIP, 0, 8, 0, 8));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_refresh:
            mConfigManager.refreshData();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBus.subscribe(this);
        mConfigManager.refreshData();
        mAdapter.setData(mConfigManager.getConfig());
        mAdapter.notifyDataSetChanged();
        setViewVisibility();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBus.unsubscribe(this);
    }

    @Handler
    public void onConfigData(SimpleArrayMap<String, SimpleArrayMap<String, PermissionConfig>> configs) {
        mAdapter.setData(configs);
        mAdapter.notifyDataSetChanged();
        setViewVisibility();
    }

    private void setViewVisibility() {
        boolean hasData = mAdapter.getItemCount() > 0;
        rvAppList.setVisibility(hasData ? View.VISIBLE : View.GONE);
        vgEmpty.setVisibility(hasData ? View.GONE : View.VISIBLE);
    }
}
