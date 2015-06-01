package com.sdchang.permissionpolice.demo.wifi;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sdchang.permissionpolice.demo.BooleanExtra;
import com.sdchang.permissionpolice.demo.Extra;
import com.sdchang.permissionpolice.demo.IntegerExtra;
import com.sdchang.permissionpolice.demo.R;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;

/**
 *
 */
public class WifiRequestFactory {
    String[] mLabels = new String[]{
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
            WifiManagerRequest.UPDATE_NETWORK,
    };
    Extra[][] mExtras = new Extra[][]{
            null,
            new Extra[]{new IntegerExtra()},
            null,
            new Extra[]{new IntegerExtra(), new BooleanExtra()},
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            new Extra[]{new IntegerExtra()},
            null,
            new Extra[]{new BooleanExtra()},
            null,
            null,
    };
    String[][] mExtrasLabel = new String[][]{
            new String[]{"wifiConfiguration"},
            new String[]{"netId"},
            null,
            new String[]{"netId", "disableOthers"},
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            new String[]{"netId"},
            null,
            new String[]{"enabled"},
            null,
            new String[]{"wifiConfiguration"},
    };

    public WifiManagerRequest getRequest(int position) {
        Extra[] extras = mExtras[position];
        switch (position) {
        case 0:
            return WifiManagerRequest.addNetwork(null);
        case 1:
            return WifiManagerRequest.disableNetwork((int) extras[0].getValue());
        case 2:
            return WifiManagerRequest.disconnect();
        case 3:
            return WifiManagerRequest.enableNetwork((int) extras[0].getValue(), (boolean) extras[1].getValue());
        case 4:
            return WifiManagerRequest.getConfiguredNetworks();
        case 5:
            return WifiManagerRequest.getConnectionInfo();
        case 6:
            return WifiManagerRequest.getDhcpInfo();
        case 7:
            return WifiManagerRequest.getScanResults();
        case 8:
            return WifiManagerRequest.getWifiState();
        case 9:
            return WifiManagerRequest.isWifiEnabled();
        case 10:
            return WifiManagerRequest.pingSupplicant();
        case 11:
            return WifiManagerRequest.reassociate();
        case 12:
            return WifiManagerRequest.reconnect();
        case 13:
            return WifiManagerRequest.removeNetwork((int) extras[0].getValue());
        case 14:
            return WifiManagerRequest.saveConfiguration();
        case 15:
            return WifiManagerRequest.setWifiEnabled((boolean) extras[0].getValue());
        case 16:
            return WifiManagerRequest.startScan();
        case 17:
            return WifiManagerRequest.updateNetwork(null);
        }
        return null;
    }

    public int getCount() {
        return mLabels.length;
    }

    public String getLabel(int position) {
        return mLabels[position];
    }

    public boolean hasExtras(int position) {
        return mExtras[position] != null;
    }

    public Dialog getDialog(Context context, ViewGroup parent, int position) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context)
                .inflate(R.layout.extras_dialog, parent, false);

        Extra[] extras = mExtras[position];
        String[] labels = mExtrasLabel[position];

        for (int i = 0, len = extras.length; i < len; i++) {
            View extraView = extras[i].getView(context, view);
            ((TextView) extraView.findViewById(R.id.tvLabel)).setText(labels[i]);
            view.addView(extraView);
        }
        return new AlertDialog.Builder(context)
                .setView(view)
                .create();
    }
}
