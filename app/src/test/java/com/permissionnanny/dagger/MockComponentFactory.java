package com.permissionnanny.dagger;

/**
 *
 */
public class MockComponentFactory {

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder().appModule(new MockAppModule()).build();
    }
}
