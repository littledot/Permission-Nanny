package com.permissionnanny.dagger;

import android.content.Context;
import com.permissionnanny.ClientPermissionUsageReceiver;
import com.permissionnanny.ClientRequestReceiver;
import com.permissionnanny.db.AppDB;
import com.permissionnanny.missioncontrol.PermissionConfigDataManager;
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

    void inject(ClientPermissionUsageReceiver victim);

    void inject(ClientRequestReceiver victim);
}
