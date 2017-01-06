package com.permissionnanny.simple

import android.Manifest
import android.content.Context
import android.content.pm.PermissionInfo
import android.location.LocationManager
import com.permissionnanny.R
import com.permissionnanny.lib.request.simple.LocationRequest
import javax.inject.Inject

/**

 */
class LocationOperation
@Inject
constructor() {
    companion object {
        val operations = arrayOf(
                SimpleOperation(LocationRequest.ADD_GPS_STATUS_LISTENER,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationAddGpsStatusListener, 3, null),
                SimpleOperation(LocationRequest.ADD_NMEA_LISTENER,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationAddNmeaListener, 5, null),
                SimpleOperation(LocationRequest.ADD_PROXIMITY_ALERT,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationAddProximityAlert, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            mgr.addProximityAlert(request.double0, request.double1, request.float0,
                                    request.long0, request.pendingIntent0)
                        }),
                SimpleOperation(LocationRequest.GET_LAST_KNOWN_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationGetLastKnownLocation, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            response.putParcelable(request.opCode, mgr.getLastKnownLocation(request.string0))
                        }),
                SimpleOperation(LocationRequest.REMOVE_PROXIMITY_ALERT,
                        "",
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_locationRemoveProximityAlert, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            mgr.removeProximityAlert(request.pendingIntent0)
                        }),
                SimpleOperation(LocationRequest.REMOVE_UPDATES,
                        "",
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_locationRemoveUpdates, 3, null),
                SimpleOperation(LocationRequest.REQUEST_LOCATION_UPDATES,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationRequestLocationUpdates, 9,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            mgr.requestLocationUpdates(request.long0, request.float0, request.criteria0,
                                    request.pendingIntent0)
                        }),
                SimpleOperation(LocationRequest.REQUEST_LOCATION_UPDATES1,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationRequestLocationUpdates, 9, null),
                SimpleOperation(LocationRequest.REQUEST_LOCATION_UPDATES2,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationRequestLocationUpdates, 9, null),
                SimpleOperation(LocationRequest.REQUEST_LOCATION_UPDATES3,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationRequestLocationUpdates, 9, null),
                SimpleOperation(LocationRequest.REQUEST_SINGLE_UPDATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationRequestLocationUpdates, 9, null),
                SimpleOperation(LocationRequest.REQUEST_SINGLE_UPDATE1,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationRequestLocationUpdates, 9, null),
                SimpleOperation(LocationRequest.REQUEST_SINGLE_UPDATE2,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationRequestLocationUpdates, 9,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            mgr.requestSingleUpdate(request.string0, request.pendingIntent0)
                        }),
                SimpleOperation(LocationRequest.REQUEST_SINGLE_UPDATE3,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_locationRequestLocationUpdates, 9,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            mgr.requestSingleUpdate(request.criteria0, request.pendingIntent0)
                        })
        )

        fun getOperation(opCode: String): SimpleOperation? {
            for (operation in operations) {
                if (operation.opCode == opCode) {
                    return operation
                }
            }
            return null
        }
    }
}
