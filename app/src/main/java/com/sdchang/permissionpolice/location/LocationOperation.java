package com.sdchang.permissionpolice.location;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.StringRes;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.request.location.LocationRequest;

/**
 *
 */
class LocationOperation {
    static LocationOperation[] operations = new LocationOperation[]{
            new LocationOperation(LocationRequest.ADD_GPS_STATUS_LISTENER,
                    R.string.dialogTitle_locationAddGpsStatusListener, null),
            new LocationOperation(LocationRequest.ADD_NMEA_LISTENER,
                    R.string.dialogTitle_locationAddNmeaListener, null),
            new LocationOperation(LocationRequest.ADD_PROXIMITY_ALERT,
                    R.string.dialogTitle_locationAddProximityAlert, new LocationFunction() {
                @Override
                public void execute(LocationManager lm, LocationRequest request, Bundle response) {
                    lm.addProximityAlert(request.double0(), request.double1(), request.float0(),
                            request.long0(), request.pendingIntent0());
                }
            }),
            new LocationOperation(LocationRequest.GET_LAST_KNOWN_LOCATION,
                    R.string.dialogTitle_locationGetLastKnownLocation, new LocationFunction() {
                @Override
                public void execute(LocationManager lm, LocationRequest request, Bundle response) {
                    response.putParcelable(request.opCode(), lm.getLastKnownLocation(request.string0()));
                }
            }),
            new LocationOperation(LocationRequest.REMOVE_GPS_STATUS_LISTENER,
                    R.string.dialogTitle_locationRemoveGpsStatusListener, null),
            new LocationOperation(LocationRequest.REMOVE_NMEA_LISTENER,
                    R.string.dialogTitle_locationRemoveNmeaListener, null),
            new LocationOperation(LocationRequest.REMOVE_PROXIMITY_ALERT,
                    R.string.dialogTitle_locationRemoveProximityAlert, new LocationFunction() {
                @Override
                public void execute(LocationManager lm, LocationRequest request, Bundle response) {
                    lm.removeProximityAlert(request.pendingIntent0());
                }
            }),
            new LocationOperation(LocationRequest.REMOVE_UPDATES,
                    R.string.dialogTitle_locationRemoveUpdates, null),
            new LocationOperation(LocationRequest.REMOVE_UPDATES1,
                    R.string.dialogTitle_locationRemoveUpdates1, null),
            new LocationOperation(LocationRequest.REQUEST_LOCATION_UPDATES,
                    R.string.dialogTitle_locationRequestLocationUpdates, new LocationFunction() {
                @Override
                public void execute(LocationManager lm, LocationRequest request, Bundle response) {
                    lm.requestLocationUpdates(request.long0(), request.float0(), request.criteria0(),
                            request.pendingIntent0());
                }
            }),
            new LocationOperation(LocationRequest.REQUEST_LOCATION_UPDATES1,
                    R.string.dialogTitle_locationRequestLocationUpdates1, null),
    };

    final String mOpCode;
    @StringRes final int mDialogTitle;
    final LocationFunction mFunction;

    public LocationOperation(String opCode,
                             int dialogTitle,
                             LocationFunction function) {
        mOpCode = opCode;
        mDialogTitle = dialogTitle;
        mFunction = function;
    }
}
