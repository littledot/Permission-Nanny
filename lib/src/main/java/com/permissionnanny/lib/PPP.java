package com.permissionnanny.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes that the target is part of the Permission Police Protocol (PPP). <b><i>Changes must guarantee backwards
 * compatibility</i></b>.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface PPP {
}
