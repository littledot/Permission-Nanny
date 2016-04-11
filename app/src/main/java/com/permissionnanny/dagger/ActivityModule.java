package com.permissionnanny.dagger;

import android.support.v7.app.AppCompatActivity;
import com.permissionnanny.ConfirmRequestBinder;
import com.permissionnanny.ProxyExecutor;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.missioncontrol.AppControlBinder;
import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ActivityModule {

    private AppCompatActivity mAppCompatActivity;

    public ActivityModule(AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    }

    @Provides
    @ContextComponent.ContextScope
    AppCompatActivity provideAppCompatActivity() {
        return mAppCompatActivity;
    }

    @Provides
    @ContextComponent.ContextScope
    AppControlBinder provideAppControlBinder(AppCompatActivity appCompatActivity,
                                             AppPermissionManager appPermissionManager,
                                             AppModule.Bus bus) {
        return new AppControlBinder(appCompatActivity, appPermissionManager, bus);
    }

    @Provides
    @ContextComponent.ContextScope
    ConfirmRequestBinder provideConfirmRequestBinder(AppCompatActivity appCompatActivity,
                                                     ProxyExecutor proxyExecutor,
                                                     AppPermissionManager appPermissionManager) {
        return new ConfirmRequestBinder(
                appCompatActivity,
                new NannyBundle(appCompatActivity.getIntent().getExtras()),
                proxyExecutor,
                appPermissionManager);
    }
}
