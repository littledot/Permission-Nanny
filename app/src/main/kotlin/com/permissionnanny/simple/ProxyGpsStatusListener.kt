package com.permissionnanny.simple

import android.content.Context
import android.location.GpsStatus.Listener
import android.location.LocationManager
import android.os.Bundle
import com.permissionnanny.ProxyListener
import com.permissionnanny.ProxyService
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.simple.GpsStatusEvent

/**

 */
class ProxyGpsStatusListener<L>(
        service: ProxyService,
        clientAddr: String)
    : ProxyListener<L>(service, clientAddr, Nanny.GPS_STATUS_SERVICE), Listener {

    override fun register(context: Context, request: RequestParams) {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        lm.addGpsStatusListener(this)
    }

    override fun unregister(context: Context) {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        lm.removeGpsStatusListener(this)
    }

    override fun onGpsStatusChanged(event: Int) {
        val entity = Bundle()
        entity.putInt(GpsStatusEvent.EVENT, event)

        sendBroadcast(okResponse(entity))
    }
}
