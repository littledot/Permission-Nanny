package com.sdchang.permissionpolice.demo.wifi;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;
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
    SparseArray<Extra[]> mExtras = new SparseArray<Extra[]>() {{
        put(1, new Extra[]{new IntegerExtra()});
        put(3, new Extra[]{new IntegerExtra(), new BooleanExtra()});
        put(13, new Extra[]{new IntegerExtra()});
        put(15, new Extra[]{new BooleanExtra()});
    }};
    SparseArray<String[]> mExtrasLabels = new SparseArray<String[]>() {{
        put(0, new String[]{"wifiConfiguration"});
        put(1, new String[]{"netId"});
        put(3, new String[]{"netId", "disableOthers"});
        put(13, new String[]{"netId"});
        put(15, new String[]{"enabled"});
        put(17, new String[]{"wifiConfiguration"});
    }};

    public WifiManagerRequest getRequest(int position) {
        Extra[] extras = mExtras.get(position);
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
        return mExtras.get(position) != null;
    }

    public Dialog getDialog(Context context, int position) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.extras_dialog, null);

        Extra[] extras = mExtras.get(position);
        String[] labels = mExtrasLabels.get(position);

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
