package com.sdchang.permissionpolice.missioncontrol;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
public class PermissionConfig {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {ALWAYS_ASK, ALWAYS_ALLOW, ALWAYS_DENY})
    public @interface UserSetting {}

    public static final int ALWAYS_ASK = 0;
    public static final int ALWAYS_ALLOW = 1;
    public static final int ALWAYS_DENY = 1 << 1;

    public String mAppPackageName;
    public String mPermissionName;
    @UserSetting public int mSetting;

    public PermissionConfig(String appPackageName, String permissionName, int setting) {
        mAppPackageName = appPackageName;
        mPermissionName = permissionName;
        mSetting = setting;
    }
}
