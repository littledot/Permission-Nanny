package com.permissionnanny.missioncontrol

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.permissionnanny.BaseActivity
import javax.inject.Inject

open class AppControlActivity : BaseActivity() {

    @Inject internal lateinit var binder: AppControlBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        binder.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return binder.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return binder.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        binder.onResume()
    }

    override fun onPause() {
        super.onPause()
        binder.onPause()
    }
}
