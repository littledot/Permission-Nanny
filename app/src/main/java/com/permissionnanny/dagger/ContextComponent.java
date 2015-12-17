package com.permissionnanny.dagger;

import android.content.Context;
import com.permissionnanny.ClientPermissionManifestReceiver;
import com.permissionnanny.ClientRequestReceiver;
import com.permissionnanny.ConfirmRequestBinder;
import com.permissionnanny.ProxyService;
import com.permissionnanny.UninstallReceiver;
import com.permissionnanny.dagger.ContextComponent.ContextScope;
import com.permissionnanny.missioncontrol.AppControlBinder;
import dagger.Component;
import javax.inject.Scope;

/**
 *
 */
@ContextScope
@Component(modules = {ContextModule.class}, dependencies = AppComponent.class)
public interface ContextComponent {

    @Scope
    @interface ContextScope {}

    Context context();

    void inject(ProxyService victim);

    void inject(ClientRequestReceiver victom);

    void inject(ClientPermissionManifestReceiver victim);

    void inject(UninstallReceiver victim);

    void inject(ConfirmRequestBinder victim);

    void inject(AppControlBinder victim);
}
