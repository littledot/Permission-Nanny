package com.sdchang.permissionpolice.telephony;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.telephony.TelephonyManager;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyManagerRequest;

import java.util.ArrayList;

/**
 *
 */
class TelephonyOperation {

    public static final TelephonyOperation[] operations = new TelephonyOperation[]{
            new TelephonyOperation(TelephonyManagerRequest.GET_ALL_CELL_INFO,
                    R.string.dialogTitle_telephonyGetAllCellInfo, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    if (VERSION.SDK_INT >= 17) {
                        response.putParcelableArrayList(request.opCode(), new ArrayList<>(tele.getAllCellInfo()));
                    }
                }
            }),
            new TelephonyOperation(TelephonyManagerRequest.GET_DEVICE_ID, R.string.dialogTitle_telephonyGetDeviceId,
                    new TelephonyFunction() {
                        @Override
                        public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                            response.putString(request.opCode(), tele.getDeviceId());
                        }
                    }),
            new TelephonyOperation(TelephonyManagerRequest.GET_DEVICE_SOFTWARE_VERSION,
                    R.string.dialogTitle_telephonyGetDeviceSoftwareVersion, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getDeviceSoftwareVersion());
                }
            }),
            new TelephonyOperation(TelephonyManagerRequest.GET_GROUP_ID_LEVEL_1,
                    R.string.dialogTitle_telephonyGetGroupIdLevel1, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    if (VERSION.SDK_INT >= 18) {
                        response.putString(request.opCode(), tele.getGroupIdLevel1());
                    }
                }
            }),
            new TelephonyOperation(TelephonyManagerRequest.GET_LINE_1_NUMBER,
                    R.string.dialogTitle_telephonyGetLine1Number, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getLine1Number());
                }
            }),
            new TelephonyOperation(TelephonyManagerRequest.GET_NEIGHBORING_CELL_INFO,
                    R.string.dialogTitle_telephonyGetNeighboringCellInfo, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putParcelableArrayList(request.opCode(), new ArrayList<>(tele.getNeighboringCellInfo()));
                }
            }),
            new TelephonyOperation(TelephonyManagerRequest.GET_SIM_SERIAL_NUMBER,
                    R.string.dialogTitle_telephonyGetSimSerialNumber, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getSimSerialNumber());
                }
            }),
            new TelephonyOperation(TelephonyManagerRequest.GET_SUBSCRIBER_ID,
                    R.string.dialogTitle_telephonyGetSubscriberId, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getSubscriberId());
                }
            }),
            new TelephonyOperation(TelephonyManagerRequest.GET_VOICE_MAIL_ALPHA_TAG,
                    R.string.dialogTitle_telephonyGetVoiceMailAlphaTag, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getVoiceMailAlphaTag());
                }
            }),
            new TelephonyOperation(TelephonyManagerRequest.GET_VOICE_MAIL_NUMBER,
                    R.string.dialogTitle_telephonyGetVoiceMailNumber, new TelephonyFunction() {
                @Override
                public void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response) {
                    response.putString(request.opCode(), tele.getVoiceMailNumber());
                }
            }),
    };

    public final String mOpCode;
    @StringRes public final int mDialogTitle;
    public final TelephonyFunction mFunction;

    public TelephonyOperation(String opCode,
                              int dialogTitle,
                              TelephonyFunction function) {
        mOpCode = opCode;
        mDialogTitle = dialogTitle;
        mFunction = function;
    }
}
