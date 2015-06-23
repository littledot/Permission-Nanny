package com.sdchang.permissionpolice.telephony;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.telephony.TelephonyManager;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.request.RequestParams;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyRequest;

import java.util.ArrayList;

/**
 *
 */
public class TelephonyOperation {

    public static final TelephonyOperation[] operations = new TelephonyOperation[]{
            new TelephonyOperation(TelephonyRequest.GET_ALL_CELL_INFO,
                    R.string.dialogTitle_telephonyGetAllCellInfo, 17, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, RequestParams request, Bundle response) {
                    if (VERSION.SDK_INT >= 17) {
                        response.putParcelableArrayList(request.opCode(), new ArrayList<>(tele.getAllCellInfo()));
                    }
                }
            }),
            new TelephonyOperation(TelephonyRequest.GET_DEVICE_ID,
                    R.string.dialogTitle_telephonyGetDeviceId, 1, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, RequestParams request, Bundle response) {
                    response.putString(request.opCode(), tele.getDeviceId());
                }
            }),
            new TelephonyOperation(TelephonyRequest.GET_DEVICE_SOFTWARE_VERSION,
                    R.string.dialogTitle_telephonyGetDeviceSoftwareVersion, 1, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, RequestParams request, Bundle response) {
                    response.putString(request.opCode(), tele.getDeviceSoftwareVersion());
                }
            }),
            new TelephonyOperation(TelephonyRequest.GET_GROUP_ID_LEVEL_1,
                    R.string.dialogTitle_telephonyGetGroupIdLevel1, 18, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, RequestParams request, Bundle response) {
                    if (VERSION.SDK_INT >= 18) {
                        response.putString(request.opCode(), tele.getGroupIdLevel1());
                    }
                }
            }),
            new TelephonyOperation(TelephonyRequest.GET_LINE_1_NUMBER,
                    R.string.dialogTitle_telephonyGetLine1Number, 1, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, RequestParams request, Bundle response) {
                    response.putString(request.opCode(), tele.getLine1Number());
                }
            }),
            new TelephonyOperation(TelephonyRequest.GET_NEIGHBORING_CELL_INFO,
                    R.string.dialogTitle_telephonyGetNeighboringCellInfo, 3, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, RequestParams request, Bundle response) {
                    response.putParcelableArrayList(request.opCode(), new ArrayList<>(tele.getNeighboringCellInfo()));
                }
            }),
            new TelephonyOperation(TelephonyRequest.GET_SIM_SERIAL_NUMBER,
                    R.string.dialogTitle_telephonyGetSimSerialNumber, 1, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, RequestParams request, Bundle response) {
                    response.putString(request.opCode(), tele.getSimSerialNumber());
                }
            }),
            new TelephonyOperation(TelephonyRequest.GET_SUBSCRIBER_ID,
                    R.string.dialogTitle_telephonyGetSubscriberId, 1, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, RequestParams request, Bundle response) {
                    response.putString(request.opCode(), tele.getSubscriberId());
                }
            }),
            new TelephonyOperation(TelephonyRequest.GET_VOICE_MAIL_ALPHA_TAG,
                    R.string.dialogTitle_telephonyGetVoiceMailAlphaTag, 1, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, RequestParams request, Bundle response) {
                    response.putString(request.opCode(), tele.getVoiceMailAlphaTag());
                }
            }),
            new TelephonyOperation(TelephonyRequest.GET_VOICE_MAIL_NUMBER,
                    R.string.dialogTitle_telephonyGetVoiceMailNumber, 1, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, RequestParams request, Bundle response) {
                    response.putString(request.opCode(), tele.getVoiceMailNumber());
                }
            }),
    };

    public final String mOpCode;
    @StringRes public final int mDialogTitle;
    public final int mMinSdk;
    public final TelephonyFunction mFunction;

    public TelephonyOperation(String opCode,
                              int dialogTitle,
                              int minSdk,
                              TelephonyFunction function) {
        mOpCode = opCode;
        mDialogTitle = dialogTitle;
        mMinSdk = minSdk;
        mFunction = function;
    }
}
