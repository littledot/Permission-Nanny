package com.permissionnanny.missioncontrol;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.SimpleArrayMap;
import com.permissionnanny.Operation;
import com.permissionnanny.content.ContentOperation;
import com.permissionnanny.dagger.AppModule;
import com.permissionnanny.db.AppDB;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.content.ContentRequest;
import com.permissionnanny.operation.ProxyOperation;
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
        Intent uses = new Intent(Nanny.ACTION_GET_PERMISSION_USAGES)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_1_0);
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
    public int getPermissionSetting(String appPackage, Operation operation, RequestParams params) {
        if (operation instanceof ProxyOperation) {
            return getPermissionSetting(appPackage, (ProxyOperation) operation, params);
        } else if (operation instanceof ContentOperation) {
            return getPermissionSetting(appPackage, (ContentOperation) operation, params);
        }
        return PermissionConfig.ALWAYS_DENY;
    }

    @PermissionConfig.UserSetting
    private int getPermissionSetting(String appPackage, ProxyOperation operation, RequestParams params) {
        return getUserConfig(appPackage, operation.mPermission);
    }

    @PermissionConfig.UserSetting
    private int getPermissionSetting(String appPackage, ContentOperation operation, RequestParams request) {
        String permission = contentPermissionMap(operation, request);
        int userConfig = getUserConfig(appPackage, permission);
        Timber.wtf("permission=" + permission + " config=" + userConfig);
        return userConfig;
    }

    @PermissionConfig.UserSetting
    private int getUserConfig(String appPackage, String permission) {
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

    private String contentPermissionMap(ContentOperation operation, RequestParams request) {
        switch (request.opCode) {
        case ContentRequest.SELECT:
            switch (operation.mContentType) {
            case ContentOperation.CONTENT_CALENDAR:
                return Manifest.permission.READ_CALENDAR;
            case ContentOperation.CONTENT_CONTACTS:
                return Manifest.permission.READ_CONTACTS;
            case ContentOperation.CONTENT_EXTERNAL_STORAGE:
                return Manifest.permission.READ_EXTERNAL_STORAGE;
            case ContentOperation.CONTENT_SMS:
                return Manifest.permission.READ_SMS;
            }
            break;
        case ContentRequest.INSERT:
        case ContentRequest.UPDATE:
        case ContentRequest.DELETE:
            switch (operation.mContentType) {
            case ContentOperation.CONTENT_CALENDAR:
                return Manifest.permission.WRITE_CALENDAR;
            case ContentOperation.CONTENT_CONTACTS:
                return Manifest.permission.WRITE_CONTACTS;
            case ContentOperation.CONTENT_EXTERNAL_STORAGE:
                return Manifest.permission.WRITE_EXTERNAL_STORAGE;
            case ContentOperation.CONTENT_SMS:
                return Manifest.permission.WRITE_SMS;
            }
            break;
        }
        return null;
    }
}
