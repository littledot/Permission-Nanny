package com.permissionnanny.dagger

import javax.inject.Qualifier

/**

 */
@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class Type(val value: String = "")
