package com.permissionnanny.lib;

import android.app.Application;
import org.mockito.MockitoAnnotations;

/**
 *
 */
public class RoboApp extends Application {
    public RoboApp() {
        MockitoAnnotations.initMocks(this);
    }
}
