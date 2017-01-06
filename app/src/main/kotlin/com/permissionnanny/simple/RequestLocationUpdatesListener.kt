package com.permissionnanny.simple

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.permissionnanny.ProxyListener
import com.permissionnanny.ProxyService
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.simple.LocationEvent
import com.permissionnanny.lib.request.simple.LocationRequest

open class RequestLocationUpdatesListener(
        service: ProxyService,
        clientAddr: String)
    : ProxyListener<LocationListener>(service, clientAddr, Nanny.LOCATION_SERVICE), LocationListener {

    override fun register(context: Context, request: RequestParams) {
        throw UnsupportedOperationException("Must override.")
    }

    override fun unregister(context: Context) {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        lm.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        val entity = Bundle()
        entity.putString(Nanny.TYPE, LocationEvent.ON_LOCATION_CHANGED)
        entity.putParcelable(LocationEvent.LOCATION, location)

        sendBroadcast(okResponse(entity))
    }

    override fun onProviderDisabled(provider: String) {
        val entity = Bundle()
        entity.putString(Nanny.TYPE, LocationEvent.ON_PROVIDER_DISABLED)
        entity.putString(LocationEvent.PROVIDER, provider)

        sendBroadcast(okResponse(entity))
    }

    override fun onProviderEnabled(provider: String) {
        val entity = Bundle()
        entity.putString(Nanny.TYPE, LocationEvent.ON_PROVIDER_ENABLED)
        entity.putString(LocationEvent.PROVIDER, provider)

        sendBroadcast(okResponse(entity))
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        val entity = Bundle()
        entity.putString(Nanny.TYPE, LocationEvent.ON_STATUS_CHANGED)
        entity.putString(LocationEvent.PROVIDER, provider)
        entity.putInt(LocationEvent.STATUS, status)
        entity.putBundle(LocationEvent.EXTRAS, extras)

        sendBroadcast(okResponse(entity))
    }

    /**
     * [LocationRequest.requestLocationUpdates]
     */
    class Api1(service: ProxyService, clientAddr: String) : RequestLocationUpdatesListener(service, clientAddr) {

        override fun register(context: Context, request: RequestParams) {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.requestLocationUpdates(request.long0, request.float0, request.criteria0, this, null)
        }
    }

    /**
     * [LocationRequest.requestLocationUpdates] and [ ][LocationRequest.requestLocationUpdates]
     */
    class Api2(service: ProxyService, clientAddr: String) : RequestLocationUpdatesListener(service, clientAddr) {

        override fun register(context: Context, request: RequestParams) {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.requestLocationUpdates(request.string0, request.long0, request.float0, this, null)
        }
    }
}
