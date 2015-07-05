package com.sdchang.permissionnanny.lib.request.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Criteria;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationListener;
import android.os.Handler;
import android.os.Looper;
import com.sdchang.permissionnanny.lib.request.PermissionRequest;
import com.sdchang.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public class LocationRequest extends PermissionRequest {

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
        RequestParams p = new RequestParams();
        p.opCode = ADD_GPS_STATUS_LISTENER;
        LocationRequest request = new LocationRequest(p);
        request.addFilter(new GpsStatusEvent(listener));
        return request;
    }

    public static LocationRequest addNmeaListener(NmeaListener listener) {
        RequestParams p = new RequestParams();
        p.opCode = ADD_NMEA_LISTENER;
        LocationRequest request = new LocationRequest(p);
        request.addFilter(new NmeaEvent(listener));
        return request;
    }

    public static LocationRequest addProximityAlert(double latitude, double longtitude, float radius, long
            expiration, PendingIntent intent) {
        RequestParams p = new RequestParams();
        p.opCode = ADD_PROXIMITY_ALERT;
        p.double0 = latitude;
        p.double1 = longtitude;
        p.float0 = radius;
        p.long0 = expiration;
        p.pendingIntent0 = intent;
        return new LocationRequest(p);
    }

    public static LocationRequest getLastKnownLocation(String provider) {
        RequestParams p = new RequestParams();
        p.opCode = GET_LAST_KNOWN_LOCATION;
        p.string0 = provider;
        return new LocationRequest(p);
    }

//    public static LocationRequest removeGpsStatusListener(Listener listener) {
//        return newBuilder().opCode(REMOVE_GPS_STATUS_LISTENER).build();
//    }

//    public static LocationRequest removeNmeaListener(NmeaListener listener) {
//        return newBuilder().opCode(REMOVE_NMEA_LISTENER).build();
//    }

    public static LocationRequest removeProximityAlert(PendingIntent intent) {
        RequestParams p = new RequestParams();
        p.opCode = REMOVE_PROXIMITY_ALERT;
        p.pendingIntent0 = intent;
        return new LocationRequest(p);
    }

    public static LocationRequest removeUpdates(PendingIntent intent) {
        RequestParams p = new RequestParams();
        p.opCode = REMOVE_UPDATES;
        p.pendingIntent0 = intent;
        return new LocationRequest(p);
    }

//    public static LocationRequest removeUpdates(LocationListener listener) {
//        return newBuilder().opCode(REMOVE_UPDATES1).build();
//    }

    public static LocationRequest requestLocationUpdates(long minTime,
                                                         float minDistance,
                                                         Criteria criteria,
                                                         PendingIntent intent) {
        RequestParams p = new RequestParams();
        p.opCode = REQUEST_LOCATION_UPDATES;
        p.long0 = minTime;
        p.float0 = minDistance;
        p.criteria0 = criteria;
        p.pendingIntent0 = intent;
        return new LocationRequest(p);
    }

    public static LocationRequest requestLocationUpdates(long minTime,
                                                         float minDistance,
                                                         Criteria criteria,
                                                         LocationListener listener,
                                                         Looper looper) {
        RequestParams p = new RequestParams();
        p.opCode = REQUEST_LOCATION_UPDATES1;
        p.long0 = minTime;
        p.float0 = minDistance;
        p.criteria0 = criteria;
        LocationRequest request = new LocationRequest(p);
        request.addFilter(new LocationEvent(listener, looper != null ? new Handler(looper) : new Handler()));
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

