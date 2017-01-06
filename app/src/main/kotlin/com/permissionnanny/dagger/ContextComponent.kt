package com.permissionnanny.dagger

import android.content.Context
import com.permissionnanny.*
import com.permissionnanny.dagger.ContextComponent.ContextScope
import dagger.Component
import javax.inject.Scope

/**

 */
@ContextScope
@Component(modules = arrayOf(ContextModule::class), dependencies = arrayOf(AppComponent::class))
interface ContextComponent {

    @Scope
    annotation class ContextScope

    fun context(): Context

    fun inject(victim: ProxyService)

    fun inject(victim: ClientRequestReceiver)

    fun inject(victim: ClientPermissionManifestReceiver)

    fun inject(victim: UninstallReceiver)

    fun inject(victim: ConfirmRequestBinder)
}
