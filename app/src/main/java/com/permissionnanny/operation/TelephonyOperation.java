package com.permissionnanny.operation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PermissionInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import com.permissionnanny.ProxyFunction;
import com.permissionnanny.R;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.TelephonyRequest;

import java.util.ArrayList;

/**
 *
 */
public class TelephonyOperation {
    public static final SimpleOperation[] operations = new SimpleOperation[]{
            new SimpleOperation(TelephonyRequest.GET_ALL_CELL_INFO,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_telephonyGetAllCellInfo, 17, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    if (VERSION.SDK_INT >= 17) {
                        TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        response.putParcelableArrayList(request.opCode, new ArrayList<>(mgr.getAllCellInfo()));
                    }
                }
            }),
            new SimpleOperation(TelephonyRequest.GET_DEVICE_ID,
                    Manifest.permission.READ_PHONE_STATE,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_telephonyGetDeviceId, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    response.putString(request.opCode, mgr.getDeviceId());
                }
            }),
            new SimpleOperation(TelephonyRequest.GET_DEVICE_SOFTWARE_VERSION,
                    Manifest.permission.READ_PHONE_STATE,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_telephonyGetDeviceSoftwareVersion, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    response.putString(request.opCode, mgr.getDeviceSoftwareVersion());
                }
            }),
            new SimpleOperation(TelephonyRequest.GET_GROUP_ID_LEVEL_1,
                    Manifest.permission.READ_PHONE_STATE,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_telephonyGetGroupIdLevel1, 18, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    if (VERSION.SDK_INT >= 18) {
                        TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        response.putString(request.opCode, mgr.getGroupIdLevel1());
                    }
                }
            }),
            new SimpleOperation(TelephonyRequest.GET_LINE_1_NUMBER,
                    Manifest.permission.READ_PHONE_STATE,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_telephonyGetLine1Number, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    response.putString(request.opCode, mgr.getLine1Number());
                }
            }),
            new SimpleOperation(TelephonyRequest.GET_NEIGHBORING_CELL_INFO,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_telephonyGetNeighboringCellInfo, 3, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    response.putParcelableArrayList(request.opCode, new ArrayList<>(mgr.getNeighboringCellInfo()));
                }
            }),
            new SimpleOperation(TelephonyRequest.GET_SIM_SERIAL_NUMBER,
                    Manifest.permission.READ_PHONE_STATE,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_telephonyGetSimSerialNumber, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    response.putString(request.opCode, mgr.getSimSerialNumber());
                }
            }),
            new SimpleOperation(TelephonyRequest.GET_SUBSCRIBER_ID,
                    Manifest.permission.READ_PHONE_STATE,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_telephonyGetSubscriberId, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    response.putString(request.opCode, mgr.getSubscriberId());
                }
            }),
            new SimpleOperation(TelephonyRequest.GET_VOICE_MAIL_ALPHA_TAG,
                    Manifest.permission.READ_PHONE_STATE,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_telephonyGetVoiceMailAlphaTag, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    response.putString(request.opCode, mgr.getVoiceMailAlphaTag());
                }
            }),
            new SimpleOperation(TelephonyRequest.GET_VOICE_MAIL_NUMBER,
                    Manifest.permission.READ_PHONE_STATE,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_telephonyGetVoiceMailNumber, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    response.putString(request.opCode, mgr.getVoiceMailNumber());
                }
            }),
    };

    public static SimpleOperation getOperation(String opCode) {
        for (SimpleOperation operation : operations) {
            if (operation.mOpCode.equals(opCode)) {
                return operation;
            }
        }
        return null;
    }
}
