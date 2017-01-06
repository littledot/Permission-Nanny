package com.permissionnanny.dagger

/**

 */
object MockComponentFactory {

    val appComponent: AppComponent
        get() = DaggerAppComponent.builder()
                .appModule(MockAppModule())
                .build()

    val contextComponent: MockContextComponent
        get() = DaggerMockContextComponent.builder()
                .appComponent(appComponent)
                .contextModule(MockContextModule())
                .build()

    val activityComponent: MockActivityComponent
        get() = DaggerMockActivityComponent.builder()
                .appComponent(appComponent)
                .contextModule(MockContextModule())
                .activityModule(MockActivityModule())
                .build()
}
