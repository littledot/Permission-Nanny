package com.permissionnanny.dagger

import android.content.Context
import com.permissionnanny.ProxyExecutor

import org.mockito.Mockito.mock

/**
 * [ContextModule] that returns mocks.
 */
class MockContextModule : ContextModule(mock(Context::class.java)) {

    override fun provideContext(): Context {
        return mock(Context::class.java)
    }

    override fun provideProxyExecutor(context: Context): ProxyExecutor {
        return mock(ProxyExecutor::class.java)
    }
}
