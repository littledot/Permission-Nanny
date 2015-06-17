package com.sdchang.permissionpolice;

import android.os.Bundle;
import com.sdchang.permissionpolice.lib.Police;
import org.apache.http.protocol.HTTP;

/**
 * TODO #32: Add typedef annotations to method parameters.
 */
public class ResponseBundle {
    private Bundle mResponse;

    public ResponseBundle() {
        mResponse = new Bundle();
        mResponse.putString(Police.HTTP_VERSION, Police.HTTP_1_1);
    }

    public ResponseBundle(Bundle response) {
        mResponse = response;
    }

    public ResponseBundle server(String server) {
        mResponse.putString(Police.SERVER, server);
        return this;
    }

    public ResponseBundle connection(String connection) {
        mResponse.putString(HTTP.CONN_DIRECTIVE, connection);
        return this;
    }

    public ResponseBundle status(int status) {
        mResponse.putInt(Police.STATUS_CODE, status);
        return this;
    }

    public ResponseBundle contentType(String type) {
        mResponse.putString(HTTP.CONTENT_TYPE, type);
        return this;
    }

    public ResponseBundle body(Bundle body) {
        mResponse.putBundle(Police.ENTITY_BODY, body);
        return this;
    }

    public ResponseBundle error(Throwable error) {
        mResponse.putSerializable(Police.ENTITY_ERROR, error);
        return this;
    }

    public Bundle build() {
        return mResponse;
    }
}
