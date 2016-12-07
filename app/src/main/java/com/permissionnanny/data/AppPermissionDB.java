package com.permissionnanny.data;

import java.util.ArrayList;

/**
 *
 */
public class AppPermissionDB {

    private static final String pipe = "\u0378\u0379";

    private final NannyDB mDB;

    public AppPermissionDB(NannyDB db) {
        mDB = db;
    }

    public void open() {
        mDB.open();
    }

    public ArrayList<AppPermission> getAllConfigs() {
        return mDB.findVals(null, AppPermission.class);
    }

    public void putConfig(AppPermission config) {
        mDB.put(key(config), config);
    }

    public void delConfig(String appPackage, String permission) {
        mDB.del(key(appPackage, permission));
    }

    public void delApp(String appPackage) {
        for (String key : mDB.findKeys(appPackage)) {
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
        return appPackage + pipe + permission;
    }
}
