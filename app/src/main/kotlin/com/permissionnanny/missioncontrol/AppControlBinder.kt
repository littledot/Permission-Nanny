package com.permissionnanny.missioncontrol

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.permissionnanny.AllOpen
import com.permissionnanny.BaseBinder
import com.permissionnanny.R
import com.permissionnanny.dagger.AppModule
import com.permissionnanny.data.AppPermission
import com.permissionnanny.data.AppPermissionManager
import net.engio.mbassy.listener.Handler

@AllOpen
open class AppControlBinder(
        activity: AppCompatActivity,
        private val appManager: AppPermissionManager,
        private val bus: AppModule.Bus)
    : BaseBinder() {

    internal var view: AppControlView = AppControlView(activity, this)

    private val context: Context = activity
    internal lateinit var adapter: AppControlAdapter

    open fun onCreate(state: Bundle?) {
        adapter = AppControlAdapter(context, appManager)
        view.onCreate(state, adapter)
    }

    open fun onCreateOptionsMenu(menu: Menu): Boolean {
        return view.onCreateOptionsMenu(menu)
    }

    open fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                appManager.refreshData()
                return true
            }
        }
        return false
    }

    open fun onResume() {
        bus.subscribe(this)
        appManager.refreshData()
        adapter.setData(appManager.config)
        adapter.notifyDataSetChanged()
        view.setViewVisibility(adapter)
    }

    open fun onPause() {
        bus.unsubscribe(this)
    }

    @Handler
    fun onConfigData(configs: Map<String, Map<String, AppPermission>>) {
        adapter.setData(configs)
        adapter.notifyDataSetChanged()
        view.setViewVisibility(adapter)
    }
}
