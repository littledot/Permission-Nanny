package com.permissionnanny.simple

import android.content.Context
import android.net.wifi.WifiManager
import com.permissionnanny.Manifest
import com.permissionnanny.PermissionInfo
import com.permissionnanny.R
import com.permissionnanny.lib.request.simple.WifiRequest
import java.util.*
import javax.inject.Inject

/**

 */
class WifiOperation
@Inject
constructor() {
    companion object {
        val operations = arrayOf(
                SimpleOperation(WifiRequest.ADD_NETWORK,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiAddNetwork, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putInt(request.opCode, mgr.addNetwork(request.wifiConfiguration0))
                        }),
                SimpleOperation(WifiRequest.DISABLE_NETWORK,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiDisableNetwork, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putBoolean(request.opCode, mgr.disableNetwork(request.int0))
                        }),
                SimpleOperation(WifiRequest.DISCONNECT,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiDisconnect, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putBoolean(request.opCode, mgr.disconnect())
                        }),
                SimpleOperation(WifiRequest.ENABLE_NETWORK,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiEnableNetwork, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

                            response.putBoolean(request.opCode, mgr.enableNetwork(request.int0, request.boolean0))
                        }),
                SimpleOperation(WifiRequest.GET_CONFIGURED_NETWORKS,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiGetConfiguredNetworks, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putParcelableArrayList(request.opCode, ArrayList(mgr.configuredNetworks))
                        }),
                SimpleOperation(WifiRequest.GET_CONNECTION_INFO,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiGetConnectionInfo, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putParcelable(request.opCode, mgr.connectionInfo)
                        }),
                SimpleOperation(WifiRequest.GET_DHCP_INFO,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiGetDhcpInfo, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putParcelable(request.opCode, mgr.dhcpInfo)
                        }),
                SimpleOperation(WifiRequest.GET_SCAN_RESULTS,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiGetScanResults, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putParcelableArrayList(request.opCode, ArrayList(mgr.scanResults))
                        }),
                SimpleOperation(WifiRequest.GET_WIFI_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiGetWifiState, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putInt(request.opCode, mgr.wifiState)
                        }),
                SimpleOperation(WifiRequest.IS_WIFI_ENABLED,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiIsWifiEnabled, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putBoolean(request.opCode, mgr.isWifiEnabled)
                        }),
                SimpleOperation(WifiRequest.PING_SUPPLICANT,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiPingSupplicant, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putBoolean(request.opCode, mgr.pingSupplicant())
                        }),
                SimpleOperation(WifiRequest.REASSOCIATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiReassociate, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putBoolean(request.opCode, mgr.reassociate())
                        }),
                SimpleOperation(WifiRequest.RECONNECT,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiReconnect, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putBoolean(request.opCode, mgr.reconnect())
                        }),
                SimpleOperation(WifiRequest.REMOVE_NETWORK,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiRemoveNetwork, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putBoolean(request.opCode, mgr.removeNetwork(request.int0))
                        }),
                SimpleOperation(WifiRequest.SAVE_CONFIGURATION,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiSaveConfiguration, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putBoolean(request.opCode, mgr.saveConfiguration())
                        }),
                SimpleOperation(WifiRequest.SET_WIFI_ENABLED,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putBoolean(request.opCode, mgr.setWifiEnabled(request.boolean0))
                        }),
                SimpleOperation(WifiRequest.START_SCAN,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiStartScan, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putBoolean(request.opCode, mgr.startScan())
                        }),
                SimpleOperation(WifiRequest.UPDATE_NETWORK,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        PermissionInfo.PROTECTION_NORMAL,
                        R.string.dialogTitle_wifiUpdateNetwork, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                            response.putInt(request.opCode, mgr.updateNetwork(request.wifiConfiguration0))
                        })
        )

        fun getOperation(opCode: String): SimpleOperation? {
            for (operation in operations) {
                if (operation.opCode == opCode) {
                    return operation
                }
            }
            return null
        }
    }
}
