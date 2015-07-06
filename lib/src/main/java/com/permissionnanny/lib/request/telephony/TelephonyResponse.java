package com.permissionnanny.lib.request.telephony;

import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import com.permissionnanny.lib.request.BaseResponse;

import java.util.List;

/**
 *
 */
public class TelephonyResponse extends BaseResponse {

    public TelephonyResponse(Bundle response) {
        super(response);
    }

    public List<CellInfo> allCellInfo() {
        return mResponse.getParcelableArrayList(TelephonyRequest.GET_ALL_CELL_INFO);
    }

    public String deviceId() {
        return mResponse.getString(TelephonyRequest.GET_DEVICE_ID);
    }

    public String deviceSoftwareVersion() {
        return mResponse.getString(TelephonyRequest.GET_DEVICE_SOFTWARE_VERSION);
    }

    public String groupIdLevel1() {
        return mResponse.getString(TelephonyRequest.GET_GROUP_ID_LEVEL_1);
    }

    public String line1Number() {
        return mResponse.getString(TelephonyRequest.GET_LINE_1_NUMBER);
    }

    public List<NeighboringCellInfo> neighboringCellInfo() {
        return mResponse.getParcelableArrayList(TelephonyRequest.GET_NEIGHBORING_CELL_INFO);
    }

    public String simSerialNumber() {
        return mResponse.getString(TelephonyRequest.GET_SIM_SERIAL_NUMBER);
    }

    public String subscriberId() {
        return mResponse.getString(TelephonyRequest.GET_SUBSCRIBER_ID);
    }

    public String voiceMailAlphaTag() {
        return mResponse.getString(TelephonyRequest.GET_VOICE_MAIL_ALPHA_TAG);
    }

    public String voiceMailNumber() {
        return mResponse.getString(TelephonyRequest.GET_VOICE_MAIL_NUMBER);
    }
}
