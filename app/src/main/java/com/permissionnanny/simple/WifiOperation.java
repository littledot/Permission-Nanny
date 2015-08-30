package com.permissionnanny.simple;

import android.Manifest;
import android.content.Context;
import android.content.pm.PermissionInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import com.permissionnanny.ProxyFunction;
import com.permissionnanny.R;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.WifiRequest;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 *
 */
public class WifiOperation {
    public static final SimpleOperation[] operations = new SimpleOperation[]{
            new SimpleOperation(WifiRequest.ADD_NETWORK,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiAddNetwork, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putInt(request.opCode, mgr.addNetwork(request.wifiConfiguration0));
                }
            }),
            new SimpleOperation(WifiRequest.DISABLE_NETWORK,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiDisableNetwork, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.disableNetwork(request.int0));
                }
            }),
            new SimpleOperation(WifiRequest.DISCONNECT,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiDisconnect, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.disconnect());
                }
            }),
            new SimpleOperation(WifiRequest.ENABLE_NETWORK,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiEnableNetwork, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.enableNetwork(request.int0, request.boolean0));
                }
            }),
            new SimpleOperation(WifiRequest.GET_CONFIGURED_NETWORKS,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiGetConfiguredNetworks, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putParcelableArrayList(request.opCode, new ArrayList<>(mgr.getConfiguredNetworks()));
                }
            }),
            new SimpleOperation(WifiRequest.GET_CONNECTION_INFO,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiGetConnectionInfo, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putParcelable(request.opCode, mgr.getConnectionInfo());
                }
            }),
            new SimpleOperation(WifiRequest.GET_DHCP_INFO,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiGetDhcpInfo, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putParcelable(request.opCode, mgr.getDhcpInfo());
                }
            }),
            new SimpleOperation(WifiRequest.GET_SCAN_RESULTS,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiGetScanResults, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putParcelableArrayList(request.opCode, new ArrayList<>(mgr.getScanResults()));
                }
            }),
            new SimpleOperation(WifiRequest.GET_WIFI_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiGetWifiState, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putInt(request.opCode, mgr.getWifiState());
                }
            }),
            new SimpleOperation(WifiRequest.IS_WIFI_ENABLED,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiIsWifiEnabled, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.isWifiEnabled());
                }
            }),
            new SimpleOperation(WifiRequest.PING_SUPPLICANT,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiPingSupplicant, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.pingSupplicant());
                }
            }),
            new SimpleOperation(WifiRequest.REASSOCIATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiReassociate, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.reassociate());
                }
            }),
            new SimpleOperation(WifiRequest.RECONNECT,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiReconnect, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.reconnect());
                }
            }),
            new SimpleOperation(WifiRequest.REMOVE_NETWORK,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiRemoveNetwork, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.removeNetwork(request.int0));
                }
            }),
            new SimpleOperation(WifiRequest.SAVE_CONFIGURATION,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiSaveConfiguration, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.saveConfiguration());
                }
            }),
            new SimpleOperation(WifiRequest.SET_WIFI_ENABLED,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    0, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.setWifiEnabled(request.boolean0));
                }
            }),
            new SimpleOperation(WifiRequest.START_SCAN,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiStartScan, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putBoolean(request.opCode, mgr.startScan());
                }
            }),
            new SimpleOperation(WifiRequest.UPDATE_NETWORK,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    PermissionInfo.PROTECTION_NORMAL,
                    R.string.dialogTitle_wifiUpdateNetwork, 1, new ProxyFunction() {
                @Override
                public void execute(Context context, RequestParams request, Bundle response) {
                    WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    response.putInt(request.opCode, mgr.updateNetwork(request.wifiConfiguration0));
                }
            })
    };

    public static SimpleOperation getOperation(String opCode) {
        for (SimpleOperation operation : operations) {
            if (operation.mOpCode.equals(opCode)) {
                return operation;
            }
        }
        return null;
    }

    @Inject
    public WifiOperation() {}
}
