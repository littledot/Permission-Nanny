package com.sdchang.permissionpolice.missioncontrol;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v4.util.SimpleArrayMap;
import com.sdchang.permissionpolice.C;
import com.sdchang.permissionpolice.MySnappy;
import com.sdchang.permissionpolice.Util;
import com.sdchang.permissionpolice.dagger.Type;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;

/**
 *
 */
@Singleton
public class PermissionConfigDataManager {

    private Context mContext;
    private MySnappy mDB;

    SimpleArrayMap<String, ApplicationInfo> mApps = new SimpleArrayMap<>();
    SimpleArrayMap<ApplicationInfo, SimpleArrayMap<String, PermissionConfig>> mConfigs = new SimpleArrayMap<>();

    @Inject
    public PermissionConfigDataManager(Context context, @Type(C.TYPE_APP_PERMISSION_CONFIG) MySnappy db) {
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
        mConfigs.put(info, newConfigs);
    }

    public SimpleArrayMap<ApplicationInfo, SimpleArrayMap<String, PermissionConfig>> getConfig() {
        return mConfigs;
    }

    public void changeConfig(PermissionConfig config, int newSetting) {
        config.mSetting = newSetting;
    }

    public int getPermissionSetting(String appPackageName, String permission) {
        ApplicationInfo app = mApps.get(appPackageName);
        if (app == null) { // App not registered yet? Default to ALWAYS_ASK
            return PermissionConfig.ALWAYS_ASK;
        }

        SimpleArrayMap<String, PermissionConfig> permissions = mConfigs.get(app);
        if (permissions == null) {
            return PermissionConfig.ALWAYS_ASK;
        }

        PermissionConfig config = permissions.get(permission);
        if (config == null) { // App didn't register this permission? Default to ALWAYS_ASK
            return PermissionConfig.ALWAYS_ASK;
        }

        return config.mSetting;
    }
}
