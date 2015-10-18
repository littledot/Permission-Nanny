package com.permissionnanny.demo.wifi;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import com.permissionnanny.demo.DataAdapter;
import com.permissionnanny.demo.ResponseDisplayListener;
import com.permissionnanny.demo.SimpleRequestFactory;
import com.permissionnanny.demo.extra.BooleanExtra;
import com.permissionnanny.demo.extra.Extra;
import com.permissionnanny.demo.extra.ExtrasDialogBuilder;
import com.permissionnanny.demo.extra.IntegerExtra;
import com.permissionnanny.lib.request.simple.SimpleRequest;
import com.permissionnanny.lib.request.simple.WifiRequest;

/**
 *
 */
public class WifiRequestFactory implements SimpleRequestFactory {
    private String[] mLabels = new String[]{
            WifiRequest.ADD_NETWORK,
            WifiRequest.DISABLE_NETWORK,
            WifiRequest.DISCONNECT,
            WifiRequest.ENABLE_NETWORK,
            WifiRequest.GET_CONFIGURED_NETWORKS,
            WifiRequest.GET_CONNECTION_INFO,
            WifiRequest.GET_DHCP_INFO,
            WifiRequest.GET_SCAN_RESULTS,
            WifiRequest.GET_WIFI_STATE,
            WifiRequest.IS_WIFI_ENABLED,
            WifiRequest.PING_SUPPLICANT,
            WifiRequest.REASSOCIATE,
            WifiRequest.RECONNECT,
            WifiRequest.REMOVE_NETWORK,
            WifiRequest.SAVE_CONFIGURATION,
            WifiRequest.SET_WIFI_ENABLED,
            WifiRequest.START_SCAN,
            WifiRequest.UPDATE_NETWORK,
    };
    private SparseArray<Extra[]> mExtras = new SparseArray<Extra[]>() {{
        put(1, new Extra[]{new IntegerExtra()});
        put(3, new Extra[]{new IntegerExtra(), new BooleanExtra()});
        put(13, new Extra[]{new IntegerExtra()});
        put(15, new Extra[]{new BooleanExtra()});
    }};
    private SparseArray<String[]> mExtrasLabels = new SparseArray<String[]>() {{
        put(0, new String[]{"wifiConfiguration"});
        put(1, new String[]{"netId"});
        put(3, new String[]{"netId", "disableOthers"});
        put(13, new String[]{"netId"});
        put(15, new String[]{"enabled"});
        put(17, new String[]{"wifiConfiguration"});
    }};

    private ExtrasDialogBuilder mBuilder = new ExtrasDialogBuilder();

    @Override
    public SimpleRequest getRequest(int position, DataAdapter adapter) {
        return getRequest(position).listener(new ResponseDisplayListener(position, adapter));
    }

    public WifiRequest getRequest(int position) {
        Extra[] extras = mExtras.get(position);
        switch (position) {
        case 0:
            return WifiRequest.addNetwork(null);
        case 1:
            return WifiRequest.disableNetwork((int) extras[0].getValue());
        case 2:
            return WifiRequest.disconnect();
        case 3:
            return WifiRequest.enableNetwork((int) extras[0].getValue(), (boolean) extras[1].getValue());
        case 4:
            return WifiRequest.getConfiguredNetworks();
        case 5:
            return WifiRequest.getConnectionInfo();
        case 6:
            return WifiRequest.getDhcpInfo();
        case 7:
            return WifiRequest.getScanResults();
        case 8:
            return WifiRequest.getWifiState();
        case 9:
            return WifiRequest.isWifiEnabled();
        case 10:
            return WifiRequest.pingSupplicant();
        case 11:
            return WifiRequest.reassociate();
        case 12:
            return WifiRequest.reconnect();
        case 13:
            return WifiRequest.removeNetwork((int) extras[0].getValue());
        case 14:
            return WifiRequest.saveConfiguration();
        case 15:
            return WifiRequest.setWifiEnabled((boolean) extras[0].getValue());
        case 16:
            return WifiRequest.startScan();
        case 17:
            return WifiRequest.updateNetwork(null);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mLabels.length;
    }

    @Override
    public String getLabel(int position) {
        return mLabels[position];
    }

    @Override
    public boolean hasExtras(int position) {
        return mExtras.get(position) != null;
    }

    @Override
    public Dialog buildDialog(Context context, int position) {
        return mBuilder.build(context, mExtras.get(position), mExtrasLabels.get(position));
    }
}
