package com.permissionnanny.dagger;

import com.permissionnanny.ConfirmRequestActivity;
import com.permissionnanny.ProxyService;
import com.permissionnanny.dagger.ActivityComponent.ActivityScope;
import com.permissionnanny.missioncontrol.AppListActivity;
import dagger.Component;

import javax.inject.Scope;

/**
 *
 */
@ActivityScope
@Component(modules = {}, dependencies = AppComponent.class)
public interface ActivityComponent {

    @Scope
    @interface ActivityScope {}

    void inject(ConfirmRequestActivity victim);

    void inject(AppListActivity victim);

    void inject(ProxyService victim);
}
