package com.permissionnanny.simple

import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.permissionnanny.ProxyService
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.simple.LocationRequest

/**
 */
open class RequestSingleUpdateListener(
        service: ProxyService,
        clientAddr: String)
    : RequestLocationUpdatesListener(service, clientAddr) {

    override fun onLocationChanged(location: Location) {
        super.onLocationChanged(location)
        stop()
    }

    /**
     * [LocationRequest.requestSingleUpdate]
     */
    class Api(service: ProxyService, clientAddr: String) : RequestSingleUpdateListener(service, clientAddr) {

        override fun register(context: Context, request: RequestParams) {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.requestSingleUpdate(request.string0, this, null)
        }
    }

    /**
     * [LocationRequest.requestSingleUpdate]
     */
    class Api1(service: ProxyService, clientAddr: String) : RequestSingleUpdateListener(service, clientAddr) {

        override fun register(context: Context, request: RequestParams) {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.requestSingleUpdate(request.criteria0, this, null)
        }
    }
}
