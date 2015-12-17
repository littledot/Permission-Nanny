package com.permissionnanny.dagger;

import android.content.Context;
import com.permissionnanny.ProxyExecutor;

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
    ProxyExecutor provideProxyExecutor(Context context) {
        return mock(ProxyExecutor.class);
    }
}
