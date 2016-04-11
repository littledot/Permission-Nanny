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
import java.util.Map;
import net.engio.mbassy.listener.Handler;

public class AppControlBinder extends BaseBinder {

    AppControlView mView;

    private final Context mContext;
    private final AppPermissionManager mAppManager;
    private final AppModule.Bus mBus;
    AppControlAdapter mAdapter;

    public AppControlBinder(AppCompatActivity activity,
                            AppPermissionManager appPermissionManager,
                            AppModule.Bus bus) {
        mContext = activity;
        mAppManager = appPermissionManager;
        mBus = bus;
        mView = new AppControlView(activity, this);
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
