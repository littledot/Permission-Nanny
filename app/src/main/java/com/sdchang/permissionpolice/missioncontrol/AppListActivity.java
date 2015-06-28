package com.sdchang.permissionpolice.missioncontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.sdchang.permissionpolice.BaseActivity;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.Police;

import javax.inject.Inject;

public class AppListActivity extends BaseActivity {

    @InjectView(R.id.rv) RecyclerView rvAppList;

    @Inject PermissionConfigDataManager mConfigManager;
    private AppListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_app_list);
        ButterKnife.inject(this);
        getActivityComponent().inject(this);

        mAdapter = new AppListAdapter(this, mConfigManager);
        rvAppList.setLayoutManager(new LinearLayoutManager(this));
        rvAppList.setAdapter(mAdapter);

        Intent uses = new Intent(Police.ACTION_GET_PERMISSION_USAGES);
        sendBroadcast(uses);

        rvAppList.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(mConfigManager.getConfig());
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }
}
