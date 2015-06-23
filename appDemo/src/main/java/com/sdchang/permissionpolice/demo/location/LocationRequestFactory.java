package com.sdchang.permissionpolice.demo.location;

import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.SparseArray;
import com.sdchang.permissionpolice.common.BundleUtil;
import com.sdchang.permissionpolice.demo.C;
import com.sdchang.permissionpolice.demo.DemoRequestFactory;
import com.sdchang.permissionpolice.demo.EzMap;
import com.sdchang.permissionpolice.demo.extra.CriteriaExtra;
import com.sdchang.permissionpolice.demo.extra.Extra;
import com.sdchang.permissionpolice.demo.extra.ExtrasDialogBuilder;
import com.sdchang.permissionpolice.demo.extra.FloatExtra;
import com.sdchang.permissionpolice.demo.extra.LongExtra;
import com.sdchang.permissionpolice.lib.request.PermissionRequest;
import com.sdchang.permissionpolice.lib.request.location.LocationRequest;
import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 *
 */
public class LocationRequestFactory implements DemoRequestFactory {

    EventBus bus = EventBus.getDefault();

    private String[] mLabels = new String[]{
            LocationRequest.ADD_GPS_STATUS_LISTENER,
            LocationRequest.ADD_NMEA_LISTENER,
            LocationRequest.GET_LAST_KNOWN_LOCATION,
            LocationRequest.REQUEST_LOCATION_UPDATES1,
    };
    private SparseArray<Extra[]> mExtras = new SparseArray<Extra[]>() {{
//        put(1, new Extra[]{new LongExtra(), new FloatExtra(), new CriteriaExtra()});
        put(3, new Extra[]{new LongExtra(), new FloatExtra(), new CriteriaExtra()});
    }};
    private SparseArray<String[]> mExtrasLabels = new SparseArray<String[]>() {{
        put(3, new String[]{"minTime", "minDistance", "criteria"});
    }};
    private ExtrasDialogBuilder mBuilder = new ExtrasDialogBuilder();

    @Override
    public PermissionRequest getRequest(final int position) {
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
                    ((Criteria) extras[2].getValue()), new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Timber.wtf("" + location);
                            bus.post(new EzMap()
                                    .put(C.POS, position)
                                    .put(C.DATA, "onLocationChanged: " + location));
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            bus.post(new EzMap()
                                    .put(C.POS, position)
                                    .put(C.DATA, "onStatusChanged: provider=" + provider + " status=" + status +
                                            " extras=" + BundleUtil.toString(extras)));
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            bus.post(new EzMap()
                                    .put(C.POS, position)
                                    .put(C.DATA, "onProviderEnabled: " + provider));
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            bus.post(new EzMap()
                                    .put(C.POS, position)
                                    .put(C.DATA, "onProviderDisabled: " + provider));
                        }
                    }, null);
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
}
