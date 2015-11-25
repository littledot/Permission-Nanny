package com.permissionnanny.dagger;

import android.app.Application;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import com.permissionnanny.App;
import com.permissionnanny.data.AppPermissionDB;
import com.permissionnanny.data.Cryo;
import com.permissionnanny.data.NannyDB;
import com.permissionnanny.data.OngoingRequestDB;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.io.IOException;
import javax.inject.Singleton;
import net.engio.mbassy.bus.MBassador;
import org.objenesis.strategy.StdInstantiatorStrategy;
import timber.log.Timber;

/**
 *
 */
@Module
public class AppModule {

    @Deprecated private static final String APP_PERMISSION_DB_SNAPDB_PATH = "clientPermissionUsage";
    @Deprecated private static final String ONGOING_REQUESTS_DB_SNAPDB_PATH = "ongoingRequests";
    private static final String APP_PERMISSION_DB_LEVELDB_PATH = "dain.leveldb.appPermission.db";
    private static final String ONGOING_REQUESTS_DB_LEVELDB_PATH = "dain.leveldb.ongoingRequests.db";

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
    Cryo provideCryo(Kryo kryo) {
        return new Cryo(kryo);
    }

    @Provides
    @Singleton
    AppPermissionDB providePermissionUsageDatabase(Application app, Cryo cryo) {
        try {
            File path = new File(app.getFilesDir(), APP_PERMISSION_DB_LEVELDB_PATH);
            return new AppPermissionDB(new NannyDB(path, cryo));
        } catch (IOException e) {
            // TODO #78: Handle db exceptions gracefully
            Timber.e(e, "appPermission");
        }
        return null;
    }

    @Provides
    @Singleton
    OngoingRequestDB provideOngoingRequestsDatabase(Application app, Cryo cryo) {
        try {
            File path = new File(app.getFilesDir(), ONGOING_REQUESTS_DB_LEVELDB_PATH);
            return new OngoingRequestDB(new NannyDB(path, cryo));
        } catch (IOException e) {
            // TODO #78: Handle db exceptions gracefully
            Timber.e(e, "ongoing");
        }
        return null;
    }
}
