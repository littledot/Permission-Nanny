package com.permissionnanny

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import com.permissionnanny.dagger.ActivityComponent
import com.permissionnanny.dagger.ActivityModule
import com.permissionnanny.dagger.ContextModule
import com.permissionnanny.dagger.DaggerActivityComponent

/**
 * The root of all Activities.
 */
open class BaseActivity : AppCompatActivity() {

    private var component: ActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun getComponent(): ActivityComponent {
        if (component == null) {
            component = DaggerActivityComponent.builder()
                    .appComponent((applicationContext as App).getAppComponent())
                    .contextModule(ContextModule(this))
                    .activityModule(ActivityModule(this))
                    .build()
        }
        return component!!
    }

    @VisibleForTesting
    fun setTestComponent(component: ActivityComponent) {
        this.component = component
    }
}
