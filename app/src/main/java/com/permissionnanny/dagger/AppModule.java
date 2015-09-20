package com.permissionnanny.dagger;

import android.app.Application;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import com.litl.leveldb.DB;
import com.permissionnanny.App;
import com.permissionnanny.data.AppPermissionDB;
import com.permissionnanny.data.OngoingRequestDB;
import dagger.Module;
import dagger.Provides;
import io.snapdb.SnapDB;
import net.engio.mbassy.bus.MBassador;
import org.objenesis.strategy.StdInstantiatorStrategy;

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
    Application provideApplication() {
        return mApp;
    }

    public static class Bus extends MBassador<Object> {}

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    Kryo provideKryo() {
        Kryo kryo = new Kryo();
        kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        return kryo;
    }

    @Provides
    @Singleton
    AppPermissionDB providePermissionUsageDatabase(Application app, Kryo kryo) {
        DB leveldb = new DB(new File(app.getFilesDir(), "clientPermissionUsage"));
        SnapDB snapdb = new SnapDB(leveldb, kryo);
        AppPermissionDB db = new AppPermissionDB(snapdb);
        db.open();
        return db;
    }

    @Provides
    @Singleton
    OngoingRequestDB provideOngoingRequestsDatabase(Application app, Kryo kryo) {
        DB leveldb = new DB(new File(app.getFilesDir(), "ongoingRequests"));
        SnapDB snapdb = new SnapDB(leveldb, kryo);
        OngoingRequestDB db = new OngoingRequestDB(snapdb);
        db.open();
        return db;
    }
}
