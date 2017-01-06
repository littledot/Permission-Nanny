package com.permissionnanny.dagger

import android.app.Application
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer
import com.permissionnanny.App
import com.permissionnanny.data.*
import dagger.Module
import dagger.Provides
import net.engio.mbassy.bus.MBassador
import org.objenesis.strategy.StdInstantiatorStrategy
import java.io.File
import java.io.IOException
import javax.inject.Singleton

/**

 */
@Module
open class AppModule(private val app: App) {

    @Provides
    open fun provideApplication(): Application {
        return app
    }

    class Bus : MBassador<Any>()

    @Provides
    @Singleton
    fun provideBus(): Bus {
        return Bus()
    }

    @Provides
    fun provideKryo(): Kryo {
        val kryo = Kryo()
        kryo.setDefaultSerializer(CompatibleFieldSerializer::class.java)
        kryo.instantiatorStrategy = StdInstantiatorStrategy()
        return kryo
    }

    @Provides
    fun provideCryo(kryo: Kryo): Cryo {
        return Cryo(kryo)
    }

    @Provides
    @Singleton
    open fun providePermissionUsageDatabase(app: Application, cryo: Cryo): AppPermissionDB {
        try {
            val path = File(app.filesDir, APP_PERMISSION_DB_LEVELDB_PATH)
            return AppPermissionDB(NannyDB(path, cryo))
        } catch (e: IOException) {
            // TODO #78: Handle db exceptions gracefully
            throw RuntimeException("Dagger error")
        }

    }

    @Provides
    @Singleton
    open fun provideOngoingRequestsDatabase(app: Application, cryo: Cryo): OngoingRequestDB {
        try {
            val path = File(app.filesDir, ONGOING_REQUESTS_DB_LEVELDB_PATH)
            return OngoingRequestDB(NannyDB(path, cryo))
        } catch (e: IOException) {
            // TODO #78: Handle db exceptions gracefully
            throw RuntimeException("Dagger error")
        }

    }

    @Provides
    @Singleton
    open fun provideAppPermissionManager(app: Application, db: AppPermissionDB, bus: Bus): AppPermissionManager {
        return AppPermissionManager(app, db, bus)
    }

    companion object {

        @Deprecated("") private val APP_PERMISSION_DB_SNAPDB_PATH = "clientPermissionUsage"
        @Deprecated("") private val ONGOING_REQUESTS_DB_SNAPDB_PATH = "ongoingRequests"
        private val APP_PERMISSION_DB_LEVELDB_PATH = "dain.leveldb.appPermission.db"
        private val ONGOING_REQUESTS_DB_LEVELDB_PATH = "dain.leveldb.ongoingRequests.db"
    }
}
