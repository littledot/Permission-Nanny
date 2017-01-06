package com.permissionnanny.data

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.v4.util.ArrayMap
import com.permissionnanny.AllOpen
import com.permissionnanny.App
import com.permissionnanny.Manifest
import com.permissionnanny.Operation
import com.permissionnanny.content.ContentOperation
import com.permissionnanny.dagger.AppModule
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.content.ContentRequest
import com.permissionnanny.simple.SimpleOperation
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manager that handles applications' permissions data.
 */
@Singleton
@AllOpen
open class AppPermissionManager
@Inject
constructor(
        private val context: Application,
        private val db: AppPermissionDB,
        private val bus: AppModule.Bus) {

    /** Index definition: {App Name: {Permission Name: Privilege}}  */
    internal val mConfigs: MutableMap<String, MutableMap<String, AppPermission>> = ArrayMap()

    init {
        readDB()
    }

    open fun refreshData() {
        val entity = Bundle()
        entity.putParcelable(Nanny.SENDER_IDENTITY, App.IDENTITY)

        val uses = Intent(Nanny.ACTION_GET_PERMISSION_MANIFEST)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.ENTITY_BODY, entity)
        context.sendBroadcast(uses)
    }

    private fun readDB() {
        val configs = db.allConfigs
        Timber.wtf("Read " + Arrays.toString(configs.toTypedArray()))

        var i = 0
        val len = configs.size
        while (i < len) {
            val config = configs[i]
            var configMap: MutableMap<String, AppPermission>? = mConfigs[config.appPackageName]
            if (configMap == null) {
                configMap = ArrayMap<String, AppPermission>()
                mConfigs.put(config.appPackageName, configMap)
            }

            configMap.put(config.permissionName, config)
            i++
        }
    }

    open fun readPermissionManifest(appPackage: String, permissions: ArrayList<String>) {
        val oldConfigs = mConfigs[appPackage]
        val newConfigs = oldConfigs ?: ArrayMap<String, AppPermission>()

        for (permission in permissions) {
            val config = newConfigs[permission]
            if (config == null) { // New permission config? Create new entry
                val newConfig = AppPermission(appPackage, permission, AppPermission.ALWAYS_ASK)
                newConfigs.put(permission, newConfig)
                db.putConfig(newConfig)
                Timber.wtf("New config=" + newConfig)
            }
        }

        mConfigs.put(appPackage, newConfigs)
        bus.publish(mConfigs)
    }

    open fun removeApp(appPackage: String) {
        db.delApp(appPackage)
        mConfigs.remove(appPackage)
        bus.publish(mConfigs)
    }

    val config: Map<String, Map<String, AppPermission>>
        get() = Collections.unmodifiableMap(mConfigs)

    open fun changePrivilege(config: AppPermission, newPrivilege: Long) {
        var config = config
        if (config.privilege != newPrivilege) {
            config = config.setPrivilege(newPrivilege)
            mConfigs[config.appPackageName]!!.put(config.permissionName, config)
            db.putConfig(config)
            Timber.wtf("Updated config=" + config)
        }
    }

    open fun changePrivilege(appPackage: String, operation: Operation, params: RequestParams, newPrivilege: Long) {
        if (operation is SimpleOperation) {
            changePrivilege(appPackage, operation.permission, newPrivilege)
        } else if (operation is ContentOperation) {
            changePrivilege(appPackage, contentPermissionMap(operation, params), newPrivilege)
        }
    }

    private fun changePrivilege(appPackage: String, permission: String, newPrivilege: Long) {
        var permissions: MutableMap<String, AppPermission>? = mConfigs[appPackage]
        if (permissions == null) {
            permissions = ArrayMap<String, AppPermission>()
            mConfigs.put(appPackage, permissions)
        }
        var config: AppPermission? = permissions[permission]
        if (config != null) {
            config = config.setPrivilege(newPrivilege)
        } else {
            config = AppPermission(appPackage, permission, newPrivilege)

        }
        permissions.put(permission, config!!)
        db.putConfig(config)
        bus.publish(mConfigs)
    }

    @AppPermission.Res
    open fun getPermissionPrivilege(appPackage: String, operation: Operation, params: RequestParams): Long {
        if (operation is SimpleOperation) {
            return getPermissionPrivilege(appPackage, operation, params)
        } else if (operation is ContentOperation) {
            return getPermissionPrivilege(appPackage, operation, params)
        }
        return AppPermission.ALWAYS_DENY
    }

    @AppPermission.Res
    private fun getPermissionPrivilege(appPackage: String, operation: SimpleOperation, params: RequestParams): Long {
        return getPermissionPrivilege(appPackage, operation.permission)
    }

    @AppPermission.Res
    private fun getPermissionPrivilege(appPackage: String, operation: ContentOperation, request: RequestParams): Long {
        val permission = contentPermissionMap(operation, request)
        val userConfig = getPermissionPrivilege(appPackage, permission)
        Timber.wtf("permission=$permission config=$userConfig")
        return userConfig
    }

    @AppPermission.Res
    private fun getPermissionPrivilege(appPackage: String, permission: String?): Long {
        if (permission == null) { // Operation does not require any permissions? ALWAYS_ALLOW
            return AppPermission.ALWAYS_ALLOW
        }

        val permissions = mConfigs[appPackage] ?: // App not registered yet? Default to ALWAYS_ASK
                return AppPermission.ALWAYS_ASK

        val config = permissions[permission] ?: // App didn't register this permission? Default to ALWAYS_ASK
                return AppPermission.ALWAYS_ASK

        return config.privilege
    }

    private fun contentPermissionMap(operation: ContentOperation, request: RequestParams): String {
        when (request.opCode) {
            ContentRequest.SELECT -> when (operation.contentType) {
                ContentOperation.CONTENT_CALENDAR -> return Manifest.permission.READ_CALENDAR
                ContentOperation.CONTENT_CONTACTS -> return Manifest.permission.READ_CONTACTS
                ContentOperation.CONTENT_EXTERNAL_STORAGE -> return Manifest.permission.READ_EXTERNAL_STORAGE
                ContentOperation.CONTENT_SMS -> return Manifest.permission.READ_SMS
            }
            ContentRequest.INSERT, ContentRequest.UPDATE, ContentRequest.DELETE -> when (operation.contentType) {
                ContentOperation.CONTENT_CALENDAR -> return Manifest.permission.WRITE_CALENDAR
                ContentOperation.CONTENT_CONTACTS -> return Manifest.permission.WRITE_CONTACTS
                ContentOperation.CONTENT_EXTERNAL_STORAGE -> return Manifest.permission.WRITE_EXTERNAL_STORAGE
                ContentOperation.CONTENT_SMS -> return Manifest.permission.WRITE_SMS
            }
        }
        return ""
    }
}
