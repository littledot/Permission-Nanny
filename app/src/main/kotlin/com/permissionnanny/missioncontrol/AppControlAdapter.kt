package com.permissionnanny.missioncontrol

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.permissionnanny.R
import com.permissionnanny.SimpleOnItemSelectedListener
import com.permissionnanny.Util
import com.permissionnanny.data.AppPermission
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.data.PermissionDetail
import timber.log.Timber

/**

 */
class AppControlAdapter(
        private val context: Context,
        private val configManager: AppPermissionManager)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val pm: PackageManager

    private val appsPositions = SparseArray<String>()
    private val permissionPositions = SparseArray<AppPermission>()
    private val displayDescription = SparseArray<Boolean>()

    init {
        pm = context.packageManager
    }

    fun setData(configs: Map<String, Map<String, AppPermission>>) {
        appsPositions.clear()
        permissionPositions.clear()
        var index = 0

        for (appName in configs.keys) {
            appsPositions.put(index++, appName)
            val appConfigs = configs[appName]
            for (permissionName in appConfigs!!.keys) {
                permissionPositions.put(index++, appConfigs[permissionName])
            }
        }
    }

    override fun getItemCount(): Int {
        return appsPositions.size() + permissionPositions.size()
    }

    override fun getItemViewType(position: Int): Int {
        return if (appsPositions.get(position) != null) R.layout.item_app else R.layout.item_switch
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return if (viewType == R.layout.item_app) AppInfoViewHolder(view) else PermissionInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AppInfoViewHolder) {
            bindAppViewHolder(holder, position)
        } else if (holder is PermissionInfoViewHolder) {
            bindPermissionSwitchViewHolder(holder, position)
        }
    }

    private fun bindAppViewHolder(holder: AppInfoViewHolder, position: Int) {
        val appPackage = appsPositions.get(position)
        var appName = appPackage
        var appIcon: Drawable? = null

        val info = Util.getApplicationInfo(context, appPackage)
        if (info != null) {
            val appLabel = pm.getApplicationLabel(info)
            if (appLabel != null) {
                appName = appLabel.toString()
            }
            appIcon = pm.getApplicationIcon(info)
        }

        holder.ivAppIcon.setImageDrawable(appIcon)
        holder.tvAppName.text = appName
    }

    private fun bindPermissionSwitchViewHolder(holder: PermissionInfoViewHolder, position: Int) {
        val config = permissionPositions.get(position)

        var permName = config.permissionName
        var permDesc: String? = null

        val permNameRes = PermissionDetail.getLabel(config.permissionName)
        if (permNameRes > 0) {
            permName = capitalize(context.getString(permNameRes))
        }
        val permDescRes = PermissionDetail.getDescription(config.permissionName)
        if (permDescRes > 0) {
            permDesc = context.getString(permDescRes)
        }

        holder.tvPermissionName.text = permName
        holder.tvPermissionName.setOnClickListener {
            displayDescription.put(position, !displayDescription(position))
            notifyItemChanged(position)
        }

        holder.tvPermissionDesc.text = permDesc
        holder.tvPermissionDesc.visibility = if (displayDescription(position)) View.VISIBLE else View.GONE

        holder.sPermissionAccess.adapter = ArrayAdapter.createFromResource(context, R.array.access_configurations,
                android.R.layout.simple_spinner_dropdown_item)
        holder.sPermissionAccess.setSelection(settingsToSelection[config.privilege.toInt()])
        holder.sPermissionAccess.onItemSelectedListener = object : SimpleOnItemSelectedListener() {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                configManager.changePrivilege(config, selectionToSettings[position])
            }
        }
    }

    private fun capitalize(line: String): String {
        return Character.toUpperCase(line[0]) + line.substring(1)
    }

    private fun displayDescription(position: Int): Boolean {
        return displayDescription.get(position) === java.lang.Boolean.TRUE
    }

    companion object {
        private val settingsToSelection = intArrayOf(0, 1, 2)
        private val selectionToSettings = longArrayOf(AppPermission.ALWAYS_ASK, AppPermission.ALWAYS_ALLOW, AppPermission.ALWAYS_DENY)
    }
}
