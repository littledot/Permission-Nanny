package com.permissionnanny.simple;

import android.Manifest;
import android.content.Context;
import android.content.pm.PermissionInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.SmsManager;
import com.permissionnanny.ProxyFunction;
import com.permissionnanny.R;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.SmsRequest;

import javax.inject.Inject;

/**
 *
 */
public class SmsOperation {
    public static final SimpleOperation[] operations = new SimpleOperation[]{
            new SimpleOperation(SmsRequest.SEND_DATA_MESSAGE,
                    Manifest.permission.SEND_SMS,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_smsSendDataMessage, 4, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    SmsManager mgr = SmsManager.getDefault();
                    mgr.sendDataMessage(request.string0, request.string1, (short) request.int0, request.byteArray0,
                            request.pendingIntent0, request.pendingIntent1);
                }
            }),
            new SimpleOperation(SmsRequest.SEND_MULTIMEDIA_MESSAGE,
                    Manifest.permission.SEND_SMS,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_smsSendMultimediaMessage, 21, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    if (VERSION.SDK_INT >= 21) {
                        SmsManager mgr = SmsManager.getDefault();
                        mgr.sendMultimediaMessage(null, request.uri0, request.string0, request.bundle0,
                                request.pendingIntent0);
                    }
                }
            }),
            new SimpleOperation(SmsRequest.SEND_MULTIPART_TEXT_MESSAGE,
                    Manifest.permission.SEND_SMS,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_smsSendMultipartTextMessage, 4, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    SmsManager mgr = SmsManager.getDefault();
                    mgr.sendMultipartTextMessage(request.string0, request.string1, request.arrayListOfStrings0,
                            request.arrayListOfPendingIntents0, request.arrayListOfPendingIntents1);
                }
            }),
            new SimpleOperation(SmsRequest.SEND_TEXT_MESSAGE,
                    Manifest.permission.SEND_SMS,
                    PermissionInfo.PROTECTION_DANGEROUS,
                    R.string.dialogTitle_smsSendTextMessage, 4, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    SmsManager mgr = SmsManager.getDefault();
                    mgr.sendTextMessage(request.string0, request.string1, request.string2,
                            request.pendingIntent0, request.pendingIntent1);
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

    @Inject
    public SmsOperation() {}
}
