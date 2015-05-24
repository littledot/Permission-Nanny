package com.sdchang.permissionpolice.sms;

import android.os.Bundle;
import android.telephony.SmsManager;
import com.sdchang.permissionpolice.lib.request.sms.SmsRequest;

/**
 *
 */
interface SmsFunction {
    void execute(SmsManager sms, SmsRequest request, Bundle response);
}
