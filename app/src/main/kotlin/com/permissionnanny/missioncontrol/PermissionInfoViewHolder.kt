package com.permissionnanny.missioncontrol

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import com.permissionnanny.R
import org.jetbrains.anko.find

class PermissionInfoViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    internal val tvPermissionName: TextView = itemView.find(R.id.permissionName)
    internal var tvPermissionDesc: TextView = itemView.find(R.id.permissionDesc)
    internal var sPermissionAccess: Spinner = itemView.find(R.id.permissionAccess)
}
