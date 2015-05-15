package com.sdchang.permissionpolice.lib.request.wifi;

import android.content.Context;
import android.content.IntentFilter;
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

    @Override
    public int getRequestType() {
        return WIFI_MGR_REQUEST;
    }

    public void startRequest(Context context, String reason, WifiManagerResponseListener listener) {
        context.registerReceiver(new WifiManagerHandshakeReceiver(listener),
                new IntentFilter(WifiManagerHandshakeReceiver.ACTION_FILTER));
        super.startRequest(context, reason);
    }

    @Op
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
    @IntDef({REASSOCIATE, START_SCAN, DISABLE_NETWORK, UPDATE_NETWORK, REMOVE_NETWORK, RECONNECT, ADD_NETWORK,
            ENABLE_NETWORK, DISCONNECT, SET_WIFI_ENABLED, SAVE_CONFIGURATION})
    public @interface Op {}

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
