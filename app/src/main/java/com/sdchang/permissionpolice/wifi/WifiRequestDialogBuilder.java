package com.sdchang.permissionpolice.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.sdchang.permissionpolice.BaseDialogBuilder;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;

import java.util.ArrayList;

/**
 *
 */
public class WifiRequestDialogBuilder extends BaseDialogBuilder<WifiManagerRequest> {

    public WifiRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
    }

    @Override
    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int string = 0;
        switch (mRequest.opCode()) {
            case WifiManagerRequest.ADD_NETWORK:
                string = R.string.dialogTitle_wifiAddNetwork;
                break;
            case WifiManagerRequest.DISABLE_NETWORK:
                string = R.string.dialogTitle_wifiDisableNetwork;
                break;
            case WifiManagerRequest.DISCONNECT:
                string = R.string.dialogTitle_wifiDisconnect;
                break;
            case WifiManagerRequest.ENABLE_NETWORK:
                string = R.string.dialogTitle_wifiEnableNetwork;
                break;
            case WifiManagerRequest.REASSOCIATE:
                string = R.string.dialogTitle_wifiReassociate;
                break;
            case WifiManagerRequest.RECONNECT:
                string = R.string.dialogTitle_wifiReconnect;
                break;
            case WifiManagerRequest.REMOVE_NETWORK:
                string = R.string.dialogTitle_wifiRemoveNetwork;
                break;
            case WifiManagerRequest.SAVE_CONFIGURATION:
                string = R.string.dialogTitle_wifiSaveConfiguration;
                break;
            case WifiManagerRequest.SET_WIFI_ENABLED:
                string = mRequest.bool() ? R.string.dialogTitle_wifiSetWifiEnabled_enable :
                        R.string.dialogTitle_wifiSetWifiEnabled_disable;
                break;
            case WifiManagerRequest.START_SCAN:
                string = R.string.dialogTitle_wifiStartScan;
                break;
            case WifiManagerRequest.UPDATE_NETWORK:
                string = R.string.dialogTitle_wifiUpdateNetwork;
                break;
        }
        return boldAppLabel.append(mActivity.getText(string));
    }

    @Override
    protected void onAllowRequest() {
        super.onAllowRequest();
        WifiManager wifi = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
        Bundle response = new Bundle();
        switch (mRequest.opCode()) {
            case WifiManagerRequest.GET_CONFIGURED_NETWORKS:
                response.putParcelableArrayList(mRequest.opCode(), new ArrayList<>(wifi.getConfiguredNetworks()));
                break;
            case WifiManagerRequest.GET_CONNECTION_INFO:
                response.putParcelable(mRequest.opCode(), wifi.getConnectionInfo());
                break;
            case WifiManagerRequest.GET_DHCP_INFO:
                response.putParcelable(mRequest.opCode(), wifi.getDhcpInfo());
                break;
            case WifiManagerRequest.GET_SCAN_RESULTS:
                response.putParcelableArrayList(mRequest.opCode(), new ArrayList<>(wifi.getScanResults()));
                break;
            case WifiManagerRequest.GET_WIFI_STATE:
                response.putInt(mRequest.opCode(), wifi.getWifiState());
                break;
            case WifiManagerRequest.IS_WIFI_ENABLED:
                response.putBoolean(mRequest.opCode(), wifi.isWifiEnabled());
                break;
            case WifiManagerRequest.PING_SUPPLICANT:
                response.putBoolean(mRequest.opCode(), wifi.pingSupplicant());
                break;
            case WifiManagerRequest.ADD_NETWORK:
                response.putInt(mRequest.opCode(), wifi.addNetwork(mRequest.wifiConfiguration()));
                break;
            case WifiManagerRequest.DISABLE_NETWORK:
                response.putBoolean(mRequest.opCode(), wifi.disableNetwork(mRequest.integer()));
                break;
            case WifiManagerRequest.DISCONNECT:
                response.putBoolean(mRequest.opCode(), wifi.disconnect());
                break;
            case WifiManagerRequest.ENABLE_NETWORK:
                response.putBoolean(mRequest.opCode(), wifi.enableNetwork(mRequest.integer(), mRequest.bool()));
                break;
            case WifiManagerRequest.REASSOCIATE:
                response.putBoolean(mRequest.opCode(), wifi.reassociate());
                break;
            case WifiManagerRequest.RECONNECT:
                response.putBoolean(mRequest.opCode(), wifi.reconnect());
                break;
            case WifiManagerRequest.REMOVE_NETWORK:
                response.putBoolean(mRequest.opCode(), wifi.removeNetwork(mRequest.integer()));
                break;
            case WifiManagerRequest.SAVE_CONFIGURATION:
                response.putBoolean(mRequest.opCode(), wifi.saveConfiguration());
                break;
            case WifiManagerRequest.SET_WIFI_ENABLED:
                response.putBoolean(mRequest.opCode(), wifi.setWifiEnabled(mRequest.bool()));
                break;
            case WifiManagerRequest.START_SCAN:
                response.putBoolean(mRequest.opCode(), wifi.startScan());
                break;
            case WifiManagerRequest.UPDATE_NETWORK:
                response.putInt(mRequest.opCode(), wifi.updateNetwork(mRequest.wifiConfiguration()));
                break;
        }
        mActivity.sendBroadcast(new Intent(WifiManagerRequest.WIFI_INTENT_FILTER)
                .putExtra(Police.APPROVED, true)
                .putExtra(Police.RESPONSE, response));
    }

    @Override
    protected void onDenyRequest() {
        super.onDenyRequest();
        mActivity.sendBroadcast(new Intent(WifiManagerRequest.WIFI_INTENT_FILTER)
                .putExtra(Police.APPROVED, false));
    }
}
