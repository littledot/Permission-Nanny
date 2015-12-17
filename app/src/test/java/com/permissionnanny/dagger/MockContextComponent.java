package com.permissionnanny.dagger;

import com.permissionnanny.UninstallReceiverTest;

/**
 * Dependency injection for tests.
 */
@ContextComponent.ContextScope
@dagger.Component(modules = {ContextModule.class}, dependencies = AppComponent.class)
public interface MockContextComponent extends ContextComponent {

    void inject(UninstallReceiverTest victim);
}
