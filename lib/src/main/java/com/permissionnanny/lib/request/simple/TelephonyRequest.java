package com.permissionnanny.lib.request.simple;

import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.RequestParams;

/**
 * Factory that create {@link android.telephony.TelephonyManager} requests.
 */
public class TelephonyRequest extends SimpleRequest {

    @PPP public static final String GET_ALL_CELL_INFO = "getAllCellInfo";
    @PPP public static final String GET_DEVICE_ID = "getDeviceId";
    @PPP public static final String GET_DEVICE_SOFTWARE_VERSION = "getDeviceSoftwareVersion";
    @PPP public static final String GET_GROUP_ID_LEVEL_1 = "getGroupIdLevel1";
    @PPP public static final String GET_LINE_1_NUMBER = "getLine1Number";
    @PPP public static final String GET_NEIGHBORING_CELL_INFO = "getNeighboringCellInfo";
    @PPP public static final String GET_SIM_SERIAL_NUMBER = "getSimSerialNumber";
    @PPP public static final String GET_SUBSCRIBER_ID = "getSubscriberId";
    @PPP public static final String GET_VOICE_MAIL_ALPHA_TAG = "getVoiceMailAlphaTag";
    @PPP public static final String GET_VOICE_MAIL_NUMBER = "getVoiceMailNumber";
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

    public TelephonyRequest(RequestParams params) {
        super(params);
    }
}
