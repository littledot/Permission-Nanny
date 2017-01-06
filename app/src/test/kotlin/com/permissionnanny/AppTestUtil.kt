package com.permissionnanny

import android.content.Intent
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.NannyException

/**

 */
object AppTestUtil {

    fun new400Response(clientAddr: String, server: String, error: Throwable): Intent {
        return Intent(clientAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_BAD_REQUEST)
                .putExtra(Nanny.SERVER, server)
                .putExtra(Nanny.CONNECTION, Nanny.CLOSE)
                .putExtra(Nanny.ENTITY_ERROR, error)
    }

    fun new200Response(clientAddr: String): Intent {
        return Intent(clientAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.SERVER, Nanny.PERMISSION_MANIFEST_SERVICE)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_OK)
                .putExtra(Nanny.CONNECTION, Nanny.CLOSE)
    }

    fun new200AuthServiceResponse(clientAddr: String): Intent {
        return Intent(clientAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.SERVER, Nanny.AUTHORIZATION_SERVICE)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_OK)
                .putExtra(Nanny.CONNECTION, Nanny.CLOSE)
    }

    fun new400AuthServiceResponse(clientAddr: String, error: String): Intent {
        return Intent(clientAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_BAD_REQUEST)
                .putExtra(Nanny.SERVER, Nanny.AUTHORIZATION_SERVICE)
                .putExtra(Nanny.CONNECTION, Nanny.CLOSE)
                .putExtra(Nanny.ENTITY_ERROR, NannyException(error))
    }

    fun new400AuthServiceResponse(clientAddr: String, error: Throwable): Intent {
        return Intent(clientAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_BAD_REQUEST)
                .putExtra(Nanny.SERVER, Nanny.AUTHORIZATION_SERVICE)
                .putExtra(Nanny.CONNECTION, Nanny.CLOSE)
                .putExtra(Nanny.ENTITY_ERROR, error)
    }
}
