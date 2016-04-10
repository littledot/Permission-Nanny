package com.permissionnanny.test;

import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyException;
import org.assertj.android.api.content.IntentAssert;

import static com.permissionnanny.test.Assertions.assertThat;

public class NannyIntentAssert extends IntentAssert {
    public NannyIntentAssert(Intent actual) {
        super(actual);
    }

    @Override
    public NannyIntentAssert hasComponent(String appPkg, Class<?> cls) {
        super.hasComponent(appPkg, cls);
        return this;
    }

    public NannyIntentAssert hasExtras(Bundle expected) {
        isNotNull();
        assertThat(actual.getExtras()).hasExtras(expected);
        return this;
    }

    public NannyIntentAssert hasExtras(Intent expected) {
        isNotNull();
        assertThat(actual.getExtras()).hasExtras(expected);
        return this;
    }

    public NannyIntentAssert hasStatusCode(int statusCode) {
        isNotNull();
        hasExtra(Nanny.STATUS_CODE, statusCode);
        return this;
    }

    public NannyIntentAssert hasClientAddress(String clientAddr) {
        isNotNull();
        hasAction(clientAddr);
        return this;
    }

    public NannyIntentAssert hasServer(String server) {
        isNotNull();
        hasExtra(Nanny.SERVER, server);
        return this;
    }

    public NannyIntentAssert hasError(Throwable error) {
        isNotNull();
        hasExtra(Nanny.ENTITY_ERROR, error);
        return this;
    }

    public NannyIntentAssert hasError(String error) {
        isNotNull();
        hasExtra(Nanny.ENTITY_ERROR, new NannyException(error));
        return this;
    }

    public NannyIntentAssert has400Response(String clientAddr, String server, Throwable error) {
        hasClientAddress(clientAddr);
        hasStatusCode(Nanny.SC_BAD_REQUEST);
        hasServer(server);
        hasError(error);

        hasAction(clientAddr);
        hasExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1);
        hasExtra(Nanny.STATUS_CODE, Nanny.SC_BAD_REQUEST);
        hasExtra(Nanny.SERVER, server);
        hasExtra(Nanny.CONNECTION, Nanny.CLOSE);
        hasExtra(Nanny.ENTITY_ERROR, error);
        return this;
    }

    public NannyIntentAssert has400Response(String clientAddr, String server, String error) {
        return has400Response(clientAddr, server, new NannyException(error));
    }

    public NannyIntentAssert has200Response(String clientAddr) {
        hasAction(clientAddr);
        hasExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1);
        hasExtra(Nanny.SERVER, Nanny.PERMISSION_MANIFEST_SERVICE);
        hasExtra(Nanny.STATUS_CODE, Nanny.SC_OK);
        hasExtra(Nanny.CONNECTION, Nanny.CLOSE);
        return this;
    }

    public NannyIntentAssert has200AuthServiceResponse(String clientAddr) {
        hasAction(clientAddr);
        hasExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1);
        hasExtra(Nanny.SERVER, Nanny.AUTHORIZATION_SERVICE);
        hasExtra(Nanny.STATUS_CODE, Nanny.SC_OK);
        hasExtra(Nanny.CONNECTION, Nanny.CLOSE);
        return this;
    }

    public NannyIntentAssert has400AuthServiceResponse(String clientAddr, String error) {
        hasAction(clientAddr);
        hasExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1);
        hasExtra(Nanny.STATUS_CODE, Nanny.SC_BAD_REQUEST);
        hasExtra(Nanny.SERVER, Nanny.AUTHORIZATION_SERVICE);
        hasExtra(Nanny.CONNECTION, Nanny.CLOSE);
        hasExtra(Nanny.ENTITY_ERROR, new NannyException(error));
        return this;
    }

    public NannyIntentAssert has400AuthServiceResponse(String clientAddr, Throwable error) {
        hasAction(clientAddr);
        hasExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1);
        hasExtra(Nanny.STATUS_CODE, Nanny.SC_BAD_REQUEST);
        hasExtra(Nanny.SERVER, Nanny.AUTHORIZATION_SERVICE);
        hasExtra(Nanny.CONNECTION, Nanny.CLOSE);
        hasExtra(Nanny.ENTITY_ERROR, error);
        return this;
    }
}
