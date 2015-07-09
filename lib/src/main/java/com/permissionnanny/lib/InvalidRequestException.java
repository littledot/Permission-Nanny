package com.permissionnanny.lib;

/**
 *
 */
public class InvalidRequestException extends NannyException {
    public InvalidRequestException(String format, Object... args) {
        super(String.format(format, args));
    }

    public InvalidRequestException(Throwable throwable, String format, Object... args) {
        super(String.format(format, args), throwable);
    }
}
