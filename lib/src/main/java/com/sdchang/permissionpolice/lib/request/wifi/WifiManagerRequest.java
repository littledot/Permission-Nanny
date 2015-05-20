package com.sdchang.permissionpolice.lib.request.wifi;

import android.net.wifi.WifiConfiguration;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import auto.parcel.AutoParcel;
import com.sdchang.permissionpolice.lib.request.BaseRequest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;

/**
 *
 */
@AutoParcel
public abstract class WifiManagerRequest extends BaseRequest {

    static Builder newBuilder() {
        return new AutoParcel_WifiManagerRequest.Builder();
    }

    @AutoParcel.Builder
    static abstract class Builder {
        public abstract Builder opCode(@Op String value);

        public abstract Builder wifiConfiguration(WifiConfiguration value);

        public abstract Builder integer(int value);

        public abstract Builder iNetAddress(InetAddress value);

        public abstract Builder bool(boolean value);

        public abstract Builder string(String value);

        public abstract WifiManagerRequest build();
    }

    @Override
    public int getRequestType() {
        return WIFI_REQUEST;
    }

    public abstract String opCode();

    @Nullable
    public abstract WifiConfiguration wifiConfiguration();

    @Nullable
    public abstract int integer();

    @Nullable
    public abstract InetAddress iNetAddress();

    @Nullable
    public abstract boolean bool();

    @Nullable
    public abstract String string();

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({GET_SCAN_RESULTS, GET_DHCP_INFO, PING_SUPPLICANT, IS_WIFI_ENABLED, GET_CONNECTION_INFO, GET_WIFI_STATE,
            GET_CONFIGURED_NETWORKS,
            REASSOCIATE, START_SCAN, DISABLE_NETWORK, UPDATE_NETWORK, REMOVE_NETWORK, RECONNECT, ADD_NETWORK,
            ENABLE_NETWORK, DISCONNECT, SET_WIFI_ENABLED, SAVE_CONFIGURATION,
    })
    public @interface Op {}

    // ACCESS_WIFI_STATE
    public static final String GET_SCAN_RESULTS = "getScanResults";
    public static final String GET_DHCP_INFO = "getDhcpInfo";
    public static final String PING_SUPPLICANT = "pingSupplicant";
    public static final String IS_WIFI_ENABLED = "isWifiEnabled";
    public static final String GET_CONNECTION_INFO = "getConnectionInfo";
    public static final String GET_WIFI_STATE = "getWifiState";
    public static final String GET_CONFIGURED_NETWORKS = "getConfiguredNetworks";

    public static WifiManagerRequest newGetScanResultsRequest() {
        return newBuilder().opCode(GET_SCAN_RESULTS).build();
    }

    public static WifiManagerRequest newGetDhcpInfoRequest() {
        return newBuilder().opCode(GET_DHCP_INFO).build();
    }

    public static WifiManagerRequest newPingSupplicantRequest() {
        return newBuilder().opCode(PING_SUPPLICANT).build();
    }

    public static WifiManagerRequest newIsWifiEnabledRequest() {
        return newBuilder().opCode(IS_WIFI_ENABLED).build();
    }

    public static WifiManagerRequest newGetConnectionInfoRequest() {
        return newBuilder().opCode(GET_CONNECTION_INFO).build();
    }

    public static WifiManagerRequest newGetWifiStateRequest() {
        return newBuilder().opCode(GET_WIFI_STATE).build();
    }

    public static WifiManagerRequest newGetConfiguredNetworksRequest() {
        return newBuilder().opCode(GET_CONFIGURED_NETWORKS).build();
    }

    // CHANGE_WIFI_STATE
    public static final String REASSOCIATE = "reassociate";
    public static final String START_SCAN = "startScan";
    public static final String DISABLE_NETWORK = "disableNetwork";
    public static final String UPDATE_NETWORK = "updateNetwork";
    public static final String REMOVE_NETWORK = "removeNetwork";
    public static final String RECONNECT = "reconnect";
    public static final String ADD_NETWORK = "addNetwork";
    public static final String ENABLE_NETWORK = "enableNetwork";
    public static final String DISCONNECT = "disconnect";
    public static final String SET_WIFI_ENABLED = "setWifiEnabled";
    public static final String SAVE_CONFIGURATION = "saveConfiguration";

    public static WifiManagerRequest newReassociateRequest() {
        return newBuilder().opCode(REASSOCIATE).build();
    }

    public static WifiManagerRequest newStartScanRequest() {
        return newBuilder().opCode(START_SCAN).build();
    }

    public static WifiManagerRequest newDisableNetworkRequest(int netId) {
        return newBuilder().opCode(DISABLE_NETWORK).integer(netId).build();
    }

    public static WifiManagerRequest newUpdateNetworkRequest(WifiConfiguration wifiConfiguration) {
        return newBuilder().opCode(UPDATE_NETWORK).wifiConfiguration(wifiConfiguration).build();
    }

    public static WifiManagerRequest newRemoveNetworkRequest(int netId) {
        return newBuilder().opCode(REMOVE_NETWORK).integer(netId).build();
    }

    public static WifiManagerRequest newReconnectRequest() {
        return newBuilder().opCode(RECONNECT).build();
    }

    public static WifiManagerRequest newAddNetworkRequest(WifiConfiguration wifiConfiguration) {
        return newBuilder().opCode(ADD_NETWORK).wifiConfiguration(wifiConfiguration).build();
    }

    public static WifiManagerRequest newEnableNetworkRequest(int netId, boolean disableOthers) {
        return newBuilder().opCode(ENABLE_NETWORK).integer(netId).bool(disableOthers).build();
    }

    public static WifiManagerRequest newDisconnectRequest() {
        return newBuilder().opCode(DISCONNECT).build();
    }

    public static WifiManagerRequest newSetWifiEnabledRequest(boolean enabled) {
        return newBuilder().opCode(SET_WIFI_ENABLED).bool(enabled).build();
    }

    public static WifiManagerRequest newSaveConfigurationRequest() {
        return newBuilder().opCode(SAVE_CONFIGURATION).build();
    }
}
