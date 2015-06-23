package com.sdchang.permissionpolice.telephony;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import com.sdchang.permissionpolice.lib.request.RequestParams;

interface TelephonyFunction {
    void execute(TelephonyManager tele, RequestParams request, Bundle response);
}
