package com.sdchang.permissionpolice.sms;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.SmsManager;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.request.sms.SmsRequest;

/**
 *
 */
class SmsValues {
    static final String[] operations = new String[]{
            SmsRequest.SEND_DATA_MESSAGE,
            SmsRequest.SEND_MULTIMEDIA_MESSAGE,
            SmsRequest.SEND_MULTIPART_TEXT_MESSAGE,
            SmsRequest.SEND_TEXT_MESSAGE
    };

    static final int[] dialogTitles = new int[]{
            R.string.dialogTitle_smsSendDataMessage,
            R.string.dialogTitle_smsSendMultimediaMessage,
            R.string.dialogTitle_smsSendMultipartTextMessage,
            R.string.dialogTitle_smsSendTextMessage
    };

    static final SmsFunction[] functions = new SmsFunction[]{
            new SmsFunction() {
                @Override
                public void execute(SmsManager sms, SmsRequest request, Bundle response) {
                    sms.sendDataMessage(request.string0(), request.string1(), request.short0(), request.byteArray0(),
                            request.pendingIntent0(), request.pendingIntent1());
                }
            },
            new SmsFunction() {
                @Override
                public void execute(SmsManager sms, SmsRequest request, Bundle response) {
                    if (VERSION.SDK_INT >= 21) {
                        sms.sendMultimediaMessage(null, request.uri0(), request.string0(), request.bundle0(),
                                request.pendingIntent0());
                    }
                }
            },
            new SmsFunction() {
                @Override
                public void execute(SmsManager sms, SmsRequest request, Bundle response) {
                    sms.sendMultipartTextMessage(request.string0(), request.string1(), request.arrayListOfStrings0(),
                            request.arrayListOfPendingIntents0(), request.arrayListOfPendingIntents1());
                }
            },
            new SmsFunction() {
                @Override
                public void execute(SmsManager sms, SmsRequest request, Bundle response) {
                    sms.sendTextMessage(request.string0(), request.string1(), request.string2(),
                            request.pendingIntent0(), request.pendingIntent1());
                }
            },
    };
}
