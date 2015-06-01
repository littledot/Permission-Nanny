package com.sdchang.permissionpolice.wifi;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;

import java.util.ArrayList;

/**
 *
 */
class WifiValues {
    static final String[] operations = new String[]{
            WifiManagerRequest.ADD_NETWORK,
            WifiManagerRequest.DISABLE_NETWORK,
            WifiManagerRequest.DISCONNECT,
            WifiManagerRequest.ENABLE_NETWORK,
            WifiManagerRequest.GET_CONFIGURED_NETWORKS,
            WifiManagerRequest.GET_CONNECTION_INFO,
            WifiManagerRequest.GET_DHCP_INFO,
            WifiManagerRequest.GET_SCAN_RESULTS,
            WifiManagerRequest.GET_WIFI_STATE,
            WifiManagerRequest.IS_WIFI_ENABLED,
            WifiManagerRequest.PING_SUPPLICANT,
            WifiManagerRequest.REASSOCIATE,
            WifiManagerRequest.RECONNECT,
            WifiManagerRequest.REMOVE_NETWORK,
            WifiManagerRequest.SAVE_CONFIGURATION,
            WifiManagerRequest.SET_WIFI_ENABLED,
            WifiManagerRequest.START_SCAN,
            WifiManagerRequest.UPDATE_NETWORK
    };

    static final int[] dialogTitles = new int[]{
            R.string.dialogTitle_wifiAddNetwork,
            R.string.dialogTitle_wifiDisableNetwork,
            R.string.dialogTitle_wifiDisconnect,
            R.string.dialogTitle_wifiEnableNetwork,
            R.string.dialogTitle_wifiGetConfiguredNetworks,
            R.string.dialogTitle_wifiGetConnectionInfo,
            R.string.dialogTitle_wifiGetDhcpInfo,
            R.string.dialogTitle_wifiGetScanResults,
            R.string.dialogTitle_wifiGetWifiState,
            R.string.dialogTitle_wifiIsWifiEnabled,
            R.string.dialogTitle_wifiPingSupplicant,
            R.string.dialogTitle_wifiReassociate,
            R.string.dialogTitle_wifiReconnect,
            R.string.dialogTitle_wifiRemoveNetwork,
            R.string.dialogTitle_wifiSaveConfiguration,
            0,
            R.string.dialogTitle_wifiStartScan,
            R.string.dialogTitle_wifiUpdateNetwork
    };

    static final WifiFunction[] functions = new WifiFunction[]{
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putInt(request.opCode(), wifi.addNetwork(request.wifiConfiguration0()));
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.disableNetwork(request.int0()));
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.disconnect());
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.enableNetwork(request.int0(), request.boolean0()));
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putParcelableArrayList(request.opCode(), new ArrayList<>(wifi.getConfiguredNetworks()));
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putParcelable(request.opCode(), wifi.getConnectionInfo());
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putParcelable(request.opCode(), wifi.getDhcpInfo());
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putParcelableArrayList(request.opCode(), new ArrayList<>(wifi.getScanResults()));
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putInt(request.opCode(), wifi.getWifiState());
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.isWifiEnabled());
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.pingSupplicant());
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.reassociate());
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.reconnect());
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.removeNetwork(request.int0()));
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.saveConfiguration());
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.setWifiEnabled(request.boolean0()));
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.startScan());
                }
            },
            new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, WifiManagerRequest request, Bundle response) {
                    response.putInt(request.opCode(), wifi.updateNetwork(request.wifiConfiguration0()));
                }
            }
    };
}
