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

        public abstract Builder wifiConfiguration0(WifiConfiguration value);

        public abstract Builder int0(int value);

        public abstract Builder inetAddress0(InetAddress value);

        public abstract Builder boolean0(boolean value);

        public abstract Builder string0(String value);

        public abstract WifiManagerRequest build();
    }

    @Override
    public int getRequestType() {
        return WIFI_REQUEST;
    }

    public abstract String opCode();

    @Nullable
    public abstract WifiConfiguration wifiConfiguration0();

    @Nullable
    public abstract int int0();

    @Nullable
    public abstract InetAddress inetAddress0();

    @Nullable
    public abstract boolean boolean0();

    @Nullable
    public abstract String string0();

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({GET_SCAN_RESULTS, GET_DHCP_INFO, PING_SUPPLICANT, IS_WIFI_ENABLED, GET_CONNECTION_INFO, GET_WIFI_STATE,
            GET_CONFIGURED_NETWORKS,
            REASSOCIATE, START_SCAN, DISABLE_NETWORK, UPDATE_NETWORK, REMOVE_NETWORK, RECONNECT, ADD_NETWORK,
            ENABLE_NETWORK, DISCONNECT, SET_WIFI_ENABLED, SAVE_CONFIGURATION,
    })
    public @interface Op {}

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
        return newBuilder().opCode(ADD_NETWORK).wifiConfiguration0(wifiConfiguration).build();
    }

    public static WifiManagerRequest disableNetwork(int netId) {
        return newBuilder().opCode(DISABLE_NETWORK).int0(netId).build();
    }

    public static WifiManagerRequest disconnect() {
        return newBuilder().opCode(DISCONNECT).build();
    }

    public static WifiManagerRequest enableNetwork(int netId, boolean disableOthers) {
        return newBuilder().opCode(ENABLE_NETWORK).int0(netId).boolean0(disableOthers).build();
    }

    public static WifiManagerRequest getConfiguredNetworks() {
        return newBuilder().opCode(GET_CONFIGURED_NETWORKS).build();
    }

    public static WifiManagerRequest getConnectionInfo() {
        return newBuilder().opCode(GET_CONNECTION_INFO).build();
    }

    public static WifiManagerRequest getDhcpInfo() {
        return newBuilder().opCode(GET_DHCP_INFO).build();
    }

    public static WifiManagerRequest getScanResults() {
        return newBuilder().opCode(GET_SCAN_RESULTS).build();
    }

    public static WifiManagerRequest getWifiState() {
        return newBuilder().opCode(GET_WIFI_STATE).build();
    }

    public static WifiManagerRequest isWifiEnabled() {
        return newBuilder().opCode(IS_WIFI_ENABLED).build();
    }

    public static WifiManagerRequest pingSupplicant() {
        return newBuilder().opCode(PING_SUPPLICANT).build();
    }

    public static WifiManagerRequest reassociate() {
        return newBuilder().opCode(REASSOCIATE).build();
    }

    public static WifiManagerRequest reconnect() {
        return newBuilder().opCode(RECONNECT).build();
    }

    public static WifiManagerRequest removeNetwork(int netId) {
        return newBuilder().opCode(REMOVE_NETWORK).int0(netId).build();
    }

    public static WifiManagerRequest saveConfiguration() {
        return newBuilder().opCode(SAVE_CONFIGURATION).build();
    }

    public static WifiManagerRequest setWifiEnabled(boolean enabled) {
        return newBuilder().opCode(SET_WIFI_ENABLED).boolean0(enabled).build();
    }

    public static WifiManagerRequest startScan() {
        return newBuilder().opCode(START_SCAN).build();
    }

    public static WifiManagerRequest updateNetwork(WifiConfiguration wifiConfiguration) {
        return newBuilder().opCode(UPDATE_NETWORK).wifiConfiguration0(wifiConfiguration).build();
    }
}
