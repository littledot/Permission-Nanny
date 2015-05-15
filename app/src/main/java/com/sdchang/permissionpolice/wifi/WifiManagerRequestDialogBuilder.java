package com.sdchang.permissionpolice.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerHandshakeReceiver;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerRequest;
import com.sdchang.permissionpolice.lib.request.wifi.WifiManagerResponse;
import timber.log.Timber;

import java.util.ArrayList;

/**
 *
 */
public class WifiManagerRequestDialogBuilder implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

    private static final int[] title = new int[]{};

    Activity mActivity;
    WifiManagerRequest mRequest;
    String mReason;
    CharSequence mTitle;

    public WifiManagerRequestDialogBuilder(Activity activity, Bundle args) {
        mActivity = activity;
        String appPackage = args.getString(BaseRequest.SENDER_PACKAGE);
        mRequest = args.getParcelable(BaseRequest.REQUEST_BODY);
        mReason = args.getString(BaseRequest.REQUEST_REASON);

        CharSequence appLabel;
        try {
            ApplicationInfo senderInfo = activity.getPackageManager().getApplicationInfo(appPackage, 0);
            appLabel = activity.getPackageManager().getApplicationLabel(senderInfo);
        } catch (NameNotFoundException e) {
            Timber.e(e, "senderPackage=%s", appPackage);
            appLabel = appPackage;
        }
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        switch (mRequest.opCode()) {
            case WifiManagerRequest.ADD_NETWORK:
                setDialogTitleText(boldAppLabel, R.string.dialogTitle_wifiAddNetwork);
                break;
            case WifiManagerRequest.DISABLE_NETWORK:
                setDialogTitleText(boldAppLabel, R.string.dialogTitle_wifiDisableNetwork);
                break;
            case WifiManagerRequest.DISCONNECT:
                setDialogTitleText(boldAppLabel, R.string.dialogTitle_wifiDisconnect);
                break;
            case WifiManagerRequest.ENABLE_NETWORK:
                setDialogTitleText(boldAppLabel, R.string.dialogTitle_wifiEnableNetwork);
                break;
            case WifiManagerRequest.REASSOCIATE:
                setDialogTitleText(boldAppLabel, R.string.dialogTitle_wifiReassociate);
                break;
            case WifiManagerRequest.RECONNECT:
                setDialogTitleText(boldAppLabel, R.string.dialogTitle_wifiReconnect);
                break;
            case WifiManagerRequest.REMOVE_NETWORK:
                setDialogTitleText(boldAppLabel, R.string.dialogTitle_wifiRemoveNetwork);
                break;
            case WifiManagerRequest.SAVE_CONFIGURATION:
                setDialogTitleText(boldAppLabel, R.string.dialogTitle_wifiSaveConfiguration);
                break;
            case WifiManagerRequest.SET_WIFI_ENABLED:
                setDialogTitleText(boldAppLabel, mRequest.bool() ? R.string.dialogTitle_wifiSetWifiEnabled_enable :
                        R.string.dialogTitle_wifiSetWifiEnabled_disable);
                break;
            case WifiManagerRequest.START_SCAN:
                setDialogTitleText(boldAppLabel, R.string.dialogTitle_wifiStartScan);
                break;
            case WifiManagerRequest.UPDATE_NETWORK:
                setDialogTitleText(boldAppLabel, R.string.dialogTitle_wifiUpdateNetwork);
                break;
        }
    }

    private void setDialogTitleText(SpannableStringBuilder appLabel, @StringRes int action, Object... args) {
        mTitle = appLabel.append(mActivity.getString(action, args));
    }

    public AlertDialog build() {
        return new AlertDialog.Builder(mActivity)
                .setTitle(mTitle)
                .setMessage(mReason)
                .setPositiveButton(R.string.dialog_allow, this)
                .setNegativeButton(R.string.dialog_deny, this)
                .setOnDismissListener(this)
                .setCancelable(false)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            WifiManager wifi = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
            Bundle response = new Bundle();
            switch (mRequest.opCode()) {
                case WifiManagerRequest.GET_CONFIGURED_NETWORKS:
                    response.putParcelableArrayList(WifiManagerResponse.WIFI_CONFIGURATIONS,
                            new ArrayList<>(wifi.getConfiguredNetworks()));
                    break;
                case WifiManagerRequest.GET_CONNECTION_INFO:
                    response.putParcelable(WifiManagerResponse.WIFI_INFO, wifi.getConnectionInfo());
                    break;
                case WifiManagerRequest.GET_DHCP_INFO:
                    response.putParcelable(WifiManagerResponse.DHCP_INFO, wifi.getDhcpInfo());
                    break;
                case WifiManagerRequest.GET_SCAN_RESULTS:
                    response.putParcelableArrayList(WifiManagerResponse.SCAN_RESULTS,
                            new ArrayList<>(wifi.getScanResults()));
                    break;
                case WifiManagerRequest.GET_WIFI_STATE:
                    response.putInt(WifiManagerResponse.WIFI_STATE, wifi.getWifiState());
                    break;
                case WifiManagerRequest.IS_WIFI_ENABLED:
                    response.putBoolean(WifiManagerResponse.WIFI_ENABLED, wifi.isWifiEnabled());
                    break;
                case WifiManagerRequest.PING_SUPPLICANT:
                    response.putBoolean(WifiManagerResponse.SUCCESS, wifi.pingSupplicant());
                    break;
                case WifiManagerRequest.ADD_NETWORK:
                    response.putInt(WifiManagerResponse.NETWORK_ID, wifi.addNetwork(mRequest.wifiConfiguration()));
                    break;
                case WifiManagerRequest.DISABLE_NETWORK:
                    response.putBoolean(WifiManagerResponse.SUCCESS, wifi.disableNetwork(mRequest.integer()));
                    break;
                case WifiManagerRequest.DISCONNECT:
                    response.putBoolean(WifiManagerResponse.SUCCESS, wifi.disconnect());
                    break;
                case WifiManagerRequest.ENABLE_NETWORK:
                    response.putBoolean(WifiManagerResponse.SUCCESS,
                            wifi.enableNetwork(mRequest.integer(), mRequest.bool()));
                    break;
                case WifiManagerRequest.REASSOCIATE:
                    response.putBoolean(WifiManagerResponse.SUCCESS, wifi.reassociate());
                    break;
                case WifiManagerRequest.RECONNECT:
                    response.putBoolean(WifiManagerResponse.SUCCESS, wifi.reconnect());
                    break;
                case WifiManagerRequest.REMOVE_NETWORK:
                    response.putBoolean(WifiManagerResponse.SUCCESS, wifi.removeNetwork(mRequest.integer()));
                    break;
                case WifiManagerRequest.SAVE_CONFIGURATION:
                    response.putBoolean(WifiManagerResponse.SUCCESS, wifi.saveConfiguration());
                    break;
                case WifiManagerRequest.SET_WIFI_ENABLED:
                    response.putBoolean(WifiManagerResponse.SUCCESS, wifi.setWifiEnabled(mRequest.bool()));
                    break;
                case WifiManagerRequest.START_SCAN:
                    response.putBoolean(WifiManagerResponse.SUCCESS, wifi.startScan());
                    break;
                case WifiManagerRequest.UPDATE_NETWORK:
                    response.putInt(WifiManagerResponse.NETWORK_ID, wifi.updateNetwork(mRequest.wifiConfiguration()));
                    break;
            }
            mActivity.sendBroadcast(new Intent(WifiManagerHandshakeReceiver.ACTION_FILTER)
                    .putExtra(Police.APPROVED, true)
                    .putExtra(Police.RESPONSE, response));
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            mActivity.sendBroadcast(new Intent(WifiManagerHandshakeReceiver.ACTION_FILTER)
                    .putExtra(Police.APPROVED, false));
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mActivity.finish();
    }
}
