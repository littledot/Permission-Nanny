package com.sdchang.permissionpolice.missioncontrol;

import android.os.Build;
import android.support.v4.util.SimpleArrayMap;

import static android.Manifest.permission.*;
import static com.sdchang.permissionpolice.R.string.*;

/**
 *
 */
public class PermRes {
    private static SimpleArrayMap<String, PermRes> resMap = new SimpleArrayMap<>();

    static {
        resMap.put(ACCESS_COARSE_LOCATION, new PermRes(permlab_accessCoarseLocation, permdesc_accessCoarseLocation));
        resMap.put(ACCESS_FINE_LOCATION, new PermRes(permlab_accessFineLocation, permdesc_accessFineLocation));
        resMap.put(ACCESS_WIFI_STATE, new PermRes(permlab_accessWifiState, permdesc_accessWifiState));
        resMap.put(CHANGE_WIFI_STATE, new PermRes(permlab_changeWifiState, permdesc_changeWifiState));
        resMap.put(READ_CALENDAR, new PermRes(permlab_readCalendar, permdesc_readCalendar));
        resMap.put(READ_CONTACTS, new PermRes(permlab_readContacts, permdesc_readContacts));
        if (Build.VERSION.SDK_INT >= 16) {
            resMap.put(READ_EXTERNAL_STORAGE, new PermRes(permlab_sdcardRead, permdesc_sdcardRead));
        }
        resMap.put(READ_PHONE_STATE, new PermRes(permlab_readPhoneState, permdesc_readPhoneState));
        resMap.put(READ_SMS, new PermRes(permlab_readSms, permdesc_readSms));
        resMap.put(SEND_SMS, new PermRes(permlab_sendSms, permdesc_sendSms));
        resMap.put(WRITE_CALENDAR, new PermRes(permlab_writeCalendar, permdesc_writeCalendar));
    }

    public static int getLabel(String permission) {
        PermRes res = resMap.get(permission);
        return res == null ? 0 : res.label;
    }

    public static int getDescription(String permission) {
        PermRes res = resMap.get(permission);
        return res == null ? 0 : res.description;
    }

    public final int label;
    public final int description;

    public PermRes(int label, int description) {
        this.label = label;
        this.description = description;
    }
}
