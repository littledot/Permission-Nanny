package com.sdchang.permissionpolice.demo.location;

import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.SparseArray;
import com.sdchang.permissionpolice.common.BundleUtil;
import com.sdchang.permissionpolice.demo.DemoRequestFactory;
import com.sdchang.permissionpolice.demo.extra.CriteriaExtra;
import com.sdchang.permissionpolice.demo.extra.Extra;
import com.sdchang.permissionpolice.demo.extra.ExtrasDialogBuilder;
import com.sdchang.permissionpolice.demo.extra.FloatExtra;
import com.sdchang.permissionpolice.demo.extra.LongExtra;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.lib.request.location.LocationRequest;
import timber.log.Timber;

/**
 *
 */
public class LocationRequestFactory implements DemoRequestFactory {
    private String[] mLabels = new String[]{
            LocationRequest.GET_LAST_KNOWN_LOCATION,
            LocationRequest.REQUEST_LOCATION_UPDATES,
            LocationRequest.REQUEST_LOCATION_UPDATES1,
    };
    private SparseArray<Extra[]> mExtras = new SparseArray<Extra[]>() {{
//        put(1, new Extra[]{new LongExtra(), new FloatExtra(), new CriteriaExtra()});
        put(2, new Extra[]{new LongExtra(), new FloatExtra(), new CriteriaExtra()});
    }};
    private SparseArray<String[]> mExtrasLabels = new SparseArray<String[]>() {{
        put(2, new String[]{"minTime", "minDistance", "criteria"});
    }};
    private ExtrasDialogBuilder mBuilder = new ExtrasDialogBuilder();

    @Override
    public BaseRequest getRequest(int position) {
        Extra[] extras = mExtras.get(position);
        switch (position) {
        case 2:
            return LocationRequest.requestLocationUpdates(((long) extras[0].getValue()), ((float) extras[1].getValue()),
                    ((Criteria) extras[2].getValue()),
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Timber.wtf("" + location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            String result = "provider=" + provider + " status=" + status + " extras=" +
                                    BundleUtil.toString(extras);
                            Timber.wtf(result);
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            Timber.wtf("" + provider);
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            Timber.wtf("" + provider);
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
