package com.sdchang.permissionnanny.dagger;

import android.content.Context;
import com.sdchang.permissionnanny.AppPermissionUsageReceiver;
import com.sdchang.permissionnanny.ExternalRequestReceiver;
import com.sdchang.permissionnanny.db.AppDB;
import com.sdchang.permissionnanny.missioncontrol.PermissionConfigDataManager;
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
