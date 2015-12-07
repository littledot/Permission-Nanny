package com.permissionnanny.data;

import android.os.Build;
import android.support.v4.util.ArrayMap;

import java.util.Map;

import static android.Manifest.permission.*;
import static com.permissionnanny.R.string.*;

/**
 *
 */
public class PermissionDetail {
    private final static Map<String, PermissionDetail> resMap = new ArrayMap<>();

    static {
        resMap.put(ACCESS_COARSE_LOCATION, new PermissionDetail(permlab_accessCoarseLocation, permdesc_accessCoarseLocation));
        resMap.put(ACCESS_FINE_LOCATION, new PermissionDetail(permlab_accessFineLocation, permdesc_accessFineLocation));
        resMap.put(ACCESS_WIFI_STATE, new PermissionDetail(permlab_accessWifiState, permdesc_accessWifiState));
        resMap.put(CHANGE_WIFI_STATE, new PermissionDetail(permlab_changeWifiState, permdesc_changeWifiState));
        resMap.put(READ_CALENDAR, new PermissionDetail(permlab_readCalendar, permdesc_readCalendar));
        resMap.put(READ_CONTACTS, new PermissionDetail(permlab_readContacts, permdesc_readContacts));
        if (Build.VERSION.SDK_INT >= 16) {
            resMap.put(READ_EXTERNAL_STORAGE, new PermissionDetail(permlab_sdcardRead, permdesc_sdcardRead));
        }
        resMap.put(READ_PHONE_STATE, new PermissionDetail(permlab_readPhoneState, permdesc_readPhoneState));
        resMap.put(READ_SMS, new PermissionDetail(permlab_readSms, permdesc_readSms));
        resMap.put(SEND_SMS, new PermissionDetail(permlab_sendSms, permdesc_sendSms));
        resMap.put(WRITE_CALENDAR, new PermissionDetail(permlab_writeCalendar, permdesc_writeCalendar));
    }

    public static int getLabel(String permission) {
        PermissionDetail res = resMap.get(permission);
        return res == null ? 0 : res.label;
    }

    public static int getDescription(String permission) {
        PermissionDetail res = resMap.get(permission);
        return res == null ? 0 : res.description;
    }

    public final int label;
    public final int description;

    public PermissionDetail(int label, int description) {
        this.label = label;
        this.description = description;
    }
}
