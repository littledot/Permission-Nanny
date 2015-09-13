package com.permissionnanny.missioncontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.permissionnanny.R;

public class AppControlView {

    private final AppCompatActivity mActivity;
    private final AppControlBinder mBinder;

    @Bind(R.id.toolbar) Toolbar tBar;
    @Bind(R.id.list) RecyclerView rvAppList;
    @Bind(R.id.empty) ViewGroup vgEmpty;

    public AppControlView(AppCompatActivity activity, AppControlBinder binder) {
        mActivity = activity;
        mBinder = binder;
    }

    public void onCreate(Bundle state, AppControlAdapter adapter) {
        mActivity.setContentView(R.layout.page_app_list);
        ButterKnife.bind(this, mActivity);
        mActivity.setSupportActionBar(tBar);
        rvAppList.setAdapter(adapter);
        rvAppList.setLayoutManager(new LinearLayoutManager(mActivity));
        rvAppList.addItemDecoration(new SpacesItemDecoration(mActivity, TypedValue.COMPLEX_UNIT_DIP, 0, 8, 0, 8));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        mActivity.getMenuInflater().inflate(R.menu.app_list_menu, menu);
        return true;
    }

    public void setViewVisibility(AppControlAdapter adapter) {
        boolean hasData = adapter.getItemCount() > 0;
        rvAppList.setVisibility(hasData ? View.VISIBLE : View.GONE);
        vgEmpty.setVisibility(hasData ? View.GONE : View.VISIBLE);
    }
}
