package com.permissionnanny.simple

import android.content.Context
import android.location.GpsStatus.NmeaListener
import android.location.LocationManager
import android.os.Bundle
import com.permissionnanny.ProxyListener
import com.permissionnanny.ProxyService
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.simple.NmeaEvent

/**

 */
class ProxyNmeaListener<L>(
        service: ProxyService,
        clientAddr: String)
    : ProxyListener<L>(service, clientAddr, Nanny.NMEA_SERVICE), NmeaListener {

    override fun register(context: Context, request: RequestParams) {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        lm.addNmeaListener(this)
    }

    override fun unregister(context: Context) {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        lm.removeNmeaListener(this)
    }

    override fun onNmeaReceived(timestamp: Long, nmea: String) {
        val entity = Bundle()
        entity.putLong(NmeaEvent.TIMESTAMP, timestamp)
        entity.putString(NmeaEvent.NMEA, nmea)

        sendBroadcast(okResponse(entity))
    }
}
