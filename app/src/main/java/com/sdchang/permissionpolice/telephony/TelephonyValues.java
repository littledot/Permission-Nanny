package com.sdchang.permissionpolice.telephony;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyManagerRequest;

import java.util.ArrayList;

/**
 *
 */
class TelephonyValues {
    static final String[] operations = new String[]{
            TelephonyManagerRequest.GET_ALL_CELL_INFO,
            TelephonyManagerRequest.GET_DEVICE_ID,
            TelephonyManagerRequest.GET_DEVICE_SOFTWARE_VERSION,
            TelephonyManagerRequest.GET_GROUP_ID_LEVEL_1,
            TelephonyManagerRequest.GET_LINE_1_NUMBER,
            TelephonyManagerRequest.GET_NEIGHBORING_CELL_INFO,
            TelephonyManagerRequest.GET_SIM_SERIAL_NUMBER,
            TelephonyManagerRequest.GET_SUBSCRIBER_ID,
            TelephonyManagerRequest.GET_VOICE_MAIL_ALPHA_TAG,
            TelephonyManagerRequest.GET_VOICE_MAIL_NUMBER};

    static final int[] dialogTitles = new int[]{
            R.string.dialogTitle_telephonyGetAllCellInfo,
            R.string.dialogTitle_telephonyGetDeviceId,
            R.string.dialogTitle_telephonyGetDeviceSoftwareVersion,
            R.string.dialogTitle_telephonyGetGroupIdLevel1,
            R.string.dialogTitle_telephonyGetLine1Number,
            R.string.dialogTitle_telephonyGetNeighboringCellInfo,
            R.string.dialogTitle_telephonyGetSimSerialNumber,
            R.string.dialogTitle_telephonyGetSubscriberId,
            R.string.dialogTitle_telephonyGetVoiceMailAlphaTag,
            R.string.dialogTitle_telephonyGetVoiceMailNumber
    };

    static final TelephonyFunction[] functions = new TelephonyFunction[]{
            new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    if (VERSION.SDK_INT >= 17) {
                        response.putParcelableArrayList(request.opCode(), new ArrayList<>(tele.getAllCellInfo()));
                    }
                }
            },
            new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getDeviceId());
                }
            },
            new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getDeviceSoftwareVersion());
                }
            },
            new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    if (VERSION.SDK_INT >= 18) {
                        response.putString(request.opCode(), tele.getGroupIdLevel1());
                    }
                }
            },
            new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getLine1Number());
                }
            },
            new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putParcelableArrayList(request.opCode(), new ArrayList<>(tele.getNeighboringCellInfo()));
                }
            },
            new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getSimSerialNumber());
                }
            },
            new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getSubscriberId());
                }
            },
            new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getVoiceMailAlphaTag());
                }
            },
            new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getVoiceMailNumber());
                }
            }
    };
}
