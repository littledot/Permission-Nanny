package com.sdchang.permissionpolice.lib.request.wifi;

import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 *
 */
public class WifiManagerResponse {

    public static final String NETWORK_ID = "netId";
    public static final String SUCCESS = "success";
    public static final String WIFI_CONFIGURATIONS = "wifiConfigurations";
    public static final String WIFI_INFO = "wifiInfo";
    public static final String DHCP_INFO = "dhcpInfo";
    public static final String SCAN_RESULTS = "scanResults";
    public static final String WIFI_STATE = "wifiState";
    public static final String WIFI_ENABLED = "wifiEnabled";

    private Bundle mResponse;

    public WifiManagerResponse(@NonNull Bundle response) {
        mResponse = response;
    }

    public int networkId() {
        return mResponse.getInt(NETWORK_ID);
    }

    public boolean success() {
        return mResponse.getBoolean(SUCCESS);
    }

    @Nullable
    public List<WifiConfiguration> wifiConfigurations() {
        return mResponse.getParcelableArrayList(WIFI_CONFIGURATIONS);
    }

    @Nullable
    public WifiInfo wifiInfo() {
        return mResponse.getParcelable(WIFI_INFO);
    }

    @Nullable
    public DhcpInfo dhcpInfo() {
        return mResponse.getParcelable(DHCP_INFO);
    }

    @Nullable
    public List<ScanResult> scanResults() {
        return mResponse.getParcelableArrayList(SCAN_RESULTS);
    }

    public int wifiState() {
        return mResponse.getInt(WIFI_STATE);
    }

    public boolean wifiEnabled() {
        return mResponse.getBoolean(WIFI_ENABLED);
    }
}
