package com.permissionnanny.dagger

import android.support.v7.app.AppCompatActivity
import com.permissionnanny.ConfirmRequestBinder
import com.permissionnanny.ProxyExecutor
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.lib.NannyBundle
import com.permissionnanny.missioncontrol.AppControlBinder
import dagger.Module
import dagger.Provides

/**

 */
@Module
open class ActivityModule(private val appCompatActivity: AppCompatActivity) {

    @Provides
    @ContextComponent.ContextScope
    open fun provideAppCompatActivity(): AppCompatActivity {
        return appCompatActivity
    }

    @Provides
    @ContextComponent.ContextScope
    open fun provideAppControlBinder(
            appCompatActivity: AppCompatActivity,
            appPermissionManager: AppPermissionManager,
            bus: AppModule.Bus)
            : AppControlBinder {
        return AppControlBinder(appCompatActivity, appPermissionManager, bus)
    }

    @Provides
    @ContextComponent.ContextScope
    open fun provideConfirmRequestBinder(
            appCompatActivity: AppCompatActivity,
            proxyExecutor: ProxyExecutor,
            appPermissionManager: AppPermissionManager)
            : ConfirmRequestBinder {
        return ConfirmRequestBinder(
                appCompatActivity,
                NannyBundle(appCompatActivity.intent.extras),
                proxyExecutor,
                appPermissionManager)
    }
}
