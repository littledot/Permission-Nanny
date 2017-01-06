package com.permissionnanny.data

import java.util.*

/**

 */
open class AppPermissionDB(private val db: NannyDB) {

    fun open() {
        db.open()
    }

    val allConfigs: ArrayList<AppPermission>
        get() = db.findVals(null, AppPermission::class.java)

    fun putConfig(config: AppPermission) {
        db.put(key(config), config)
    }

    fun delConfig(appPackage: String, permission: String) {
        db.del(key(appPackage, permission))
    }

    fun delApp(appPackage: String) {
        for (key in db.findKeys(appPackage)) {
            db.del(key)
        }
    }

    fun delConfig(config: AppPermission) {
        db.del(key(config))
    }

    private fun key(config: AppPermission): String {
        return key(config.appPackageName, config.permissionName)
    }

    private fun key(appPackage: String, permission: String): String {
        return appPackage + pipe + permission
    }

    companion object {

        private val pipe = "\u0378\u0379"
    }
}
