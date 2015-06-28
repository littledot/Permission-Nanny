package com.sdchang.permissionpolice.dagger;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
@Qualifier
@Retention(RetentionPolicy.SOURCE)
public @interface Type {
    String value() default "";
}
