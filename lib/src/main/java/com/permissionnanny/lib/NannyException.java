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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NannyException that = (NannyException) o;
        return getMessage().equals(that.getMessage());
    }

    @Override
    public int hashCode() {
        return getMessage().hashCode();
    }
}
