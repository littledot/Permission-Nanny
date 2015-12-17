package com.permissionnanny.dagger;

import android.app.Application;
import com.permissionnanny.data.AppPermissionDB;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.data.Cryo;
import com.permissionnanny.data.OngoingRequestDB;

import static org.mockito.Mockito.mock;

/**
 * {@link AppModule} that returns mocks.
 */
public class MockAppModule extends AppModule {

    public MockAppModule() {
        super(null);
    }

    @Override
    Application provideApplication() {
        return mock(Application.class);
    }

    @Override
    AppPermissionDB providePermissionUsageDatabase(Application app, Cryo cryo) {
        return mock(AppPermissionDB.class);
    }

    @Override
    OngoingRequestDB provideOngoingRequestsDatabase(Application app, Cryo cryo) {
        return mock(OngoingRequestDB.class);
    }

    @Override
    AppPermissionManager provideAppPermissionManager(Application app, AppPermissionDB db, Bus bus) {
        return mock(AppPermissionManager.class);
    }
}
