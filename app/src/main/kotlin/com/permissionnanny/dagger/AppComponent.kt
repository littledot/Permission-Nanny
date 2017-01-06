package com.permissionnanny.dagger

import android.app.Application
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.data.OngoingRequestDB
import dagger.Component

import javax.inject.Singleton

/**

 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun app(): Application

    fun bus(): AppModule.Bus

    fun appPermissionManager(): AppPermissionManager

    fun db(): OngoingRequestDB
}
