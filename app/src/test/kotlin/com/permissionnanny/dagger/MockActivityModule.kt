package com.permissionnanny.dagger

import android.support.v7.app.AppCompatActivity
import com.permissionnanny.ConfirmRequestBinder
import com.permissionnanny.ProxyExecutor
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.missioncontrol.AppControlActivity
import com.permissionnanny.missioncontrol.AppControlBinder

import org.mockito.Mockito.mock

/**

 */
class MockActivityModule : ActivityModule(mock(AppCompatActivity::class.java)) {

    override fun provideAppCompatActivity(): AppCompatActivity {
        return mock(AppControlActivity::class.java)
    }

    override fun provideAppControlBinder(
            appCompatActivity: AppCompatActivity,
            appPermissionManager: AppPermissionManager,
            bus: AppModule.Bus)
            : AppControlBinder {
        return mock(AppControlBinder::class.java)
    }

    override fun provideConfirmRequestBinder(
            appCompatActivity: AppCompatActivity,
            proxyExecutor: ProxyExecutor,
            appPermissionManager: AppPermissionManager)
            : ConfirmRequestBinder {
        return mock(ConfirmRequestBinder::class.java)
    }
}
