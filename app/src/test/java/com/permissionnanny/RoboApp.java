package com.permissionnanny;

import android.app.Activity;
import android.content.Context;
import com.permissionnanny.dagger.AppComponent;
import com.permissionnanny.dagger.ContextComponent;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

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

    public static Context newMockContext() {
        Context mock = Mockito.mock(Context.class);
        when(mock.getApplicationContext()).thenReturn(new RoboApp());
        return mock;
    }

    public static Activity newMockActivity() {
        Activity mock = Mockito.mock(Activity.class);
        when(mock.getApplicationContext()).thenReturn(new RoboApp());
        return mock;
    }
}
