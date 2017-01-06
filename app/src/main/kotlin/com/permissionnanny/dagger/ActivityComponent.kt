package com.permissionnanny.dagger

import com.permissionnanny.ConfirmRequestActivity
import com.permissionnanny.missioncontrol.AppControlActivity
import dagger.Component

/**

 */
@ContextComponent.ContextScope
@Component(modules = arrayOf(ContextModule::class, ActivityModule::class), dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {

    fun inject(victim: AppControlActivity)

    fun inject(victim: ConfirmRequestActivity)
}
