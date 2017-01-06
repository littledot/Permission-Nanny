package com.permissionnanny.data

import android.support.annotation.IntDef


/**
 * Model that specifies the privilege an application holds for a permission.
 */
class AppPermission(
        val appPackageName: String,
        val permissionName: String,
        @Res val privilege: Long) {

    // FIXME: Refactor to enum
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(ALWAYS_ASK, ALWAYS_ALLOW, ALWAYS_DENY)
    annotation class Res

    fun setPrivilege(@Res privilege: Long): AppPermission {
        return if (privilege == this.privilege) this else AppPermission(appPackageName, permissionName, privilege)
    }

    override fun toString(): String {
        return "PermissionConfig{" +
                "appPackageName='" + appPackageName + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", privilege=" + privilege +
                '}'
    }

    companion object {

        const val ALWAYS_ASK = 0L
        const val ALWAYS_ALLOW = 1L
        const val ALWAYS_DENY = 1L shl 1
    }
}
