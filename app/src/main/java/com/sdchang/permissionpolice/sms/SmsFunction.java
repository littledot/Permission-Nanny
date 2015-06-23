package com.sdchang.permissionpolice.sms;

import android.os.Bundle;
import android.telephony.SmsManager;
import com.sdchang.permissionpolice.lib.request.RequestParams;

/**
 *
 */
interface SmsFunction {
    void execute(SmsManager sms, RequestParams request, Bundle response);
}
