package com.permissionnanny.missioncontrol

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.permissionnanny.R
import org.jetbrains.anko.find

class AppControlView(
        private val activity: AppCompatActivity,
        private val binder: AppControlBinder) {

    internal lateinit var tBar: Toolbar
    internal lateinit var rvAppList: RecyclerView
    internal lateinit var vgEmpty: ViewGroup

    fun onCreate(state: Bundle?, adapter: AppControlAdapter) {
        activity.setContentView(R.layout.page_app_list)
        tBar = activity.find(R.id.toolbar)
        rvAppList = activity.find(R.id.list)
        vgEmpty = activity.find(R.id.empty)

        activity.setSupportActionBar(tBar)
        rvAppList.adapter = adapter
        rvAppList.layoutManager = LinearLayoutManager(activity)
        rvAppList.addItemDecoration(SpacesItemDecoration(activity, TypedValue.COMPLEX_UNIT_DIP, 0f, 8f, 0f, 8f))
    }

    fun onCreateOptionsMenu(menu: Menu): Boolean {
        activity.menuInflater.inflate(R.menu.app_list_menu, menu)
        return true
    }

    fun setViewVisibility(adapter: AppControlAdapter) {
        val hasData = adapter.itemCount > 0
        rvAppList.visibility = if (hasData) View.VISIBLE else View.GONE
        vgEmpty.visibility = if (hasData) View.GONE else View.VISIBLE
    }
}
