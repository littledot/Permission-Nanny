package com.sdchang.permissionpolice.demo.telephony;

import android.app.Dialog;
import android.content.Context;
import com.sdchang.permissionpolice.demo.DemoRequestFactory;
import com.sdchang.permissionpolice.lib.request.PermissionRequest;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyRequest;

/**
 *
 */
public class TelephonyRequestFactory implements DemoRequestFactory {
    private static String[] mLabels = new String[]{
            TelephonyRequest.GET_ALL_CELL_INFO,
            TelephonyRequest.GET_DEVICE_ID,
            TelephonyRequest.GET_DEVICE_SOFTWARE_VERSION,
            TelephonyRequest.GET_GROUP_ID_LEVEL_1,
            TelephonyRequest.GET_LINE_1_NUMBER,
            TelephonyRequest.GET_NEIGHBORING_CELL_INFO,
            TelephonyRequest.GET_SIM_SERIAL_NUMBER,
            TelephonyRequest.GET_SUBSCRIBER_ID,
            TelephonyRequest.GET_VOICE_MAIL_ALPHA_TAG,
            TelephonyRequest.GET_VOICE_MAIL_NUMBER,
    };

    @Override
    public PermissionRequest getRequest(int position) {
        switch (position) {
        case 0:
            return TelephonyRequest.getAllCellInfo();
        case 1:
            return TelephonyRequest.getDeviceId();
        case 2:
            return TelephonyRequest.getDeviceSoftwareVersion();
        case 3:
            return TelephonyRequest.getGroupIdLevel1();
        case 4:
            return TelephonyRequest.getLine1Number();
        case 5:
            return TelephonyRequest.getNeighboringCellInfo();
        case 6:
            return TelephonyRequest.getSimSerialNumber();
        case 7:
            return TelephonyRequest.getSubscriberId();
        case 8:
            return TelephonyRequest.getVoiceMailAlphaTag();
        case 9:
            return TelephonyRequest.getVoiceMailNumber();
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
        return false;
    }

    @Override
    public Dialog buildDialog(Context context, int position) {
        return null;
    }
}
