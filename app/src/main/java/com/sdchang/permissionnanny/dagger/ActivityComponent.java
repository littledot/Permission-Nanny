package com.sdchang.permissionnanny.dagger;

import com.sdchang.permissionnanny.ConfirmRequestActivity;
import com.sdchang.permissionnanny.ProxyService;
import com.sdchang.permissionnanny.dagger.ActivityComponent.ActivityScope;
import com.sdchang.permissionnanny.missioncontrol.AppListActivity;
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
