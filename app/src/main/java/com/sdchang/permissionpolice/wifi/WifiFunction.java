package com.sdchang.permissionpolice.wifi;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import com.sdchang.permissionpolice.lib.request.RequestParams;

/**
 *
 */
interface WifiFunction {
    void execute(WifiManager wifi, RequestParams request, Bundle response);
}
