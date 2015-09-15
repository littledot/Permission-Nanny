package com.permissionnanny.dagger;

import android.app.Application;
import com.permissionnanny.data.AppDB;
import com.permissionnanny.data.AppPermissionManager;
import dagger.Component;
import io.snapdb.SnapDB;

import javax.inject.Singleton;

/**
 *
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Application app();

    SnapDB db();

    AppDB db2();

    AppModule.Bus bus();

    AppPermissionManager appPermissionManager();
}
