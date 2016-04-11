package com.permissionnanny;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import com.permissionnanny.dagger.AppComponent;
import com.permissionnanny.dagger.ContextComponent;
import com.permissionnanny.dagger.ContextModule;
import com.permissionnanny.dagger.DaggerContextComponent;

/**
 * The root of all Activities.
 */
public class BaseActivity extends AppCompatActivity {

    private ContextComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public AppComponent getAppComponent() {
        return ((App) getApplicationContext()).getAppComponent();
    }

    public ContextComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerContextComponent.builder()
                    .appComponent(getAppComponent())
                    .contextModule(new ContextModule(this))
                    .build();
        }
        return mComponent;
    }

    @VisibleForTesting
    public void inject(ContextComponent component) {
        mComponent = component;
    }
}
