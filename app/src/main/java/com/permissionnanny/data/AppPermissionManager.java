package com.permissionnanny.data;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import com.permissionnanny.App;
import com.permissionnanny.Manifest;
import com.permissionnanny.Operation;
import com.permissionnanny.content.ContentOperation;
import com.permissionnanny.dagger.AppModule;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.content.ContentRequest;
import com.permissionnanny.simple.SimpleOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import timber.log.Timber;

/**
 * Manager that handles applications' permissions data.
 */
@Singleton
public class AppPermissionManager {

    private final Application mContext;
    private final AppPermissionDB mDB;
    private final AppModule.Bus mBus;

    /** Index definition: {App Name: {Permission Name: Privilege}} */
    Map<String, Map<String, AppPermission>> mConfigs = new ArrayMap<>();

    @Inject
    public AppPermissionManager(Application context, AppPermissionDB db, AppModule.Bus bus) {
        mContext = context;
        mDB = db;
        mBus = bus;
        readDB();
    }

    public void refreshData() {
        Bundle entity = new Bundle();
        entity.putParcelable(Nanny.SENDER_IDENTITY, App.IDENTITY);

        Intent uses = new Intent(Nanny.ACTION_GET_PERMISSION_MANIFEST)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.ENTITY_BODY, entity);
        mContext.sendBroadcast(uses);
    }

    private void readDB() {
        List<AppPermission> configs = mDB.getAllConfigs();
        Timber.wtf("Read " + Arrays.toString(configs.toArray()));

        for (int i = 0, len = configs.size(); i < len; i++) {
            AppPermission config = configs.get(i);
            Map<String, AppPermission> configMap = mConfigs.get(config.mAppPackageName);
            if (configMap == null) {
                configMap = new ArrayMap<>();
                mConfigs.put(config.mAppPackageName, configMap);
            }

            configMap.put(config.mPermissionName, config);
        }
    }

    public void readPermissionManifest(String appPackage, ArrayList<String> permissions) {
        Map<String, AppPermission> oldConfigs = mConfigs.get(appPackage);
        Map<String, AppPermission> newConfigs = oldConfigs == null ? new ArrayMap<String, AppPermission>() : oldConfigs;

        for (String permission : permissions) {
            AppPermission config = newConfigs.get(permission);
            if (config == null) { // New permission config? Create new entry
                AppPermission newConfig = new AppPermission(appPackage, permission, AppPermission.ALWAYS_ASK);
                newConfigs.put(permission, newConfig);
                mDB.putConfig(newConfig);
                Timber.wtf("New config=" + newConfig);
            }
        }

        mConfigs.put(appPackage, newConfigs);
        mBus.publish(mConfigs);
    }

    public void removeApp(String appPackage) {
        mDB.delApp(appPackage);
        mConfigs.remove(appPackage);
        mBus.publish(mConfigs);
    }

    public Map<String, Map<String, AppPermission>> getConfig() {
        return Collections.unmodifiableMap(mConfigs);
    }

    public void changePrivilege(AppPermission config, int newPrivilege) {
        if (config.mPrivilege != newPrivilege) {
            config = config.setPrivilege(newPrivilege);
            mConfigs.get(config.mAppPackageName).put(config.mPermissionName, config);
            mDB.putConfig(config);
            Timber.wtf("Updated config=" + config);
        }
    }

    public void changePrivilege(String appPackage, Operation operation, RequestParams params, int newPrivilege) {
        if (operation instanceof SimpleOperation) {
            changePrivilege(appPackage, ((SimpleOperation) operation).mPermission, newPrivilege);
        } else if (operation instanceof ContentOperation) {
            changePrivilege(appPackage, contentPermissionMap((ContentOperation) operation, params), newPrivilege);
        }
    }

    private void changePrivilege(String appPackage, String permission, int newPrivilege) {
        Map<String, AppPermission> permissions = mConfigs.get(appPackage);
        if (permissions == null) {
            permissions = new ArrayMap<>();
            mConfigs.put(appPackage, permissions);
        }
        AppPermission config = permissions.get(permission);
        if (config == null) {
            config = new AppPermission(appPackage, permission, newPrivilege);
        } else {
            config = config.setPrivilege(newPrivilege);
        }
        permissions.put(permission, config);
        mDB.putConfig(config);
        mBus.publish(mConfigs);
    }

    @AppPermission.Res
    public int getPermissionPrivilege(String appPackage, Operation operation, RequestParams params) {
        if (operation instanceof SimpleOperation) {
            return getPermissionPrivilege(appPackage, (SimpleOperation) operation, params);
        } else if (operation instanceof ContentOperation) {
            return getPermissionPrivilege(appPackage, (ContentOperation) operation, params);
        }
        return AppPermission.ALWAYS_DENY;
    }

    @AppPermission.Res
    private int getPermissionPrivilege(String appPackage, SimpleOperation operation, RequestParams params) {
        return getPermissionPrivilege(appPackage, operation.mPermission);
    }

    @AppPermission.Res
    private int getPermissionPrivilege(String appPackage, ContentOperation operation, RequestParams request) {
        String permission = contentPermissionMap(operation, request);
        int userConfig = getPermissionPrivilege(appPackage, permission);
        Timber.wtf("permission=" + permission + " config=" + userConfig);
        return userConfig;
    }

    @AppPermission.Res
    private int getPermissionPrivilege(String appPackage, String permission) {
        if (permission == null) { // Operation does not require any permissions? ALWAYS_ALLOW
            return AppPermission.ALWAYS_ALLOW;
        }

        Map<String, AppPermission> permissions = mConfigs.get(appPackage);
        if (permissions == null) { // App not registered yet? Default to ALWAYS_ASK
            return AppPermission.ALWAYS_ASK;
        }

        AppPermission config = permissions.get(permission);
        if (config == null) { // App didn't register this permission? Default to ALWAYS_ASK
            return AppPermission.ALWAYS_ASK;
        }

        return config.mPrivilege;
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
