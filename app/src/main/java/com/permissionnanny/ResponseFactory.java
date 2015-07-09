package com.permissionnanny;

import com.permissionnanny.lib.Nanny;

/**
 *
 */
public class ResponseFactory {
    public static ResponseBundle newAllowResponse() {
        return new ResponseBundle()
                .server(Nanny.AUTHORIZATION_SERVICE)
                .status(Nanny.SC_OK);
    }

    public static ResponseBundle newDenyResponse() {
        return new ResponseBundle()
                .server(Nanny.AUTHORIZATION_SERVICE)
                .status(Nanny.SC_FORBIDDEN)
                .connection(Nanny.CLOSE);
    }

    public static ResponseBundle newBadRequestResponse(Throwable error) {
        return new ResponseBundle()
                .server(Nanny.AUTHORIZATION_SERVICE)
                .status(Nanny.SC_BAD_REQUEST)
                .connection(Nanny.CLOSE)
                .error(error);
    }
}
