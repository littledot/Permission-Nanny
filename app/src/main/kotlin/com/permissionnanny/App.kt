package com.permissionnanny

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.permissionnanny.common.StackTraceDebugTree
import com.permissionnanny.dagger.AppComponent
import com.permissionnanny.dagger.AppModule
import com.permissionnanny.dagger.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric
import timber.log.Timber

/**
 * Server application.
 */
open class App : Application() {
    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        // Crashlytics must always be first
        val crashlytics = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(crashlytics).build())
        PRNGFixes.apply()
        Timber.plant(StackTraceDebugTree())
        LeakCanary.install(this)

        // LevelDB patch: https://github.com/dain/leveldb/issues/43
        System.setProperty("sun.arch.data.model", "32")
        System.setProperty("leveldb.mmap", "false")

        IDENTITY = PendingIntent.getBroadcast(this, 0, Intent(), 0)
    }

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .build()
        }
        return appComponent!!
    }

    companion object {

        /** Identity of the server; sent to clients as a proof of identity.  */
        lateinit var IDENTITY: PendingIntent
    }
}
