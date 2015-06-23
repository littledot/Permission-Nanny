package com.sdchang.permissionpolice.dagger;

import com.sdchang.permissionpolice.MySnappy;
import dagger.Component;

import javax.inject.Singleton;

/**
 *
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    MySnappy db();
}
