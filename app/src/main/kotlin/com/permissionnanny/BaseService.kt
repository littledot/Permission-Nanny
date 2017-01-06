package com.permissionnanny

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.annotation.VisibleForTesting
import com.permissionnanny.dagger.ContextComponent
import com.permissionnanny.dagger.ContextModule
import com.permissionnanny.dagger.DaggerContextComponent
import timber.log.Timber

/**
 * The root of all Services.
 */
open class BaseService : Service() {

    private var component: ContextComponent? = null

    override fun onCreate() {
        Timber.wtf("")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.wtf("")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        Timber.wtf("")
        return null
    }

    override fun onDestroy() {
        Timber.wtf("destroy")
        super.onDestroy()
    }

    @VisibleForTesting
    fun setTestComponent(component: ContextComponent) {
        this.component = component
    }

    fun getComponent(): ContextComponent {
        if (component == null) {
            component = DaggerContextComponent.builder()
                    .appComponent((applicationContext as App).getAppComponent())
                    .contextModule(ContextModule(this))
                    .build()
        }
        return component!!
    }
}
