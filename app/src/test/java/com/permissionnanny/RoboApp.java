package com.permissionnanny;

import com.permissionnanny.dagger.AppComponent;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 */
public class RoboApp extends App {
    @Mock AppComponent appComponent;

    public RoboApp() {
        MockitoAnnotations.initMocks(this);
    }

    @Override
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
