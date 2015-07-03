package com.sdchang.permissionpolice.missioncontrol;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v4.util.SimpleArrayMap;
import com.sdchang.permissionpolice.C;
import com.sdchang.permissionpolice.ProxyOperation;
import com.sdchang.permissionpolice.Util;
import com.sdchang.permissionpolice.db.AppDB;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;

/**
 *
 */
@Singleton
public class PermissionConfigDataManager {

    private Context mContext;
    private AppDB mDB;

    SimpleArrayMap<String, ApplicationInfo> mApps = new SimpleArrayMap<>();
    SimpleArrayMap<ApplicationInfo, SimpleArrayMap<String, PermissionConfig>> mConfigs = new SimpleArrayMap<>();

    @Inject
    public PermissionConfigDataManager(Context context, AppDB db) {
        mContext = context;
        mDB = db;
    }

    public void registerApp(String appPackageName, ArrayList<String> permissions) {
        ApplicationInfo info = Util.getApplicationInfo(mContext, appPackageName);
        mApps.put(appPackageName, info);

        SimpleArrayMap<String, PermissionConfig> oldConfigs = mConfigs.get(info);
        SimpleArrayMap<String, PermissionConfig> newConfigs = new SimpleArrayMap<>();

        for (String permission : permissions) {
            if (oldConfigs != null) { // New app? All permissions are new
                PermissionConfig oldConfig = oldConfigs.get(permission);
                if (oldConfig != null) { // User config exists? Retain it
                    newConfigs.put(permission, oldConfig);
                    continue;
                }
            }
            // New permission usage? Create new entry
            newConfigs.put(permission, new PermissionConfig(appPackageName, permission, PermissionConfig.ALWAYS_ASK));
        }
        // TODO #39: Persist settings
        mConfigs.put(info, newConfigs);
    }

    public SimpleArrayMap<ApplicationInfo, SimpleArrayMap<String, PermissionConfig>> getConfig() {
        return mConfigs;
    }

    public void changeConfig(PermissionConfig config, int newSetting) {
        // TODO #39: Persist settings
        config.mSetting = newSetting;
    }

    @PermissionConfig.UserSetting
    public int getPermissionSetting(String appPackageName, ProxyOperation operation) {
        String permission = operation.mPermission;
        if (permission == null) { // Operation does not require any permissions? ALWAYS_ALLOW
            return PermissionConfig.ALWAYS_ALLOW;
        }

        ApplicationInfo app = mApps.get(appPackageName);
        if (app == null) { // App not registered yet? Default to ALWAYS_ASK
            return PermissionConfig.ALWAYS_ASK;
        }

        SimpleArrayMap<String, PermissionConfig> permissions = mConfigs.get(app);
        if (permissions == null) { // Null safety - this should never happen though
            return PermissionConfig.ALWAYS_ASK;
        }

        PermissionConfig config = permissions.get(permission);
        if (config == null) { // App didn't register this permission? Default to ALWAYS_ASK
            return PermissionConfig.ALWAYS_ASK;
        }

        return config.mSetting;
    }
}
