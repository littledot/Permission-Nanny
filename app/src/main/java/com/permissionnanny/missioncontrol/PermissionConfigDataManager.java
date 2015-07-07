package com.permissionnanny.missioncontrol;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.SimpleArrayMap;
import com.permissionnanny.ProxyOperation;
import com.permissionnanny.dagger.AppModule;
import com.permissionnanny.db.AppDB;
import com.permissionnanny.lib.Nanny;
import timber.log.Timber;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
@Singleton
public class PermissionConfigDataManager {

    private Context mContext;
    private AppDB mDB;
    private AppModule.Bus mBus;

    SimpleArrayMap<String, SimpleArrayMap<String, PermissionConfig>> mConfigs = new SimpleArrayMap<>();

    @Inject
    public PermissionConfigDataManager(Context context, AppDB db, AppModule.Bus bus) {
        mContext = context;
        mDB = db;
        mBus = bus;
        readDB();
    }

    public void refreshData() {
        Intent uses = new Intent(Nanny.ACTION_GET_PERMISSION_USAGES).setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        mContext.sendBroadcast(uses);
    }

    private void readDB() {
        PermissionConfig[] configs = mDB.getAllConfigs();
        Timber.wtf("Read " + Arrays.toString(configs));

        for (PermissionConfig config : configs) {
            SimpleArrayMap<String, PermissionConfig> configMap = mConfigs.get(config.appPackageName);
            if (configMap == null) {
                configMap = new SimpleArrayMap<>();
                mConfigs.put(config.appPackageName, configMap);
            }

            configMap.put(config.permissionName, config);
        }
    }

    public void registerApp(String appPackage, ArrayList<String> permissions) {
        SimpleArrayMap<String, PermissionConfig> oldConfigs = mConfigs.get(appPackage);
        SimpleArrayMap<String, PermissionConfig> newConfigs = new SimpleArrayMap<>();

        for (String permission : permissions) {
            if (oldConfigs != null) { // Existing app? Look for existing configs
                PermissionConfig oldConfig = oldConfigs.get(permission);
                if (oldConfig != null) { // Permission config exists? Retain it
                    Timber.wtf("Existing config=" + oldConfig);
                    newConfigs.put(permission, oldConfig);
                    oldConfigs.remove(permission);
                    continue;
                }
            }
            // New permission config? Create new entry
            PermissionConfig newConfig = new PermissionConfig(appPackage, permission, PermissionConfig.ALWAYS_ASK);
            newConfigs.put(permission, newConfig);
            mDB.putConfig(newConfig);
            Timber.wtf("New config=" + newConfig);
        }

        if (oldConfigs != null) { // Delete permission configs that are no longer in use
            for (int i = 0, l = oldConfigs.size(); i < l; i++) {
                mDB.delConfig(oldConfigs.valueAt(i));
                Timber.wtf("Delete config=" + oldConfigs.valueAt(i));
            }
        }

        mConfigs.put(appPackage, newConfigs);
        mBus.publish(mConfigs);
    }

    public SimpleArrayMap<String, SimpleArrayMap<String, PermissionConfig>> getConfig() {
        return mConfigs;
    }

    public void changeConfig(PermissionConfig config, int newSetting) {
        if (config.setting != newSetting) {
            config.setting = newSetting;
            mDB.putConfig(config);
            Timber.wtf("Updated config=" + config);
        }
    }

    @PermissionConfig.UserSetting
    public int getPermissionSetting(String appPackage, ProxyOperation operation) {
        String permission = operation.mPermission;
        if (permission == null) { // Operation does not require any permissions? ALWAYS_ALLOW
            return PermissionConfig.ALWAYS_ALLOW;
        }

        SimpleArrayMap<String, PermissionConfig> permissions = mConfigs.get(appPackage);
        if (permissions == null) { // App not registered yet? Default to ALWAYS_ASK
            return PermissionConfig.ALWAYS_ASK;
        }

        PermissionConfig config = permissions.get(permission);
        if (config == null) { // App didn't register this permission? Default to ALWAYS_ASK
            return PermissionConfig.ALWAYS_ASK;
        }

        return config.setting;
    }
}
