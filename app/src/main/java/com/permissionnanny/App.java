package com.permissionnanny;

import android.app.Application;
import com.permissionnanny.common.StackTraceDebugTree;
import com.permissionnanny.dagger.AppComponent;
import com.permissionnanny.dagger.AppModule;
import com.permissionnanny.dagger.DaggerAppComponent;
import com.permissionnanny.dagger.DaggerAppComponent.Builder;
import timber.log.Timber;

/**
 *
 */
public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new StackTraceDebugTree());
    }

    /**
     * @param builder
     * @return
     */
    protected Builder buildAppComponent(Builder builder) {
        return builder.appModule(new AppModule(this));
    }

    /**
     * @return
     */
    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = buildAppComponent(DaggerAppComponent.builder()).build();
        }
        return mAppComponent;
    }
}
