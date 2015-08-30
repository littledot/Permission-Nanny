package com.permissionnanny;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.permissionnanny.dagger.ContextComponent;
import com.permissionnanny.dagger.DaggerContextComponent;

/**
 *
 */
public class BaseActivity extends AppCompatActivity {

    private ContextComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @param builder
     * @return
     */
    protected DaggerContextComponent.Builder buildContextComponent(DaggerContextComponent.Builder builder) {
        return builder.appComponent(((App) getApplication()).getAppComponent());
    }

    /**
     * @return
     */
    public ContextComponent getContextComponent() {
        if (mComponent == null) {
            mComponent = buildContextComponent(DaggerContextComponent.builder()).build();
        }
        return mComponent;
    }

    public ContextComponent getComponent() {
        return ((App) getApplicationContext()).getContextComponent(this);
    }
}
