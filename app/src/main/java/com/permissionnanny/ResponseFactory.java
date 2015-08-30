package com.permissionnanny;

import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;

/**
 *
 */
public class ResponseFactory {
    public static NannyBundle.Builder newAllowResponse(String server) {
        NannyBundle.Builder builder = new NannyBundle.Builder();
        builder.mStatusCode = Nanny.SC_OK;
        builder.mServer = server;
        return builder;
    }

    public static NannyBundle.Builder newDenyResponse(String server) {
        NannyBundle.Builder builder = new NannyBundle.Builder();
        builder.mStatusCode = Nanny.SC_FORBIDDEN;
        builder.mServer = server;
        builder.mConnection = Nanny.CLOSE;
        return builder;
    }

    public static NannyBundle.Builder newBadRequestResponse(String server, Throwable error) {
        NannyBundle.Builder builder = new NannyBundle.Builder();
        builder.mStatusCode = Nanny.SC_BAD_REQUEST;
        builder.mServer = server;
        builder.mConnection = Nanny.CLOSE;
        builder.mError = error;
        return builder;
    }
}
