package com.sdchang.permissionpolice.lib.request.telephony;

import android.support.annotation.StringDef;
import auto.parcel.AutoParcel;
import com.sdchang.permissionpolice.lib.request.BaseRequest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
@AutoParcel
public abstract class TelephonyManagerRequest extends BaseRequest {

    static Builder newBuilder() {
        return new AutoParcel_TelephonyManagerRequest.Builder();
    }

    @AutoParcel.Builder
    public static abstract class Builder {
        public abstract Builder opCode(@Op String op);

        public abstract TelephonyManagerRequest build();
    }

    @Override
    public int getRequestType() {
        return TELEPHONY_REQUEST;
    }

    public abstract String opCode();

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({GET_ALL_CELL_INFO, GET_DEVICE_ID, GET_DEVICE_SOFTWARE_VERSION, GET_GROUP_ID_LEVEL_1,
            GET_LINE_1_NUMBER, GET_NEIGHBORING_CELL_INFO, GET_SIM_SERIAL_NUMBER, GET_SUBSCRIBER_ID,
            GET_VOICE_MAIL_ALPHA_TAG, GET_VOICE_MAIL_NUMBER})
    public @interface Op {}

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

    public static TelephonyManagerRequest newGetAllCellInfoRequest() {
        return newBuilder().opCode(GET_ALL_CELL_INFO).build();
    }

//    public static TelephonyManagerRequest newGetCellLocationRequest() {
//        return newBuilder().opCode(GET_CELL_LOCATION).build();
//    }

    public static TelephonyManagerRequest newGetDeviceIdRequest() {
        return newBuilder().opCode(GET_DEVICE_ID).build();
    }

    public static TelephonyManagerRequest newGetDeviceSoftwareVersion() {
        return newBuilder().opCode(GET_DEVICE_SOFTWARE_VERSION).build();
    }

    public static TelephonyManagerRequest newGetGroupIdLevel1Request() {
        return newBuilder().opCode(GET_GROUP_ID_LEVEL_1).build();
    }

    public static TelephonyManagerRequest newGetLine1NumberRequest() {
        return newBuilder().opCode(GET_LINE_1_NUMBER).build();
    }

    public static TelephonyManagerRequest newGetNeighboringCellInfoRequest() {
        return newBuilder().opCode(GET_NEIGHBORING_CELL_INFO).build();
    }

    public static TelephonyManagerRequest newGetSimSerialNumberRequest() {
        return newBuilder().opCode(GET_SIM_SERIAL_NUMBER).build();
    }

    public static TelephonyManagerRequest newGetSubscriberIdRequest() {
        return newBuilder().opCode(GET_SUBSCRIBER_ID).build();
    }

    public static TelephonyManagerRequest newGetVoiceMailAlphaTagRequest() {
        return newBuilder().opCode(GET_VOICE_MAIL_ALPHA_TAG).build();
    }

    public static TelephonyManagerRequest newGetVoiceMailNumberRequest() {
        return newBuilder().opCode(GET_VOICE_MAIL_NUMBER).build();
    }
}
