package com.sdchang.permissionpolice.telephony;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyManagerRequest;

interface TelephonyFunction {
    void execute(TelephonyManager tele, TelephonyManagerRequest request, Bundle response);
}
