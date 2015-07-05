package com.sdchang.permissionpolice.db;

import com.sdchang.permissionpolice.missioncontrol.PermissionConfig;
import io.snapdb.SnapDB;

import javax.inject.Inject;

/**
 *
 */
public class AppDB {

    private static final String _ = "\u0378\u0379";
    private static final String CONFIG = "config" + _;
    private SnapDB mDB;

    @Inject
    public AppDB(SnapDB db) {
        mDB = db;
    }

    public void open() {
        mDB.open();
    }

    public PermissionConfig[] getAllConfigs() {
        return mDB.findVals(CONFIG, PermissionConfig.class);
    }

    public void putConfig(PermissionConfig config) {
        mDB.put(key(config), config);
    }

    public void delConfig(String appPackage, String permission) {
        mDB.del(key(appPackage, permission));
    }

    public void delConfig(PermissionConfig config) {
        mDB.del(key(config));
    }

    private String key(PermissionConfig config) {
        return key(config.appPackageName, config.permissionName);
    }

    private String key(String appPackage, String permission) {
        return CONFIG + appPackage + _ + permission;
    }
}
