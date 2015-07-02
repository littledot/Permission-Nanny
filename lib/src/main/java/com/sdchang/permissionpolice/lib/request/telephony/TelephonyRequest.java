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
        RequestParams p = new RequestParams();
        p.opCode = GET_ALL_CELL_INFO;
        return new TelephonyRequest(p);
    }

//    public static TelephonyManagerRequest getCellLocation() {
//        return newBuilder().opCode(GET_CELL_LOCATION).build();
//    }

    public static TelephonyRequest getDeviceId() {
        RequestParams p = new RequestParams();
        p.opCode = GET_DEVICE_ID;
        return new TelephonyRequest(p);
    }

    public static TelephonyRequest getDeviceSoftwareVersion() {
        RequestParams p = new RequestParams();
        p.opCode = GET_DEVICE_SOFTWARE_VERSION;
        return new TelephonyRequest(p);
    }

    public static TelephonyRequest getGroupIdLevel1() {
        RequestParams p = new RequestParams();
        p.opCode = GET_GROUP_ID_LEVEL_1;
        return new TelephonyRequest(p);
    }

    public static TelephonyRequest getLine1Number() {
        RequestParams p = new RequestParams();
        p.opCode = GET_LINE_1_NUMBER;
        return new TelephonyRequest(p);
    }

    public static TelephonyRequest getNeighboringCellInfo() {
        RequestParams p = new RequestParams();
        p.opCode = GET_NEIGHBORING_CELL_INFO;
        return new TelephonyRequest(p);
    }

    public static TelephonyRequest getSimSerialNumber() {
        RequestParams p = new RequestParams();
        p.opCode = GET_SIM_SERIAL_NUMBER;
        return new TelephonyRequest(p);
    }

    public static TelephonyRequest getSubscriberId() {
        RequestParams p = new RequestParams();
        p.opCode = GET_SUBSCRIBER_ID;
        return new TelephonyRequest(p);
    }

    public static TelephonyRequest getVoiceMailAlphaTag() {
        RequestParams p = new RequestParams();
        p.opCode = GET_VOICE_MAIL_ALPHA_TAG;
        return new TelephonyRequest(p);
    }

    public static TelephonyRequest getVoiceMailNumber() {
        RequestParams p = new RequestParams();
        p.opCode = GET_VOICE_MAIL_NUMBER;
        return new TelephonyRequest(p);
    }
}
