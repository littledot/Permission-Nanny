package com.sdchang.permissionpolice.telephony;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.sdchang.permissionpolice.BaseDialogBuilder;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.lib.request.telephony.TelephonyManagerRequest;

import java.util.ArrayList;

/**
 *
 */
public class TelephonyRequestDialogBuilder extends BaseDialogBuilder {

    private final TelephonyManagerRequest mRequest;

    public TelephonyRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
        mRequest = args.getParcelable(BaseRequest.REQUEST_BODY);
    }

    @Override
    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int string = 0;
        switch (mRequest.opCode()) {
            case TelephonyManagerRequest.GET_ALL_CELL_INFO:
                string = R.string.dialogTitle_telephonyGetAllCellInfo;
                break;
            case TelephonyManagerRequest.GET_DEVICE_ID:
                string = R.string.dialogTitle_telephonyGetDeviceId;
                break;
            case TelephonyManagerRequest.GET_DEVICE_SOFTWARE_VERSION:
                string = R.string.dialogTitle_telephonyGetDeviceSoftwareVersion;
                break;
            case TelephonyManagerRequest.GET_GROUP_ID_LEVEL_1:
                string = R.string.dialogTitle_telephonyGetGroupIdLevel1;
                break;
            case TelephonyManagerRequest.GET_LINE_1_NUMBER:
                string = R.string.dialogTitle_telephonyGetLine1Number;
                break;
            case TelephonyManagerRequest.GET_NEIGHBORING_CELL_INFO:
                string = R.string.dialogTitle_telephonyGetNeighboringCellInfo;
                break;
            case TelephonyManagerRequest.GET_SIM_SERIAL_NUMBER:
                string = R.string.dialogTitle_telephonyGetSimSerialNumber;
                break;
            case TelephonyManagerRequest.GET_SUBSCRIBER_ID:
                string = R.string.dialogTitle_telephonyGetSubscriberId;
                break;
            case TelephonyManagerRequest.GET_VOICE_MAIL_ALPHA_TAG:
                string = R.string.dialogTitle_telephonyGetVoiceMailAlphaTag;
                break;
            case TelephonyManagerRequest.GET_VOICE_MAIL_NUMBER:
                string = R.string.dialogTitle_telephonyGetVoiceMailNumber;
                break;
        }
        return boldAppLabel.append(mActivity.getText(string));
    }

    @Override
    protected void onAllowRequest() {
        super.onAllowRequest();
        TelephonyManager tele = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
        Bundle response = new Bundle();
        switch (mRequest.opCode()) {
            case TelephonyManagerRequest.GET_ALL_CELL_INFO:
                if (VERSION.SDK_INT >= 17) {
                    response.putParcelableArrayList(mRequest.opCode(), new ArrayList<>(tele.getAllCellInfo()));
                }
                break;
            case TelephonyManagerRequest.GET_DEVICE_ID:
                response.putString(mRequest.opCode(), tele.getDeviceId());
                break;
            case TelephonyManagerRequest.GET_DEVICE_SOFTWARE_VERSION:
                response.putString(mRequest.opCode(), tele.getDeviceSoftwareVersion());
                break;
            case TelephonyManagerRequest.GET_GROUP_ID_LEVEL_1:
                if (VERSION.SDK_INT >= 18) {
                    response.putString(mRequest.opCode(), tele.getGroupIdLevel1());
                }
                break;
            case TelephonyManagerRequest.GET_LINE_1_NUMBER:
                response.putString(mRequest.opCode(), tele.getLine1Number());
                break;
            case TelephonyManagerRequest.GET_NEIGHBORING_CELL_INFO:
                response.putParcelableArrayList(mRequest.opCode(), new ArrayList<>(tele.getNeighboringCellInfo()));
                break;
            case TelephonyManagerRequest.GET_SIM_SERIAL_NUMBER:
                response.putString(mRequest.opCode(), tele.getSimSerialNumber());
                break;
            case TelephonyManagerRequest.GET_SUBSCRIBER_ID:
                response.putString(mRequest.opCode(), tele.getSubscriberId());
                break;
            case TelephonyManagerRequest.GET_VOICE_MAIL_ALPHA_TAG:
                response.putString(mRequest.opCode(), tele.getVoiceMailAlphaTag());
                break;
            case TelephonyManagerRequest.GET_VOICE_MAIL_NUMBER:
                response.putString(mRequest.opCode(), tele.getVoiceMailNumber());
                break;
        }
        mActivity.sendBroadcast(new Intent(TelephonyManagerRequest.TELEPHONY_INTENT_FILTER)
                .putExtra(Police.APPROVED, true)
                .putExtra(Police.RESPONSE, response));
    }

    @Override
    protected void onDenyRequest() {
        super.onDenyRequest();
        mActivity.sendBroadcast(new Intent(TelephonyManagerRequest.TELEPHONY_INTENT_FILTER)
                .putExtra(Police.APPROVED, false));
    }
}
