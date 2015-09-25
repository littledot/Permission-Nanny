package com.permissionnanny.simple;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import com.permissionnanny.ProxyService;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.LocationRequest;

/**
 */
public class RequestSingleUpdateListener extends RequestLocationUpdatesListener {
    public RequestSingleUpdateListener(ProxyService service, String clientAddr) {
        super(service, clientAddr);
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        stop();
    }

    /**
     * {@link LocationRequest#requestSingleUpdate(String, LocationListener, Looper)}
     */
    public static class Api extends RequestSingleUpdateListener {
        public Api(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            lm.requestSingleUpdate(request.string0, this, null);
        }
    }

    /**
     * {@link LocationRequest#requestSingleUpdate(Criteria, LocationListener, Looper)}
     */
    public static class Api1 extends RequestSingleUpdateListener {
        public Api1(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            lm.requestSingleUpdate(request.criteria0, this, null);
        }
    }
}
