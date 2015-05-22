package com.sdchang.permissionpolice.sms;

import android.os.Bundle;
import android.telephony.SmsManager;
import com.sdchang.permissionpolice.lib.request.sms.SmsRequest;

/**
 *
 */
public interface SmsFunction {
    void execute(SmsManager tele, SmsRequest request, Bundle response);
}
