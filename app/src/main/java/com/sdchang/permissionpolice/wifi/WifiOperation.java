package com.sdchang.permissionpolice.wifi;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.StringRes;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.request.RequestParams;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;

import java.util.ArrayList;

/**
 *
 */
class WifiOperation {
    static final WifiOperation[] operations = new WifiOperation[]{
            new WifiOperation(WifiManagerRequest.ADD_NETWORK,
                    R.string.dialogTitle_wifiAddNetwork, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putInt(request.opCode(), wifi.addNetwork(request.wifiConfiguration0()));
                }
            }),
            new WifiOperation(WifiManagerRequest.DISABLE_NETWORK,
                    R.string.dialogTitle_wifiDisableNetwork, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.disableNetwork(request.int0()));
                }
            }),
            new WifiOperation(WifiManagerRequest.DISCONNECT,
                    R.string.dialogTitle_wifiDisconnect, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.disconnect());
                }
            }),
            new WifiOperation(WifiManagerRequest.ENABLE_NETWORK,
                    R.string.dialogTitle_wifiEnableNetwork, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.enableNetwork(request.int0(), request.boolean0()));
                }
            }),
            new WifiOperation(WifiManagerRequest.GET_CONFIGURED_NETWORKS,
                    R.string.dialogTitle_wifiGetConfiguredNetworks, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putParcelableArrayList(request.opCode(), new ArrayList<>(wifi.getConfiguredNetworks()));
                }
            }),
            new WifiOperation(WifiManagerRequest.GET_CONNECTION_INFO,
                    R.string.dialogTitle_wifiGetConnectionInfo, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putParcelable(request.opCode(), wifi.getConnectionInfo());
                }
            }),
            new WifiOperation(WifiManagerRequest.GET_DHCP_INFO,
                    R.string.dialogTitle_wifiGetDhcpInfo, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putParcelable(request.opCode(), wifi.getDhcpInfo());
                }
            }),
            new WifiOperation(WifiManagerRequest.GET_SCAN_RESULTS,
                    R.string.dialogTitle_wifiGetScanResults, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putParcelableArrayList(request.opCode(), new ArrayList<>(wifi.getScanResults()));
                }
            }),
            new WifiOperation(WifiManagerRequest.GET_WIFI_STATE,
                    R.string.dialogTitle_wifiGetWifiState, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putInt(request.opCode(), wifi.getWifiState());
                }
            }),
            new WifiOperation(WifiManagerRequest.IS_WIFI_ENABLED,
                    R.string.dialogTitle_wifiIsWifiEnabled, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.isWifiEnabled());
                }
            }),
            new WifiOperation(WifiManagerRequest.PING_SUPPLICANT,
                    R.string.dialogTitle_wifiPingSupplicant, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.pingSupplicant());
                }
            }),
            new WifiOperation(WifiManagerRequest.REASSOCIATE,
                    R.string.dialogTitle_wifiReassociate, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.reassociate());
                }
            }),
            new WifiOperation(WifiManagerRequest.RECONNECT,
                    R.string.dialogTitle_wifiReconnect, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.reconnect());
                }
            }),
            new WifiOperation(WifiManagerRequest.REMOVE_NETWORK,
                    R.string.dialogTitle_wifiRemoveNetwork, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.removeNetwork(request.int0()));
                }
            }),
            new WifiOperation(WifiManagerRequest.SAVE_CONFIGURATION,
                    R.string.dialogTitle_wifiSaveConfiguration, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.saveConfiguration());
                }
            }),
            new WifiOperation(WifiManagerRequest.SET_WIFI_ENABLED,
                    0, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.setWifiEnabled(request.boolean0()));
                }
            }),
            new WifiOperation(WifiManagerRequest.START_SCAN,
                    R.string.dialogTitle_wifiStartScan, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putBoolean(request.opCode(), wifi.startScan());
                }
            }),
            new WifiOperation(WifiManagerRequest.UPDATE_NETWORK,
                    R.string.dialogTitle_wifiUpdateNetwork, 1, new WifiFunction() {
                @Override
                public void execute(WifiManager wifi, RequestParams request, Bundle response) {
                    response.putInt(request.opCode(), wifi.updateNetwork(request.wifiConfiguration0()));
                }
            })
    };

    public final String mOpCode;
    @StringRes public final int mDialogTitle;
    public final int mMinSdk;
    public final WifiFunction mFunction;

    public WifiOperation(String opCode,
                         int dialogTitle,
                         int minSdk,
                         WifiFunction function) {
        mOpCode = opCode;
        mDialogTitle = dialogTitle;
        mMinSdk = minSdk;
        mFunction = function;
    }
}
