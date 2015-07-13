package com.permissionnanny.operation;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import com.permissionnanny.ProxyFunction;
import com.permissionnanny.R;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.LocationRequest;

/**
 *
 */
public class LocationOperation {
    public static SimpleOperation[] operations = new SimpleOperation[]{
            new SimpleOperation(LocationRequest.ADD_GPS_STATUS_LISTENER,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    R.string.dialogTitle_locationAddGpsStatusListener, 3, null),
            new SimpleOperation(LocationRequest.ADD_NMEA_LISTENER,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    R.string.dialogTitle_locationAddNmeaListener, 5, null),
            new SimpleOperation(LocationRequest.ADD_PROXIMITY_ALERT,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    R.string.dialogTitle_locationAddProximityAlert, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    mgr.addProximityAlert(request.double0, request.double1, request.float0,
                            request.long0, request.pendingIntent0);
                }
            }),
            new SimpleOperation(LocationRequest.GET_LAST_KNOWN_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    R.string.dialogTitle_locationGetLastKnownLocation, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    response.putParcelable(request.opCode, mgr.getLastKnownLocation(request.string0));
                }
            }),
            new SimpleOperation(LocationRequest.REMOVE_GPS_STATUS_LISTENER,
                    null, R.string.dialogTitle_locationRemoveGpsStatusListener, 3, null),
            new SimpleOperation(LocationRequest.REMOVE_NMEA_LISTENER,
                    null, R.string.dialogTitle_locationRemoveNmeaListener, 5, null),
            new SimpleOperation(LocationRequest.REMOVE_PROXIMITY_ALERT,
                    null, R.string.dialogTitle_locationRemoveProximityAlert, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    mgr.removeProximityAlert(request.pendingIntent0);
                }
            }),
            new SimpleOperation(LocationRequest.REMOVE_UPDATES,
                    null, R.string.dialogTitle_locationRemoveUpdates, 3, null),
            new SimpleOperation(LocationRequest.REMOVE_UPDATES1,
                    null, R.string.dialogTitle_locationRemoveUpdates1, 1, null),
            new SimpleOperation(LocationRequest.REQUEST_LOCATION_UPDATES,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    R.string.dialogTitle_locationRequestLocationUpdates, 9, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    mgr.requestLocationUpdates(request.long0, request.float0, request.criteria0,
                            request.pendingIntent0);
                }
            }),
            new SimpleOperation(LocationRequest.REQUEST_LOCATION_UPDATES1,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    R.string.dialogTitle_locationRequestLocationUpdates1, 9, null),
    };

    public static SimpleOperation getOperation(String opCode) {
        for (SimpleOperation operation : operations) {
            if (operation.mOpCode.equals(opCode)) {
                return operation;
            }
        }
        return null;
    }
}
