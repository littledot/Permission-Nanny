package com.permissionnanny.data;

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

    public AppPermission[] getAllConfigs() {
        return mDB.findVals(CONFIG, AppPermission.class);
    }

    public void putConfig(AppPermission config) {
        mDB.put(key(config), config);
    }

    public void delConfig(String appPackage, String permission) {
        mDB.del(key(appPackage, permission));
    }

    public void delApp(String appPackage) {
        for (String key : mDB.findKeys(CONFIG + appPackage)) {
            mDB.del(key);
        }
    }

    public void delConfig(AppPermission config) {
        mDB.del(key(config));
    }

    private String key(AppPermission config) {
        return key(config.mAppPackageName, config.mPermissionName);
    }

    private String key(String appPackage, String permission) {
        return CONFIG + appPackage + _ + permission;
    }
}
