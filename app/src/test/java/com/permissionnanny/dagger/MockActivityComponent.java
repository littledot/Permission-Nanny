package com.permissionnanny.dagger;

import com.permissionnanny.ConfirmRequestActivityTest;
import com.permissionnanny.missioncontrol.AppControlActivityTest;
import dagger.Component;

/**
 *
 */
@ContextComponent.ContextScope
@Component(modules = {ContextModule.class, ActivityModule.class}, dependencies = {AppComponent.class})
public interface MockActivityComponent extends ActivityComponent {

    void inject(AppControlActivityTest victim);

    void inject(ConfirmRequestActivityTest victim);
}
