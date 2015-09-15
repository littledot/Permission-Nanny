package com.permissionnanny.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Model that specifies the privilege an application holds for a permission.
 */
public class AppPermission {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {ALWAYS_ASK, ALWAYS_ALLOW, ALWAYS_DENY})
    public @interface Res {}

    public static final int ALWAYS_ASK = 0;
    public static final int ALWAYS_ALLOW = 1;
    public static final int ALWAYS_DENY = 1 << 1;

    public final String mAppPackageName;
    public final String mPermissionName;
    @Res public final int mPrivilege;

    public AppPermission(String appPackageName, String permissionName, @Res int privilege) {
        mAppPackageName = appPackageName;
        mPermissionName = permissionName;
        mPrivilege = privilege;
    }

    public AppPermission setPrivilege(@Res int privilege) {
        return privilege == mPrivilege ? this : new AppPermission(mAppPackageName, mPermissionName, privilege);
    }

    @Override
    public String toString() {
        return "PermissionConfig{" +
                "appPackageName='" + mAppPackageName + '\'' +
                ", permissionName='" + mPermissionName + '\'' +
                ", privilege=" + mPrivilege +
                '}';
    }
}
