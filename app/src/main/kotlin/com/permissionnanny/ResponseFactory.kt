package com.permissionnanny

import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.NannyBundle

object ResponseFactory {
    fun newAllowResponse(server: String): NannyBundle.Builder {
        val builder = NannyBundle.Builder()
        builder.mStatusCode = Nanny.SC_OK
        builder.mServer = server
        return builder
    }

    fun newDenyResponse(server: String): NannyBundle.Builder {
        val builder = NannyBundle.Builder()
        builder.mStatusCode = Nanny.SC_FORBIDDEN
        builder.mServer = server
        builder.mConnection = Nanny.CLOSE
        return builder
    }

    fun newBadRequestResponse(error: Throwable): NannyBundle.Builder {
        return newBadRequestResponse(Nanny.AUTHORIZATION_SERVICE, error)
    }

    fun newBadRequestResponse(server: String, error: Throwable): NannyBundle.Builder {
        val builder = NannyBundle.Builder()
        builder.mStatusCode = Nanny.SC_BAD_REQUEST
        builder.mServer = server
        builder.mConnection = Nanny.CLOSE
        builder.mError = error
        return builder
    }
}
