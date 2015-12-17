package com.permissionnanny.dagger;

import android.content.Context;
import com.permissionnanny.ProxyExecutor;
import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ContextModule {

    private final Context mContext;

    public ContextModule(Context context) {
        mContext = context;
    }

    @Provides
    @ContextComponent.ContextScope
    Context provideContext() {
        return mContext;
    }

    @Provides
    @ContextComponent.ContextScope
    ProxyExecutor provideProxyExecutor(Context context) {
        return new ProxyExecutor(context);
    }
}
