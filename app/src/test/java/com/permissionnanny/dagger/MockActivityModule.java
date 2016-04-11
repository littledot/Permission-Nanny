package com.permissionnanny.dagger;

import android.support.v7.app.AppCompatActivity;
import com.permissionnanny.ConfirmRequestBinder;
import com.permissionnanny.ProxyExecutor;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.missioncontrol.AppControlActivity;
import com.permissionnanny.missioncontrol.AppControlBinder;

import static org.mockito.Mockito.mock;

/**
 *
 */
public class MockActivityModule extends ActivityModule {

    public MockActivityModule() {
        super(null);
    }

    @Override
    AppCompatActivity provideAppCompatActivity() {
        return mock(AppControlActivity.class);
    }

    @Override
    AppControlBinder provideAppControlBinder(AppCompatActivity appCompatActivity,
                                             AppPermissionManager appPermissionManager,
                                             AppModule.Bus bus) {
        return mock(AppControlBinder.class);
    }

    @Override
    ConfirmRequestBinder provideConfirmRequestBinder(AppCompatActivity appCompatActivity,
                                                     ProxyExecutor proxyExecutor,
                                                     AppPermissionManager appPermissionManager) {
        return mock(ConfirmRequestBinder.class);
    }
}
