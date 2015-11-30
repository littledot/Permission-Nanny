package com.permissionnanny.lib.request.simple;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Criteria;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationListener;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.RequestParams;

/**
 * Factory that creates {@link android.location.LocationManager} requests.
 */
public class LocationRequest extends SimpleRequest {

    @PPP public static final String ADD_GPS_STATUS_LISTENER = "addGpsStatusListener";
    @PPP public static final String ADD_NMEA_LISTENER = "addNmeaListener";
    @PPP public static final String ADD_PROXIMITY_ALERT = "addProximityAlert";
    @PPP public static final String GET_LAST_KNOWN_LOCATION = "getLastKnownLocation";
    @PPP public static final String REMOVE_GPS_STATUS_LISTENER = "removeGpsStatusListener";
    @PPP public static final String REMOVE_NMEA_LISTENER = "removeNmeaListener";
    @PPP public static final String REMOVE_PROXIMITY_ALERT = "removeProximityAlert";
    @PPP public static final String REMOVE_UPDATES = "removeUpdates";
    @PPP public static final String REMOVE_UPDATES1 = "removeUpdates1";
    @PPP public static final String REQUEST_LOCATION_UPDATES = "requestLocationUpdates";
    @PPP public static final String REQUEST_LOCATION_UPDATES1 = "requestLocationUpdates1";
    @PPP public static final String REQUEST_LOCATION_UPDATES2 = "requestLocationUpdates2";
    @PPP public static final String REQUEST_LOCATION_UPDATES3 = "requestLocationUpdates3";
    @PPP public static final String REQUEST_LOCATION_UPDATES4 = "requestLocationUpdates4";
    @PPP public static final String REQUEST_SINGLE_UPDATE = "requestSingleUpdate";
    @PPP public static final String REQUEST_SINGLE_UPDATE1 = "requestSingleUpdate1";
    @PPP public static final String REQUEST_SINGLE_UPDATE2 = "requestSingleUpdate2";
    @PPP public static final String REQUEST_SINGLE_UPDATE3 = "requestSingleUpdate3";

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
        request.addFilter(new LocationEvent(listener, newHandler(looper)));
        return request;
    }

    public static LocationRequest requestLocationUpdates(String provider,
                                                         long minTime,
                                                         float minDistance,
                                                         LocationListener listener) {
        return requestLocationUpdates(provider, minTime, minDistance, listener, null);
    }

    public static LocationRequest requestLocationUpdates(String provider,
                                                         long minTime,
                                                         float minDistance,
                                                         LocationListener listener,
                                                         @Nullable Looper looper) {
        RequestParams p = new RequestParams();
        p.opCode = REQUEST_LOCATION_UPDATES2;
        p.string0 = provider;
        p.long0 = minTime;
        p.float0 = minDistance;
        LocationRequest request = new LocationRequest(p);
        request.addFilter(new LocationEvent(listener, newHandler(looper)));
        return request;
    }

    public static LocationRequest requestLocationUpdates(String provider,
                                                         long minTime,
                                                         float minDistance,
                                                         PendingIntent intent) {
        RequestParams p = new RequestParams();
        p.opCode = REQUEST_LOCATION_UPDATES3;
        p.string0 = provider;
        p.long0 = minTime;
        p.float0 = minDistance;
        p.pendingIntent0 = intent;
        return new LocationRequest(p);
    }

    public static LocationRequest requestSingleUpdate(String provider, LocationListener listener, Looper looper) {
        RequestParams p = new RequestParams();
        p.opCode = REQUEST_SINGLE_UPDATE;
        p.string0 = provider;
        LocationRequest request = new LocationRequest(p);
        request.addFilter(new LocationEvent(listener, newHandler(looper)));
        return request;
    }

    public static LocationRequest requestSingleUpdate(Criteria criteria, LocationListener listener, Looper looper) {
        RequestParams p = new RequestParams();
        p.opCode = REQUEST_SINGLE_UPDATE1;
        p.criteria0 = criteria;
        LocationRequest request = new LocationRequest(p);
        request.addFilter(new LocationEvent(listener, newHandler(looper)));
        return request;
    }

    public static LocationRequest requestSingleUpdate(String provider, PendingIntent intent) {
        RequestParams p = new RequestParams();
        p.opCode = REQUEST_SINGLE_UPDATE2;
        p.string0 = provider;
        p.pendingIntent0 = intent;
        return new LocationRequest(p);
    }

    public static LocationRequest requestSingleUpdate(Criteria criteria, PendingIntent intent) {
        RequestParams p = new RequestParams();
        p.opCode = REQUEST_SINGLE_UPDATE3;
        p.criteria0 = criteria;
        p.pendingIntent0 = intent;
        return new LocationRequest(p);
    }

    private Context mContext;

    public LocationRequest(RequestParams params) {
        super(params);
    }

    @Override
    public void startRequest(@NonNull Context context, @Nullable String rationale) {
        mContext = context;
        super.startRequest(context, rationale);
    }

    public void stop() {
        stop(mContext);
    }
}

