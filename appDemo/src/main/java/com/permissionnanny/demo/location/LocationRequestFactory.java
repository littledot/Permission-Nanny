package com.permissionnanny.demo.location;

import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.SparseArray;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.demo.C;
import com.permissionnanny.demo.SimpleRequestFactory;
import com.permissionnanny.demo.EzMap;
import com.permissionnanny.demo.extra.CriteriaExtra;
import com.permissionnanny.demo.extra.Extra;
import com.permissionnanny.demo.extra.ExtrasDialogBuilder;
import com.permissionnanny.demo.extra.FloatExtra;
import com.permissionnanny.demo.extra.LongExtra;
import com.permissionnanny.demo.extra.StringExtra;
import com.permissionnanny.lib.request.simple.LocationRequest;
import com.permissionnanny.lib.request.simple.SimpleRequest;
import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 *
 */
public class LocationRequestFactory implements SimpleRequestFactory {

    EventBus bus = EventBus.getDefault();

    private String[] mLabels = new String[]{
            LocationRequest.ADD_GPS_STATUS_LISTENER,
            LocationRequest.ADD_NMEA_LISTENER,
            LocationRequest.GET_LAST_KNOWN_LOCATION,
            LocationRequest.REQUEST_LOCATION_UPDATES1,
            LocationRequest.REQUEST_LOCATION_UPDATES2,
            LocationRequest.REQUEST_SINGLE_UPDATE,
            LocationRequest.REQUEST_SINGLE_UPDATE1,
    };
    private SparseArray<Extra[]> mExtras = new SparseArray<Extra[]>() {{
        put(3, new Extra[]{new LongExtra(), new FloatExtra(), new CriteriaExtra()});
        put(4, new Extra[]{new StringExtra("gps"), new LongExtra(), new FloatExtra()});
        put(5, new Extra[]{new StringExtra("gps")});
        put(6, new Extra[]{new CriteriaExtra()});
    }};
    private SparseArray<String[]> mExtrasLabels = new SparseArray<String[]>() {{
        put(3, new String[]{"minTime", "minDistance", "criteria"});
        put(4, new String[]{"provider", "minTime", "minDistance"});
        put(5, new String[]{"provider"});
        put(6, new String[]{"criteria"});
    }};
    private ExtrasDialogBuilder mBuilder = new ExtrasDialogBuilder();

    @Override
    public SimpleRequest getRequest(final int position) {
        Extra[] extras = mExtras.get(position);
        switch (position) {
        case 0:
            return LocationRequest.addGpsStatusListener(new GpsStatus.Listener() {
                @Override
                public void onGpsStatusChanged(int event) {
                    bus.post(new EzMap()
                            .put(C.POS, position)
                            .put(C.DATA, "onGpsStatusChanged: " + event));
                }
            });
        case 1:
            return LocationRequest.addNmeaListener(new GpsStatus.NmeaListener() {
                @Override
                public void onNmeaReceived(long timestamp, String nmea) {
                    bus.post(new EzMap()
                            .put(C.POS, position)
                            .put(C.DATA, "onGpsStatusChanged: ts=" + timestamp + " nmea=" + nmea));
                }
            });
        case 2:
            return LocationRequest.getLastKnownLocation("gps");
        case 3:
            return LocationRequest.requestLocationUpdates(((long) extras[0].getValue()), ((float) extras[1].getValue()),
                    ((Criteria) extras[2].getValue()), new DemoLocationListener(position), null);
        case 4:
            return LocationRequest.requestLocationUpdates((String) extras[0].getValue(), (long) extras[1].getValue(),
                    (float) extras[2].getValue(), new DemoLocationListener(position), null);
        case 5:
            return LocationRequest.requestSingleUpdate((String) extras[0].getValue(), new DemoLocationListener(position), null);
        case 6:
            return LocationRequest.requestSingleUpdate((Criteria) extras[0].getValue(), new DemoLocationListener(position), null);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mLabels.length;
    }

    @Override
    public String getLabel(int position) {
        return mLabels[position];
    }

    @Override
    public boolean hasExtras(int position) {
        return mExtras.get(position) != null;
    }

    @Override
    public Dialog buildDialog(Context context, int position) {
        return mBuilder.build(context, mExtras.get(position), mExtrasLabels.get(position));
    }

    private class DemoLocationListener implements LocationListener {

        private int mPosition;

        public DemoLocationListener(int position) {
            mPosition = position;
        }

        @Override
        public void onLocationChanged(Location location) {
            Timber.wtf("" + location);
            bus.post(new EzMap()
                    .put(C.POS, mPosition)
                    .put(C.DATA, "onLocationChanged: " + location));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            bus.post(new EzMap()
                    .put(C.POS, mPosition)
                    .put(C.DATA, "onStatusChanged: provider=" + provider + " status=" + status +
                            " extras=" + BundleUtil.toString(extras)));
        }

        @Override
        public void onProviderEnabled(String provider) {
            bus.post(new EzMap()
                    .put(C.POS, mPosition)
                    .put(C.DATA, "onProviderEnabled: " + provider));
        }

        @Override
        public void onProviderDisabled(String provider) {
            bus.post(new EzMap()
                    .put(C.POS, mPosition)
                    .put(C.DATA, "onProviderDisabled: " + provider));
        }
    }
}
