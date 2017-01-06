package com.permissionnanny.dagger

import android.content.Context
import com.permissionnanny.ProxyExecutor
import dagger.Module
import dagger.Provides

/**

 */
@Module
open class ContextModule(private val context: Context) {

    @Provides
    @ContextComponent.ContextScope
    open fun provideContext(): Context {
        return context
    }

    @Provides
    @ContextComponent.ContextScope
    open fun provideProxyExecutor(context: Context): ProxyExecutor {
        return ProxyExecutor(context)
    }
}
