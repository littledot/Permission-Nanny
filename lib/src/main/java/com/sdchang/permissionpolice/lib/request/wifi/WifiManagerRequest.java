package com.sdchang.permissionpolice.lib.request.wifi;

import android.net.wifi.WifiConfiguration;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
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
        public abstract Builder opCode(@Op int value);

        public abstract Builder wifiConfiguration(WifiConfiguration value);

        public abstract Builder integer(int value);

        public abstract Builder iNetAddress(InetAddress value);

        public abstract Builder bool(boolean value);

        public abstract Builder string(String value);

        public abstract WifiManagerRequest build();
    }

    public static final String WIFI_INTENT_FILTER = "WIFI_INTENT_FILTER";

    @Override
    public int getRequestType() {
        return WIFI_REQUEST;
    }

    @Override
    public String getIntentFilter() {
        return WIFI_INTENT_FILTER;
    }

    public abstract int opCode();

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
    @IntDef({GET_SCAN_RESULTS, GET_DHCP_INFO, PING_SUPPLICANT, IS_WIFI_ENABLED, GET_CONNECTION_INFO, GET_WIFI_STATE,
            GET_CONFIGURED_NETWORKS,
            REASSOCIATE, START_SCAN, DISABLE_NETWORK, UPDATE_NETWORK, REMOVE_NETWORK, RECONNECT, ADD_NETWORK,
            ENABLE_NETWORK, DISCONNECT, SET_WIFI_ENABLED, SAVE_CONFIGURATION,
    })
    public @interface Op {}

    // ACCESS_WIFI_STATE
    public static final int GET_SCAN_RESULTS = 1 << 16;
    public static final int GET_DHCP_INFO = 1 << 16 + 1;
    public static final int PING_SUPPLICANT = 1 << 16 + 2;
    public static final int IS_WIFI_ENABLED = 1 << 16 + 3;
    public static final int GET_CONNECTION_INFO = 1 << 16 + 4;
    public static final int GET_WIFI_STATE = 1 << 16 + 5;
    public static final int GET_CONFIGURED_NETWORKS = 1 << 17 + 6;

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
    public static final int REASSOCIATE = 1;
    public static final int START_SCAN = 2;
    public static final int DISABLE_NETWORK = 3;
    public static final int UPDATE_NETWORK = 4;
    public static final int REMOVE_NETWORK = 5;
    public static final int RECONNECT = 6;
    public static final int ADD_NETWORK = 7;
    public static final int ENABLE_NETWORK = 8;
    public static final int DISCONNECT = 9;
    public static final int SET_WIFI_ENABLED = 10;
    public static final int SAVE_CONFIGURATION = 11;

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
