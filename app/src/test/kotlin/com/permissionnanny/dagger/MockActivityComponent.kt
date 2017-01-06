package com.permissionnanny.dagger

import com.permissionnanny.ConfirmRequestActivityTest
import com.permissionnanny.missioncontrol.AppControlActivityTest
import dagger.Component

/**

 */
@ContextComponent.ContextScope
@Component(modules = arrayOf(ContextModule::class, ActivityModule::class), dependencies = arrayOf(AppComponent::class))
interface MockActivityComponent : ActivityComponent {

    fun inject(victim: AppControlActivityTest)

    fun inject(victim: ConfirmRequestActivityTest)
}
