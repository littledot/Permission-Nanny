package com.sdchang.permissionpolice.dagger;

import android.content.Context;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import com.litl.leveldb.DB;
import com.sdchang.permissionpolice.App;
import com.sdchang.permissionpolice.C;
import com.sdchang.permissionpolice.db.CryDB;
import dagger.Module;
import dagger.Provides;
import net.engio.mbassy.bus.MBassador;

import javax.inject.Singleton;
import java.io.File;

/**
 *
 */
@Module
public class AppModule {

    App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    Context provideContext() {
        return mApp;
    }

    public static class Bus extends MBassador<Object> {}

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    Kryo providesKryo() {
        Kryo kryo = new Kryo();
        kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
        return kryo;
    }

    @Provides
    @Singleton
    @Type(C.TYPE_ONGOING_REQUESTS)
    CryDB provideMySnappy(Context context, Kryo kryo) {
        CryDB db = new CryDB(new DB(new File(context.getFilesDir(), "ongoingRequests")), kryo);
        db.open();
        return db;
    }

    @Provides
    @Singleton
    @Type(C.TYPE_APP_PERMISSION_CONFIG)
    CryDB providePermissionUsageDatabase(Context context, Kryo kryo) {
        CryDB db = new CryDB(new DB(new File(context.getFilesDir(), "clientPermissionUsage")), kryo);
        db.open();
        return db;
    }
}
