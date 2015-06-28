package com.sdchang.permissionpolice.dagger;

import android.content.Context;
import com.sdchang.permissionpolice.AppPermissionUsageReceiver;
import com.sdchang.permissionpolice.C;
import com.sdchang.permissionpolice.ExternalRequestReceiver;
import com.sdchang.permissionpolice.MySnappy;
import com.sdchang.permissionpolice.missioncontrol.PermissionConfigDataManager;
import dagger.Component;

import javax.inject.Singleton;

/**
 *
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context appContext();

    @Type(C.TYPE_APP_PERMISSION_CONFIG)
    MySnappy db();

    @Type(C.TYPE_ONGOING_REQUESTS)
    MySnappy db2();

    PermissionConfigDataManager pcdm();

    void inject(AppPermissionUsageReceiver victim);

    void inject(ExternalRequestReceiver victim);
}
