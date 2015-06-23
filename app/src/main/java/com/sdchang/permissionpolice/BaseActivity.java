package com.sdchang.permissionpolice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.sdchang.permissionpolice.dagger.ActivityComponent;
import com.sdchang.permissionpolice.dagger.DaggerActivityComponent;
import com.sdchang.permissionpolice.dagger.DaggerActivityComponent.Builder;

/**
 *
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @param builder
     * @return
     */
    protected Builder buildActivityComponent(Builder builder) {
        return builder.appComponent(((App) getApplication()).getAppComponent());
    }

    /**
     * @return
     */
    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = buildActivityComponent(DaggerActivityComponent.builder()).build();
        }
        return mActivityComponent;
    }
}
