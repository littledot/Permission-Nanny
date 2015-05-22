package com.sdchang.permissionpolice.wifi;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;

/**
 *
 */
interface WifiFunction {
    void execute(WifiManager wifi, WifiManagerRequest request, Bundle response);
}
