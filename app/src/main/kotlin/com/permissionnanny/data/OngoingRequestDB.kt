package com.permissionnanny.data

import android.support.v4.util.ArrayMap
import com.permissionnanny.lib.request.RequestParams

/**
 */
class OngoingRequestDB(private val db: NannyDB) {

    fun open() {
        db.open()
    }

    fun putOngoingRequest(clientId: String, request: RequestParams) {
        db.put(clientId, request)
    }

    val ongoingRequests: ArrayMap<String, RequestParams>
        get() = db.findVal(null, RequestParams::class.java)

    fun delOngoingRequest(clientId: String) {
        db.del(clientId)
    }
}
