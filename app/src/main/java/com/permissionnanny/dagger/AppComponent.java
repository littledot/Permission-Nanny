package com.permissionnanny.dagger;

import android.app.Application;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.data.RecurringRequestDB;
import dagger.Component;

import javax.inject.Singleton;

/**
 *
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Application app();

    AppModule.Bus bus();

    AppPermissionManager appPermissionManager();

    RecurringRequestDB recurringRequestDB();
}
