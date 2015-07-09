package com.permissionnanny.lib.request.wifi;

import android.net.wifi.WifiConfiguration;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public class WifiManagerRequest extends PermissionRequest {

    public WifiManagerRequest(RequestParams params) {
        super(params);
    }

    @Override
    public int getRequestType() {
        return SIMPLE_REQUEST;
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
        RequestParams p = new RequestParams();
        p.opCode = ADD_NETWORK;
        p.wifiConfiguration0 = wifiConfiguration;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest disableNetwork(int netId) {
        RequestParams p = new RequestParams();
        p.opCode = DISABLE_NETWORK;
        p.int0 = netId;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest disconnect() {
        RequestParams p = new RequestParams();
        p.opCode = DISCONNECT;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest enableNetwork(int netId, boolean disableOthers) {
        RequestParams p = new RequestParams();
        p.opCode = ENABLE_NETWORK;
        p.int0 = netId;
        p.boolean0 = disableOthers;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest getConfiguredNetworks() {
        RequestParams p = new RequestParams();
        p.opCode = GET_CONFIGURED_NETWORKS;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest getConnectionInfo() {
        RequestParams p = new RequestParams();
        p.opCode = GET_CONNECTION_INFO;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest getDhcpInfo() {
        RequestParams p = new RequestParams();
        p.opCode = GET_DHCP_INFO;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest getScanResults() {
        RequestParams p = new RequestParams();
        p.opCode = GET_SCAN_RESULTS;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest getWifiState() {
        RequestParams p = new RequestParams();
        p.opCode = GET_WIFI_STATE;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest isWifiEnabled() {
        RequestParams p = new RequestParams();
        p.opCode = IS_WIFI_ENABLED;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest pingSupplicant() {
        RequestParams p = new RequestParams();
        p.opCode = PING_SUPPLICANT;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest reassociate() {
        RequestParams p = new RequestParams();
        p.opCode = REASSOCIATE;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest reconnect() {
        RequestParams p = new RequestParams();
        p.opCode = RECONNECT;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest removeNetwork(int netId) {
        RequestParams p = new RequestParams();
        p.opCode = REMOVE_NETWORK;
        p.int0 = netId;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest saveConfiguration() {
        RequestParams p = new RequestParams();
        p.opCode = SAVE_CONFIGURATION;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest setWifiEnabled(boolean enabled) {
        RequestParams p = new RequestParams();
        p.opCode = SET_WIFI_ENABLED;
        p.boolean0 = enabled;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest startScan() {
        RequestParams p = new RequestParams();
        p.opCode = START_SCAN;
        return new WifiManagerRequest(p);
    }

    public static WifiManagerRequest updateNetwork(WifiConfiguration wifiConfiguration) {
        RequestParams p = new RequestParams();
        p.opCode = UPDATE_NETWORK;
        p.wifiConfiguration0 = wifiConfiguration;
        return new WifiManagerRequest(p);
    }
}
