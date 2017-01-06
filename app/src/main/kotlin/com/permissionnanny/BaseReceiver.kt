package com.permissionnanny

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.annotation.VisibleForTesting
import com.permissionnanny.dagger.ContextComponent
import com.permissionnanny.dagger.ContextModule
import com.permissionnanny.dagger.DaggerContextComponent

/**
 * The root of all BroadcastReceivers.
 */
open class BaseReceiver : BroadcastReceiver() {

    private var component: ContextComponent? = null

    override fun onReceive(context: Context, intent: Intent) {
    }

    @VisibleForTesting
    fun setTestComponent(component: ContextComponent) {
        this.component = component
    }

    fun getComponent(context: Context): ContextComponent {
        if (component == null) {
            component = DaggerContextComponent.builder()
                    .appComponent((context.applicationContext as App).getAppComponent())
                    .contextModule(ContextModule(context))
                    .build()
        }
        return component!!
    }
}
