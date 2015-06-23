package com.sdchang.permissionpolice.lib.request.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Criteria;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationListener;
import android.os.Handler;
import android.os.Looper;
import com.sdchang.permissionpolice.lib.request.PermissionRequest;
import com.sdchang.permissionpolice.lib.request.RequestParams;

/**
 *
 */
public class LocationRequest extends PermissionRequest {

    public static RequestParams.Builder newBuilder() {
        return RequestParams.newBuilder();
    }

    private Context mContext;

    public LocationRequest(RequestParams params) {
        super(params);
    }

    @Override
    public int getRequestType() {
        return LOCATION_REQUEST;
    }

    public static final String ADD_GPS_STATUS_LISTENER = "addGpsStatusListener";
    public static final String ADD_NMEA_LISTENER = "addNmeaListener";
    public static final String ADD_PROXIMITY_ALERT = "addProximityAlert";
    public static final String GET_LAST_KNOWN_LOCATION = "getLastKnownLocation";
    public static final String REMOVE_GPS_STATUS_LISTENER = "removeGpsStatusListener";
    public static final String REMOVE_NMEA_LISTENER = "removeNmeaListener";
    public static final String REMOVE_PROXIMITY_ALERT = "removeProximityAlert";
    public static final String REMOVE_UPDATES = "removeUpdates";
    public static final String REMOVE_UPDATES1 = "removeUpdates1";
    public static final String REQUEST_LOCATION_UPDATES = "requestLocationUpdates";
    public static final String REQUEST_LOCATION_UPDATES1 = "requestLocationUpdates1";
    public static final String REQUEST_LOCATION_UPDATES2 = "requestLocationUpdates2";
    public static final String REQUEST_LOCATION_UPDATES3 = "requestLocationUpdates3";
    public static final String REQUEST_LOCATION_UPDATES4 = "requestLocationUpdates4";
    public static final String requestSingleUpdate = "requestSingleUpdate";
    public static final String requestSingleUpdate1 = "requestSingleUpdate1";
    public static final String requestSingleUpdate2 = "requestSingleUpdate2";
    public static final String requestSingleUpdate3 = "requestSingleUpdate3";

    public static LocationRequest addGpsStatusListener(Listener listener) {
        RequestParams params = newBuilder().opCode(ADD_GPS_STATUS_LISTENER).build();
        LocationRequest request = new LocationRequest(params);
        request.addFilter(new GpsStatusEvent(listener));
        return request;
    }

    public static LocationRequest addNmeaListener(NmeaListener listener) {
        RequestParams params = newBuilder().opCode(ADD_NMEA_LISTENER).build();
        LocationRequest request = new LocationRequest(params);
        request.addFilter(new NmeaEvent(listener));
        return request;
    }

    public static LocationRequest addProximityAlert(double latitude, double longtitude, float radius, long
            expiration, PendingIntent intent) {
        RequestParams params = newBuilder().opCode(ADD_PROXIMITY_ALERT).double0(latitude).double1(longtitude).float0
                (radius)
                .long0(expiration).pendingIntent0(intent).build();
        return new LocationRequest(params);
    }

    public static LocationRequest getLastKnownLocation(String provider) {
        RequestParams params = newBuilder().opCode(GET_LAST_KNOWN_LOCATION).string0(provider).build();
        return new LocationRequest(params);
    }

//    public static LocationRequest removeGpsStatusListener(Listener listener) {
//        return newBuilder().opCode(REMOVE_GPS_STATUS_LISTENER).build();
//    }

//    public static LocationRequest removeNmeaListener(NmeaListener listener) {
//        return newBuilder().opCode(REMOVE_NMEA_LISTENER).build();
//    }

    public static LocationRequest removeProximityAlert(PendingIntent intent) {
        RequestParams params = newBuilder().opCode(REMOVE_PROXIMITY_ALERT).pendingIntent0(intent).build();
        return new LocationRequest(params);
    }

    public static LocationRequest removeUpdates(PendingIntent intent) {
        RequestParams params = newBuilder().opCode(REMOVE_UPDATES).pendingIntent0(intent).build();
        return new LocationRequest(params);
    }

//    public static LocationRequest removeUpdates(LocationListener listener) {
//        return newBuilder().opCode(REMOVE_UPDATES1).build();
//    }

    public static LocationRequest requestLocationUpdates(long minTime,
                                                         float minDistance,
                                                         Criteria criteria,
                                                         PendingIntent intent) {
        RequestParams params = newBuilder().opCode(REQUEST_LOCATION_UPDATES).long0(minTime).float0(minDistance)
                .criteria0(criteria).pendingIntent0(intent).build();
        return new LocationRequest(params);
    }

    public static LocationRequest requestLocationUpdates(long minTime,
                                                         float minDistance,
                                                         Criteria criteria,
                                                         LocationListener listener,
                                                         Looper looper) {
        Handler handler = looper != null ? new Handler(looper) : new Handler();
        RequestParams params = newBuilder().opCode(REQUEST_LOCATION_UPDATES1).long0(minTime).float0(minDistance)
                .criteria0(criteria).build();
        LocationRequest request = new LocationRequest(params);
        request.addFilter(new LocationEvent(listener, handler));
        return request;
    }

    @Override
    public LocationRequest startRequest(Context context, String reason) {
        mContext = context;
        super.startRequest(context, reason);
        return this;
    }

    public void stop() {
        mContext.unregisterReceiver(mReceiver);
    }
}

