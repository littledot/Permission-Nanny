package com.permissionnanny;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.permissionnanny.dagger.ContextComponent;

/**
 *
 */
public class BaseActivity extends AppCompatActivity {

    private ContextComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ContextComponent getComponent() {
        if (mComponent == null) {
            mComponent = ((App) getApplicationContext()).getContextComponent(this);
        }
        return mComponent;
    }
}
