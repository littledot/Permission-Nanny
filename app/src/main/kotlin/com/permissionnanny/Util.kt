package com.permissionnanny

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

/**
 * Utilities.
 */
object Util {

    fun getApplicationInfo(context: Context, packageName: String): ApplicationInfo? {
        val pm = context.packageManager
        try {
            return pm.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            return null
        }

    }
}
