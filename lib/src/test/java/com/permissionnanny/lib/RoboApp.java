package com.permissionnanny.lib;

import android.app.Application;
import org.mockito.MockitoAnnotations;

/**
 * A no-op application.
 */
public class RoboApp extends Application {
    public RoboApp() {
        MockitoAnnotations.initMocks(this);
    }
}
