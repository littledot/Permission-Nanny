package com.permissionnanny.dagger;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.permissionnanny.ProxyExecutor;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.missioncontrol.AppControlBinder;

import static org.mockito.Mockito.mock;

/**
 * {@link ContextModule} that returns mocks.
 */
public class MockContextModule extends ContextModule {

    public MockContextModule() {
        super(null);
    }

    @Override
    Context provideContext() {
        return mock(Context.class);
    }

    @Override
    AppCompatActivity provideAppCompatActivity() {
        return mock(AppCompatActivity.class);
    }

    @Override
    ProxyExecutor provideProxyExecutor(Context context) {
        return mock(ProxyExecutor.class);
    }

    @Override
    AppControlBinder provideAppControlBinder(AppCompatActivity appCompatActivity,
                                             AppPermissionManager appPermissionManager,
                                             AppModule.Bus bus) {
        return mock(AppControlBinder.class);
    }
}
