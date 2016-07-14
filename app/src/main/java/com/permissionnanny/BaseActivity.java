package com.permissionnanny;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import com.permissionnanny.dagger.ActivityComponent;
import com.permissionnanny.dagger.ActivityModule;
import com.permissionnanny.dagger.ContextModule;
import com.permissionnanny.dagger.DaggerActivityComponent;

/**
 * The root of all Activities.
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerActivityComponent.builder()
                    .appComponent(((App) getApplicationContext()).getAppComponent())
                    .contextModule(new ContextModule(this))
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return mComponent;
    }

    @VisibleForTesting
    public void setTestComponent(ActivityComponent component) {
        mComponent = component;
    }
}
