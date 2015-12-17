package com.permissionnanny.dagger;

import com.permissionnanny.ClientPermissionManifestReceiverTest;
import com.permissionnanny.ClientRequestReceiverTest;
import com.permissionnanny.ConfirmRequestBinderTest;
import com.permissionnanny.UninstallReceiverTest;

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
}
