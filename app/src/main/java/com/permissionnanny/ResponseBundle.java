package com.permissionnanny;

import android.os.Bundle;
import com.permissionnanny.lib.Nanny;

/**
 * TODO #32: Add typedef annotations to method parameters.
 */
public class ResponseBundle {
    private Bundle mResponse;

    public ResponseBundle() {
        mResponse = new Bundle();
        mResponse.putString(Nanny.PROTOCOL_VERSION, Nanny.PPP_1_0);
    }

    public ResponseBundle(Bundle response) {
        mResponse = response;
    }

    public ResponseBundle server(String server) {
        mResponse.putString(Nanny.SERVER, server);
        return this;
    }

    public ResponseBundle connection(String connection) {
        mResponse.putString(Nanny.CONNECTION, connection);
        return this;
    }

    public ResponseBundle status(int status) {
        mResponse.putInt(Nanny.STATUS_CODE, status);
        return this;
    }

    public ResponseBundle contentType(Class type) {
        mResponse.putString(Nanny.CONTENT_TYPE, type.getCanonicalName());
        return this;
    }

    public ResponseBundle contentEncoding(String encoding) {
        mResponse.putString(Nanny.CONTENT_ENCODING, encoding);
        return this;
    }

    public ResponseBundle body(Bundle body) {
        mResponse.putBundle(Nanny.ENTITY_BODY, body);
        return this;
    }

    public ResponseBundle error(Throwable error) {
        mResponse.putSerializable(Nanny.ENTITY_ERROR, error);
        return this;
    }

    public Bundle build() {
        return mResponse;
    }
}
