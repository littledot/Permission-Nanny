package com.sdchang.permissionpolice.missioncontrol;

/**
 *
 */
public class PermissionConfig {

    public static final int ALWAYS_ASK = 0;
    public static final int ALWAYS_ALLOW = 1;
    public static final int ALWAYS_DENY = 1 << 1;

    public String mAppPackageName;
    public String mPermissionName;
    public int mSetting;

    public PermissionConfig(String appPackageName, String permissionName, int setting) {
        mAppPackageName = appPackageName;
        mPermissionName = permissionName;
        mSetting = setting;
    }
}
