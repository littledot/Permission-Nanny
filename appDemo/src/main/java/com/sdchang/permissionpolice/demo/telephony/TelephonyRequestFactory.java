package com.sdchang.permissionpolice.demo.telephony;

import android.app.Dialog;
import android.content.Context;
import com.sdchang.permissionpolice.demo.DemoRequestFactory;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyManagerRequest;

/**
 *
 */
public class TelephonyRequestFactory implements DemoRequestFactory {
    private static String[] mLabels = new String[]{
            TelephonyManagerRequest.GET_ALL_CELL_INFO,
            TelephonyManagerRequest.GET_DEVICE_ID,
            TelephonyManagerRequest.GET_DEVICE_SOFTWARE_VERSION,
            TelephonyManagerRequest.GET_GROUP_ID_LEVEL_1,
            TelephonyManagerRequest.GET_LINE_1_NUMBER,
            TelephonyManagerRequest.GET_NEIGHBORING_CELL_INFO,
            TelephonyManagerRequest.GET_SIM_SERIAL_NUMBER,
            TelephonyManagerRequest.GET_SUBSCRIBER_ID,
            TelephonyManagerRequest.GET_VOICE_MAIL_ALPHA_TAG,
            TelephonyManagerRequest.GET_VOICE_MAIL_NUMBER,
    };

    @Override
    public BaseRequest getRequest(int position) {
        switch (position) {
        case 0:
            return TelephonyManagerRequest.getAllCellInfo();
        case 1:
            return TelephonyManagerRequest.getDeviceId();
        case 2:
            return TelephonyManagerRequest.getDeviceSoftwareVersion();
        case 3:
            return TelephonyManagerRequest.getGroupIdLevel1();
        case 4:
            return TelephonyManagerRequest.getLine1Number();
        case 5:
            return TelephonyManagerRequest.getNeighboringCellInfo();
        case 6:
            return TelephonyManagerRequest.getSimSerialNumber();
        case 7:
            return TelephonyManagerRequest.getSubscriberId();
        case 8:
            return TelephonyManagerRequest.getVoiceMailAlphaTag();
        case 9:
            return TelephonyManagerRequest.getVoiceMailNumber();
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
