package com.sdchang.permissionpolice.lib.request.telephony;

import com.sdchang.permissionpolice.lib.request.PermissionRequest;
import com.sdchang.permissionpolice.lib.request.RequestParams;

/**
 *
 */
public class TelephonyRequest extends PermissionRequest {

    public TelephonyRequest(RequestParams params) {
        super(params);
    }

    static RequestParams.Builder newBuilder() {
        return RequestParams.newBuilder();
    }

    @Override
    public int getRequestType() {
        return TELEPHONY_REQUEST;
    }

    public static final String GET_ALL_CELL_INFO = "getAllCellInfo";
    public static final String GET_DEVICE_ID = "getDeviceId";
    public static final String GET_DEVICE_SOFTWARE_VERSION = "getDeviceSoftwareVersion";
    public static final String GET_GROUP_ID_LEVEL_1 = "getGroupIdLevel1";
    public static final String GET_LINE_1_NUMBER = "getLine1Number";
    public static final String GET_NEIGHBORING_CELL_INFO = "getNeighboringCellInfo";
    public static final String GET_SIM_SERIAL_NUMBER = "getSimSerialNumber";
    public static final String GET_SUBSCRIBER_ID = "getSubscriberId";
    public static final String GET_VOICE_MAIL_ALPHA_TAG = "getVoiceMailAlphaTag";
    public static final String GET_VOICE_MAIL_NUMBER = "getVoiceMailNumber";
    public static final int ICC_CLOSE_LOGICAL_CHANNEL = 12;
    public static final int ICC_EXCHANGE_SIM_IO = 13;
    public static final int ICC_OPEN_LOGICAL_CHANNEL = 14;
    public static final int ICC_TRANSMIT_APDU_BASIC_CHANNEL = 15;
    public static final int ICC_TRANSMIT_APDU_LOGICAL_CHANNEL = 16;
    public static final int SEND_ENVELOPE_WITH_STATUS = 17;

    public static TelephonyRequest getAllCellInfo() {
        RequestParams params = newBuilder().opCode(GET_ALL_CELL_INFO).build();
        return new TelephonyRequest(params);
    }

//    public static TelephonyManagerRequest getCellLocation() {
//        return newBuilder().opCode(GET_CELL_LOCATION).build();
//    }

    public static TelephonyRequest getDeviceId() {
        RequestParams params = newBuilder().opCode(GET_DEVICE_ID).build();
        return new TelephonyRequest(params);
    }

    public static TelephonyRequest getDeviceSoftwareVersion() {
        RequestParams params = newBuilder().opCode(GET_DEVICE_SOFTWARE_VERSION).build();
        return new TelephonyRequest(params);
    }

    public static TelephonyRequest getGroupIdLevel1() {
        RequestParams params = newBuilder().opCode(GET_GROUP_ID_LEVEL_1).build();
        return new TelephonyRequest(params);
    }

    public static TelephonyRequest getLine1Number() {
        RequestParams params = newBuilder().opCode(GET_LINE_1_NUMBER).build();
        return new TelephonyRequest(params);
    }

    public static TelephonyRequest getNeighboringCellInfo() {
        RequestParams params = newBuilder().opCode(GET_NEIGHBORING_CELL_INFO).build();
        return new TelephonyRequest(params);
    }

    public static TelephonyRequest getSimSerialNumber() {
        RequestParams params = newBuilder().opCode(GET_SIM_SERIAL_NUMBER).build();
        return new TelephonyRequest(params);
    }

    public static TelephonyRequest getSubscriberId() {
        RequestParams params = newBuilder().opCode(GET_SUBSCRIBER_ID).build();
        return new TelephonyRequest(params);
    }

    public static TelephonyRequest getVoiceMailAlphaTag() {
        RequestParams params = newBuilder().opCode(GET_VOICE_MAIL_ALPHA_TAG).build();
        return new TelephonyRequest(params);
    }

    public static TelephonyRequest getVoiceMailNumber() {
        RequestParams params = newBuilder().opCode(GET_VOICE_MAIL_NUMBER).build();
        return new TelephonyRequest(params);
    }
}
