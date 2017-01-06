package com.permissionnanny.dagger

import com.permissionnanny.ClientPermissionManifestReceiverTest
import com.permissionnanny.ClientRequestReceiverTest
import com.permissionnanny.ConfirmRequestBinderTest
import com.permissionnanny.UninstallReceiverTest

/**
 * Dependency injection for tests.
 */
@ContextComponent.ContextScope
@dagger.Component(modules = arrayOf(ContextModule::class), dependencies = arrayOf(AppComponent::class))
interface MockContextComponent : ContextComponent {

    fun inject(victim: UninstallReceiverTest)

    fun inject(victim: ClientPermissionManifestReceiverTest)

    fun inject(victim: ClientRequestReceiverTest)

    fun inject(victim: ConfirmRequestBinderTest)
}
