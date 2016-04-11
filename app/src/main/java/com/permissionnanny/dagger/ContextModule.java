package com.permissionnanny.dagger;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.permissionnanny.ProxyExecutor;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.missioncontrol.AppControlBinder;
import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ContextModule {

    private final Context mContext;
    private AppCompatActivity mAppCompatActivity;

    public ContextModule(Context context) {
        mContext = context;
    }

    public ContextModule(AppCompatActivity appCompatActivity) {
        mContext = appCompatActivity;
        mAppCompatActivity = appCompatActivity;
    }

    @Provides
    @ContextComponent.ContextScope
    Context provideContext() {
        return mContext;
    }

    @Provides
    @ContextComponent.ContextScope
    AppCompatActivity provideAppCompatActivity() {
        return mAppCompatActivity;
    }

    @Provides
    @ContextComponent.ContextScope
    ProxyExecutor provideProxyExecutor(Context context) {
        return new ProxyExecutor(context);
    }

    @Provides
    @ContextComponent.ContextScope
    AppControlBinder provideAppControlBinder(AppCompatActivity appCompatActivity,
                                             AppPermissionManager appPermissionManager,
                                             AppModule.Bus bus) {
        return new AppControlBinder(appCompatActivity, appPermissionManager, bus);
    }
}
