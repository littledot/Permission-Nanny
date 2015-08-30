package com.permissionnanny;

import android.content.Context;
import com.permissionnanny.dagger.AppComponent;
import com.permissionnanny.dagger.ContextComponent;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * A no-op application.
 */
public class RoboApp extends App {
    @Mock AppComponent appComponent;
    @Mock ContextComponent mContextComponent;

    public RoboApp() {
        MockitoAnnotations.initMocks(this);
    }

    @Override
    public void onCreate() {
        // Explicit no-op
    }

    @Override
    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public ContextComponent getContextComponent(Context context) {
        return mContextComponent;
    }
}
