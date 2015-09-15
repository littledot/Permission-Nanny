package com.permissionnanny.missioncontrol;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.permissionnanny.BaseBinder;
import com.permissionnanny.R;
import com.permissionnanny.dagger.AppModule;
import com.permissionnanny.data.AppPermission;
import com.permissionnanny.data.AppPermissionManager;
import net.engio.mbassy.listener.Handler;

import javax.inject.Inject;
import java.util.Map;

public class AppControlBinder extends BaseBinder {

    AppControlView mView;

    private Context mContext;
    @Inject AppPermissionManager mAppManager;
    @Inject AppModule.Bus mBus;
    AppControlAdapter mAdapter;

    public AppControlBinder(AppCompatActivity activity) {
        getComponent(activity).inject(this);
        mView = new AppControlView(activity, this);
        mContext = activity;
    }

    public void onCreate(Bundle state) {
        mAdapter = new AppControlAdapter(mContext, mAppManager);
        mView.onCreate(state, mAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return mView.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                mAppManager.refreshData();
                return true;
        }
        return false;
    }

    public void onResume() {
        mBus.subscribe(this);
        mAppManager.refreshData();
        mAdapter.setData(mAppManager.getConfig());
        mAdapter.notifyDataSetChanged();
        mView.setViewVisibility(mAdapter);
    }

    public void onPause() {
        mBus.unsubscribe(this);
    }

    @Handler
    public void onConfigData(Map<String, Map<String, AppPermission>> configs) {
        mAdapter.setData(configs);
        mAdapter.notifyDataSetChanged();
        mView.setViewVisibility(mAdapter);
    }
}
