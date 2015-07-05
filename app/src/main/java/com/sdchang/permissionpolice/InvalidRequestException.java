package com.sdchang.permissionpolice;

/**
 *
 */
public class InvalidRequestException extends Exception {
    public InvalidRequestException(String format, Object... args) {
        super(String.format(format, args));
    }

    public InvalidRequestException(Throwable throwable, String format, Object... args) {
        super(String.format(format, args), throwable);
    }
}
