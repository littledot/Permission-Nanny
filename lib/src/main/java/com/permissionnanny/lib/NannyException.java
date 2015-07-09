package com.permissionnanny.lib;

/**
 *
 */
public class NannyException extends Exception {
    public NannyException(String format, Object... args) {
        super(String.format(format, args));
    }

    public NannyException(Throwable throwable, String format, Object... args) {
        super(String.format(format, args), throwable);
    }
}
