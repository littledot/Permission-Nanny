package com.sdchang.permissionnanny.dagger;

import android.content.Context;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import com.litl.leveldb.DB;
import com.sdchang.permissionnanny.App;
import com.sdchang.permissionnanny.db.AppDB;
import dagger.Module;
import dagger.Provides;
import io.snapdb.SnapDB;
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
    SnapDB provideMySnappy(Context context, Kryo kryo) {
        SnapDB db = new SnapDB(new DB(new File(context.getFilesDir(), "ongoingRequests")), kryo);
        db.open();
        return db;
    }

    @Provides
    @Singleton
    AppDB providePermissionUsageDatabase(Context context, Kryo kryo) {
        DB leveldb = new DB(new File(context.getFilesDir(), "clientPermissionUsage"));
        SnapDB snapdb = new SnapDB(leveldb, kryo);
        AppDB db = new AppDB(snapdb);
        db.open();
        return db;
    }
}
