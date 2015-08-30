package com.permissionnanny;

import android.content.Intent;
import com.permissionnanny.lib.Nanny;

/**
 *
 */
public class AppTestUtil {

    public static Intent new400Response(String clientAddr, String server, Throwable error) {
        return new Intent(clientAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_BAD_REQUEST)
                .putExtra(Nanny.SERVER, server)
                .putExtra(Nanny.CONNECTION, Nanny.CLOSE)
                .putExtra(Nanny.ENTITY_ERROR, error);
    }

    public static Intent new200Response(String clientAddr) {
        return new Intent(clientAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.SERVER, Nanny.PERMISSION_MANIFEST_SERVICE)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_OK)
                .putExtra(Nanny.CONNECTION, Nanny.CLOSE);
    }
}
