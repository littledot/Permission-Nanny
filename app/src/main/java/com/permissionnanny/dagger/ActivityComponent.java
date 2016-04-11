package com.permissionnanny.dagger;

import com.permissionnanny.ConfirmRequestActivity;
import com.permissionnanny.missioncontrol.AppControlActivity;
import dagger.Component;

/**
 *
 */
@ContextComponent.ContextScope
@Component(modules = {ContextModule.class, ActivityModule.class}, dependencies = {AppComponent.class})
public interface ActivityComponent {

    void inject(AppControlActivity victim);

    void inject(ConfirmRequestActivity victim);
}
