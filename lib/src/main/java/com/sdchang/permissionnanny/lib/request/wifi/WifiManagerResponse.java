package com.sdchang.permissionnanny.lib.request.wifi;

import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.sdchang.permissionnanny.lib.request.BaseResponse;

import java.util.List;

/**
 *
 */
public class WifiManagerResponse extends BaseResponse {

    public WifiManagerResponse(Bundle response) {
        super(response);
    }

    @Nullable
    public List<WifiConfiguration> wifiConfigurations() {
        return mResponse.getParcelableArrayList(WifiManagerRequest.GET_CONFIGURED_NETWORKS);
    }

    @Nullable
    public WifiInfo wifiInfo() {
        return mResponse.getParcelable(WifiManagerRequest.GET_CONNECTION_INFO);
    }

    @Nullable
    public DhcpInfo dhcpInfo() {
        return mResponse.getParcelable(WifiManagerRequest.GET_DHCP_INFO);
    }

    @Nullable
    public List<ScanResult> scanResults() {
        return mResponse.getParcelableArrayList(WifiManagerRequest.GET_SCAN_RESULTS);
    }

    public int wifiState() {
        return mResponse.getInt(WifiManagerRequest.GET_WIFI_STATE);
    }

    public boolean isWifiEnabled() {
        return mResponse.getBoolean(WifiManagerRequest.IS_WIFI_ENABLED);
    }

    public boolean pingSupplicant() {
        return mResponse.getBoolean(WifiManagerRequest.PING_SUPPLICANT);
    }

    public int networkId() {
        return mResponse.getInt(WifiManagerRequest.ADD_NETWORK);
    }

    public boolean networkDisabled() {
        return mResponse.getBoolean(WifiManagerRequest.DISABLE_NETWORK);
    }

    public boolean disconnected() {
        return mResponse.getBoolean(WifiManagerRequest.DISCONNECT);
    }

    public boolean networkEnabled() {
        return mResponse.getBoolean(WifiManagerRequest.ENABLE_NETWORK);
    }

    public boolean reassociated() {
        return mResponse.getBoolean(WifiManagerRequest.REASSOCIATE);
    }

    public boolean reconnected() {
        return mResponse.getBoolean(WifiManagerRequest.RECONNECT);
    }

    public boolean removeNetwork() {
        return mResponse.getBoolean(WifiManagerRequest.REMOVE_NETWORK);
    }

    public boolean saveConfiguration() {
        return mResponse.getBoolean(WifiManagerRequest.SAVE_CONFIGURATION);
    }

    public boolean setWifiEnabled() {
        return mResponse.getBoolean(WifiManagerRequest.SET_WIFI_ENABLED);
    }

    public boolean startScan() {
        return mResponse.getBoolean(WifiManagerRequest.START_SCAN);
    }
}
