package com.permissionnanny;

import com.permissionnanny.dagger.AppComponent;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * A no-op application.
 */
public class RoboApp extends App {
    @Mock AppComponent appComponent;

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
}
