package com.sdchang.permissionnanny;

import com.sdchang.permissionnanny.lib.Nanny;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;

/**
 *
 */
public class ResponseFactory {
    public static ResponseBundle newAllowResponse() {
        return new ResponseBundle()
                .server(Nanny.AUTHORIZATION_SERVICE)
                .status(HttpStatus.SC_OK);
    }

    public static ResponseBundle newDenyResponse() {
        return new ResponseBundle()
                .server(Nanny.AUTHORIZATION_SERVICE)
                .status(HttpStatus.SC_UNAUTHORIZED)
                .connection(HTTP.CONN_CLOSE);
    }

    public static ResponseBundle newBadRequestResponse(Throwable error) {
        return new ResponseBundle()
                .server(Nanny.AUTHORIZATION_SERVICE)
                .status(HttpStatus.SC_BAD_REQUEST)
                .connection(HTTP.CONN_CLOSE)
                .contentType(Nanny.APPLICATION_SERIALIZABLE)
                .error(error);
    }
}
