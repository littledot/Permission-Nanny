package com.permissionnanny.dagger;

import com.permissionnanny.ClientPermissionManifestReceiverTest;
import com.permissionnanny.ClientRequestReceiverTest;
import com.permissionnanny.ConfirmRequestActivityTest;
import com.permissionnanny.ConfirmRequestBinderTest;
import com.permissionnanny.UninstallReceiverTest;
import com.permissionnanny.missioncontrol.AppControlActivityTest;

/**
 * Dependency injection for tests.
 */
@ContextComponent.ContextScope
@dagger.Component(modules = {ContextModule.class}, dependencies = AppComponent.class)
public interface MockContextComponent extends ContextComponent {

    void inject(UninstallReceiverTest victim);

    void inject(ClientPermissionManifestReceiverTest victim);

    void inject(ClientRequestReceiverTest victim);

    void inject(ConfirmRequestBinderTest victim);

    void inject(AppControlActivityTest victim);

    void inject(ConfirmRequestActivityTest victim);
}
