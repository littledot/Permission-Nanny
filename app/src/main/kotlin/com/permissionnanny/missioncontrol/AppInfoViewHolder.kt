package com.permissionnanny.missioncontrol

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.permissionnanny.R
import org.jetbrains.anko.find

class AppInfoViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    internal val tvAppName: TextView = itemView.find(R.id.appName)
    internal val ivAppIcon: ImageView = itemView.find(R.id.appIcon)
}

