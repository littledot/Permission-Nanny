package com.permissionnanny.lib.request.simple;

import android.net.wifi.WifiConfiguration;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.RequestParams;

/**
 * Factory that creates {@link android.net.wifi.WifiManager} requests.
 */
public class WifiRequest extends SimpleRequest {

    @PPP public static final String ADD_NETWORK = "addNetwork";
    @PPP public static final String DISABLE_NETWORK = "disableNetwork";
    @PPP public static final String DISCONNECT = "disconnect";
    @PPP public static final String ENABLE_NETWORK = "enableNetwork";
    @PPP public static final String GET_CONFIGURED_NETWORKS = "getConfiguredNetworks";
    @PPP public static final String GET_CONNECTION_INFO = "getConnectionInfo";
    @PPP public static final String GET_DHCP_INFO = "getDhcpInfo";
    @PPP public static final String GET_SCAN_RESULTS = "getScanResults";
    @PPP public static final String GET_WIFI_STATE = "getWifiState";
    @PPP public static final String IS_WIFI_ENABLED = "isWifiEnabled";
    @PPP public static final String PING_SUPPLICANT = "pingSupplicant";
    @PPP public static final String REASSOCIATE = "reassociate";
    @PPP public static final String RECONNECT = "reconnect";
    @PPP public static final String REMOVE_NETWORK = "removeNetwork";
    @PPP public static final String SAVE_CONFIGURATION = "saveConfiguration";
    @PPP public static final String SET_WIFI_ENABLED = "setWifiEnabled";
    @PPP public static final String START_SCAN = "startScan";
    @PPP public static final String UPDATE_NETWORK = "updateNetwork";

    /**
     * @see {@link android.net.wifi.WifiManager#addNetwork(WifiConfiguration)}
     */
    public static WifiRequest addNetwork(WifiConfiguration wifiConfiguration) {
        RequestParams p = new RequestParams();
        p.opCode = ADD_NETWORK;
        p.wifiConfiguration0 = wifiConfiguration;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#disableNetwork(int)}
     */
    public static WifiRequest disableNetwork(int netId) {
        RequestParams p = new RequestParams();
        p.opCode = DISABLE_NETWORK;
        p.int0 = netId;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#disconnect()}
     */
    public static WifiRequest disconnect() {
        RequestParams p = new RequestParams();
        p.opCode = DISCONNECT;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#enableNetwork(int, boolean)}
     */
    public static WifiRequest enableNetwork(int netId, boolean disableOthers) {
        RequestParams p = new RequestParams();
        p.opCode = ENABLE_NETWORK;
        p.int0 = netId;
        p.boolean0 = disableOthers;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#getConfiguredNetworks()}
     */
    public static WifiRequest getConfiguredNetworks() {
        RequestParams p = new RequestParams();
        p.opCode = GET_CONFIGURED_NETWORKS;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#getConnectionInfo()}
     */
    public static WifiRequest getConnectionInfo() {
        RequestParams p = new RequestParams();
        p.opCode = GET_CONNECTION_INFO;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#getDhcpInfo()}
     */
    public static WifiRequest getDhcpInfo() {
        RequestParams p = new RequestParams();
        p.opCode = GET_DHCP_INFO;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#getScanResults()}
     */
    public static WifiRequest getScanResults() {
        RequestParams p = new RequestParams();
        p.opCode = GET_SCAN_RESULTS;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#getWifiState()}
     */
    public static WifiRequest getWifiState() {
        RequestParams p = new RequestParams();
        p.opCode = GET_WIFI_STATE;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#isWifiEnabled()}
     */
    public static WifiRequest isWifiEnabled() {
        RequestParams p = new RequestParams();
        p.opCode = IS_WIFI_ENABLED;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#pingSupplicant()}
     */
    public static WifiRequest pingSupplicant() {
        RequestParams p = new RequestParams();
        p.opCode = PING_SUPPLICANT;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#reassociate()}
     */

    public static WifiRequest reassociate() {
        RequestParams p = new RequestParams();
        p.opCode = REASSOCIATE;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#reconnect()}
     */

    public static WifiRequest reconnect() {
        RequestParams p = new RequestParams();
        p.opCode = RECONNECT;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#removeNetwork(int)}
     */
    public static WifiRequest removeNetwork(int netId) {
        RequestParams p = new RequestParams();
        p.opCode = REMOVE_NETWORK;
        p.int0 = netId;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#saveConfiguration()}
     */
    public static WifiRequest saveConfiguration() {
        RequestParams p = new RequestParams();
        p.opCode = SAVE_CONFIGURATION;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#setWifiEnabled(boolean)}
     */
    public static WifiRequest setWifiEnabled(boolean enabled) {
        RequestParams p = new RequestParams();
        p.opCode = SET_WIFI_ENABLED;
        p.boolean0 = enabled;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#startScan()}
     */
    public static WifiRequest startScan() {
        RequestParams p = new RequestParams();
        p.opCode = START_SCAN;
        return new WifiRequest(p);
    }

    /**
     * @see {@link android.net.wifi.WifiManager#updateNetwork(WifiConfiguration)}
     */
    public static WifiRequest updateNetwork(WifiConfiguration wifiConfiguration) {
        RequestParams p = new RequestParams();
        p.opCode = UPDATE_NETWORK;
        p.wifiConfiguration0 = wifiConfiguration;
        return new WifiRequest(p);
    }

    public WifiRequest(RequestParams params) {
        super(params);
    }
}
