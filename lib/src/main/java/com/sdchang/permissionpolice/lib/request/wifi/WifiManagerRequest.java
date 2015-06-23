package com.sdchang.permissionpolice.lib.request.wifi;

import android.net.wifi.WifiConfiguration;
import com.sdchang.permissionpolice.lib.request.PermissionRequest;
import com.sdchang.permissionpolice.lib.request.RequestParams;

/**
 *
 */
public class WifiManagerRequest extends PermissionRequest {

    public WifiManagerRequest(RequestParams params) {
        super(params);
    }

    static RequestParams.Builder newBuilder() {
        return RequestParams.newBuilder();
    }

    @Override
    public int getRequestType() {
        return WIFI_REQUEST;
    }

    public static final String ADD_NETWORK = "addNetwork";
    public static final String DISABLE_NETWORK = "disableNetwork";
    public static final String DISCONNECT = "disconnect";
    public static final String ENABLE_NETWORK = "enableNetwork";
    public static final String GET_CONFIGURED_NETWORKS = "getConfiguredNetworks";
    public static final String GET_CONNECTION_INFO = "getConnectionInfo";
    public static final String GET_DHCP_INFO = "getDhcpInfo";
    public static final String GET_SCAN_RESULTS = "getScanResults";
    public static final String GET_WIFI_STATE = "getWifiState";
    public static final String IS_WIFI_ENABLED = "isWifiEnabled";
    public static final String PING_SUPPLICANT = "pingSupplicant";
    public static final String REASSOCIATE = "reassociate";
    public static final String RECONNECT = "reconnect";
    public static final String REMOVE_NETWORK = "removeNetwork";
    public static final String SAVE_CONFIGURATION = "saveConfiguration";
    public static final String SET_WIFI_ENABLED = "setWifiEnabled";
    public static final String START_SCAN = "startScan";
    public static final String UPDATE_NETWORK = "updateNetwork";

    public static WifiManagerRequest addNetwork(WifiConfiguration wifiConfiguration) {
        RequestParams params = newBuilder().opCode(ADD_NETWORK).wifiConfiguration0(wifiConfiguration).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest disableNetwork(int netId) {
        RequestParams params = newBuilder().opCode(DISABLE_NETWORK).int0(netId).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest disconnect() {
        RequestParams params = newBuilder().opCode(DISCONNECT).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest enableNetwork(int netId, boolean disableOthers) {
        RequestParams params = newBuilder().opCode(ENABLE_NETWORK).int0(netId).boolean0(disableOthers).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest getConfiguredNetworks() {
        RequestParams params = newBuilder().opCode(GET_CONFIGURED_NETWORKS).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest getConnectionInfo() {
        RequestParams params = newBuilder().opCode(GET_CONNECTION_INFO).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest getDhcpInfo() {
        RequestParams params = newBuilder().opCode(GET_DHCP_INFO).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest getScanResults() {
        RequestParams params = newBuilder().opCode(GET_SCAN_RESULTS).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest getWifiState() {
        RequestParams params = newBuilder().opCode(GET_WIFI_STATE).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest isWifiEnabled() {
        RequestParams params = newBuilder().opCode(IS_WIFI_ENABLED).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest pingSupplicant() {
        RequestParams params = newBuilder().opCode(PING_SUPPLICANT).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest reassociate() {
        RequestParams params = newBuilder().opCode(REASSOCIATE).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest reconnect() {
        RequestParams params = newBuilder().opCode(RECONNECT).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest removeNetwork(int netId) {
        RequestParams params = newBuilder().opCode(REMOVE_NETWORK).int0(netId).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest saveConfiguration() {
        RequestParams params = newBuilder().opCode(SAVE_CONFIGURATION).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest setWifiEnabled(boolean enabled) {
        RequestParams params = newBuilder().opCode(SET_WIFI_ENABLED).boolean0(enabled).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest startScan() {
        RequestParams params = newBuilder().opCode(START_SCAN).build();
        return new WifiManagerRequest(params);
    }

    public static WifiManagerRequest updateNetwork(WifiConfiguration wifiConfiguration) {
        RequestParams params = newBuilder().opCode(UPDATE_NETWORK).wifiConfiguration0(wifiConfiguration).build();
        return new WifiManagerRequest(params);
    }
}
