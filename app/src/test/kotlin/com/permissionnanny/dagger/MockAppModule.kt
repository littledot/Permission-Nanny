package com.permissionnanny.dagger

import android.app.Application
import com.permissionnanny.App
import com.permissionnanny.data.AppPermissionDB
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.data.Cryo
import com.permissionnanny.data.OngoingRequestDB
import org.mockito.Mockito.mock

/**
 * [AppModule] that returns mocks.
 */
class MockAppModule : AppModule(mock(App::class.java)) {

    override fun provideApplication(): Application {
        return mock(Application::class.java)
    }

    override fun providePermissionUsageDatabase(app: Application, cryo: Cryo): AppPermissionDB {
        return mock(AppPermissionDB::class.java)
    }

    override fun provideOngoingRequestsDatabase(app: Application, cryo: Cryo): OngoingRequestDB {
        return mock(OngoingRequestDB::class.java)
    }

    override fun provideAppPermissionManager(app: Application, db: AppPermissionDB, bus: AppModule.Bus): AppPermissionManager {
        return mock(AppPermissionManager::class.java)
    }
}
