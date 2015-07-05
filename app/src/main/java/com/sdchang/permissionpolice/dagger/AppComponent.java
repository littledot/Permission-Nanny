package com.sdchang.permissionpolice.dagger;

import android.content.Context;
import com.sdchang.permissionpolice.AppPermissionUsageReceiver;
import com.sdchang.permissionpolice.ExternalRequestReceiver;
import com.sdchang.permissionpolice.db.AppDB;
import com.sdchang.permissionpolice.missioncontrol.PermissionConfigDataManager;
import dagger.Component;
import io.snapdb.SnapDB;

import javax.inject.Singleton;

/**
 *
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context appContext();

    SnapDB db();

    AppDB db2();

    AppModule.Bus bus();

    PermissionConfigDataManager pcdm();

    void inject(AppPermissionUsageReceiver victim);

    void inject(ExternalRequestReceiver victim);
}
