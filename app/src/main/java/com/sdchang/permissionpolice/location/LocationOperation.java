package com.sdchang.permissionpolice.location;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import com.sdchang.permissionpolice.ProxyFunction;
import com.sdchang.permissionpolice.ProxyOperation;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.request.RequestParams;
import com.sdchang.permissionpolice.lib.request.location.LocationRequest;

/**
 *
 */
public class LocationOperation {
    private static ProxyOperation[] operations = new ProxyOperation[]{
            new ProxyOperation(LocationRequest.ADD_GPS_STATUS_LISTENER,
                    R.string.dialogTitle_locationAddGpsStatusListener, 3, null),
            new ProxyOperation(LocationRequest.ADD_NMEA_LISTENER,
                    R.string.dialogTitle_locationAddNmeaListener, 5, null),
            new ProxyOperation(LocationRequest.ADD_PROXIMITY_ALERT,
                    R.string.dialogTitle_locationAddProximityAlert, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    mgr.addProximityAlert(request.double0(), request.double1(), request.float0(),
                            request.long0(), request.pendingIntent0());
                }
            }),
            new ProxyOperation(LocationRequest.GET_LAST_KNOWN_LOCATION,
                    R.string.dialogTitle_locationGetLastKnownLocation, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    response.putParcelable(request.opCode(), mgr.getLastKnownLocation(request.string0()));
                }
            }),
            new ProxyOperation(LocationRequest.REMOVE_GPS_STATUS_LISTENER,
                    R.string.dialogTitle_locationRemoveGpsStatusListener, 3, null),
            new ProxyOperation(LocationRequest.REMOVE_NMEA_LISTENER,
                    R.string.dialogTitle_locationRemoveNmeaListener, 5, null),
            new ProxyOperation(LocationRequest.REMOVE_PROXIMITY_ALERT,
                    R.string.dialogTitle_locationRemoveProximityAlert, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    mgr.removeProximityAlert(request.pendingIntent0());
                }
            }),
            new ProxyOperation(LocationRequest.REMOVE_UPDATES,
                    R.string.dialogTitle_locationRemoveUpdates, 3, null),
            new ProxyOperation(LocationRequest.REMOVE_UPDATES1,
                    R.string.dialogTitle_locationRemoveUpdates1, 1, null),
            new ProxyOperation(LocationRequest.REQUEST_LOCATION_UPDATES,
                    R.string.dialogTitle_locationRequestLocationUpdates, 9, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    mgr.requestLocationUpdates(request.long0(), request.float0(), request.criteria0(),
                            request.pendingIntent0());
                }
            }),
            new ProxyOperation(LocationRequest.REQUEST_LOCATION_UPDATES1,
                    R.string.dialogTitle_locationRequestLocationUpdates1, 9, null),
    };

    public static ProxyOperation getOperation(String opCode) {
        for (ProxyOperation operation : operations) {
            if (operation.mOpCode.equals(opCode)) {
                return operation;
            }
        }
        return null;
    }
}
