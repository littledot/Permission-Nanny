package com.sdchang.permissionpolice.lib.request.sms;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.sdchang.permissionpolice.lib.request.PermissionRequest;
import com.sdchang.permissionpolice.lib.request.RequestParams;

import java.util.ArrayList;

/**
 *
 */
public class SmsRequest extends PermissionRequest {

    public SmsRequest(RequestParams params) {
        super(params);
    }

    @Override
    public int getRequestType() {
        return SMS_REQUEST;
    }

    public static final String SEND_DATA_MESSAGE = "sendDataMessage";
    public static final String SEND_MULTIMEDIA_MESSAGE = "sendMultimediaMessage";
    public static final String SEND_MULTIPART_TEXT_MESSAGE = "sendMultipartTextMessage";
    public static final String SEND_TEXT_MESSAGE = "sendTextMessage";

    /**
     * Send a data based SMS to a specific application port. <p/> <p class="note"><strong>Note:</strong> Using this
     * method requires that your app has the {@link android.Manifest.permission#SEND_SMS} permission.</p>
     *
     * @param destinationAddress the address to send the message to
     * @param scAddress          is the service center address or null to use the current default SMSC
     * @param destinationPort    the port to deliver the message to
     * @param data               the body of the message to send
     * @param sentIntent         if not NULL this <code>PendingIntent</code> is broadcast when the message is
     *                           successfully sent, or failed. The result code will be <code>Activity.RESULT_OK</code>
     *                           for success, or one of these errors:<br> <code>RESULT_ERROR_GENERIC_FAILURE</code><br>
     *                           <code>RESULT_ERROR_RADIO_OFF</code><br> <code>RESULT_ERROR_NULL_PDU</code><br> For
     *                           <code>RESULT_ERROR_GENERIC_FAILURE</code> the sentIntent may include the extra
     *                           "errorCode" containing a radio technology specific value, generally only useful for
     *                           troubleshooting.<br> The per-application based SMS control checks sentIntent. If
     *                           sentIntent is NULL the caller will be checked against all unknown applications, which
     *                           cause smaller number of SMS to be sent in checking period.
     * @param deliveryIntent     if not NULL this <code>PendingIntent</code> is broadcast when the message is delivered
     *                           to the recipient.  The raw pdu of the status report is in the extended data ("pdu").
     * @throws IllegalArgumentException if destinationAddress or data are empty
     */
    public static SmsRequest sendDataMessage(String destinationAddress,
                                             String scAddress,
                                             short destinationPort,
                                             byte[] data,
                                             PendingIntent sentIntent,
                                             PendingIntent deliveryIntent) {
        RequestParams p = new RequestParams();
        p.opCode = SEND_DATA_MESSAGE;
        p.string0 = destinationAddress;
        p.string1 = scAddress;
        p.int0 = destinationPort;
        p.byteArray0 = data;
        p.pendingIntent0 = sentIntent;
        p.pendingIntent1 = deliveryIntent;
        return new SmsRequest(p);
    }

    /**
     * Send an MMS message
     *
     * @param context         application context
     * @param contentUri      the content Uri from which the message pdu will be read
     * @param locationUrl     the optional location url where message should be sent to
     * @param configOverrides the carrier-specific messaging configuration values to override for sending the message.
     * @param sentIntent      if not NULL this <code>PendingIntent</code> is broadcast when the message is successfully
     *                        sent, or failed
     * @throws IllegalArgumentException if contentUri is empty
     */
    @TargetApi(21)
    public static SmsRequest sendMultimediaMessage(Context context,
                                                   Uri contentUri,
                                                   String locationUrl,
                                                   Bundle configOverrides,
                                                   PendingIntent sentIntent) {
        RequestParams p = new RequestParams();
        p.opCode = SEND_MULTIMEDIA_MESSAGE;
        p.uri0 = contentUri;
        p.string0 = locationUrl;
        p.bundle0 = configOverrides;
        p.pendingIntent0 = sentIntent;
        return new SmsRequest(p);
    }

    /**
     * Send a multi-part text based SMS.  The callee should have already divided the message into correctly sized parts
     * by calling <code>divideMessage</code>. <p/> <p class="note"><strong>Note:</strong> Using this method requires
     * that your app has the {@link android.Manifest.permission#SEND_SMS} permission.</p> <p/> <p
     * class="note"><strong>Note:</strong> Beginning with Android 4.4 (API level 19), if <em>and only if</em> an app is
     * not selected as the default SMS app, the system automatically writes messages sent using this method to the SMS
     * Provider (the default SMS app is always responsible for writing its sent messages to the SMS Provider). For
     * information about how to behave as the default SMS app, see {@link android.provider.Telephony}.</p>
     *
     * @param destinationAddress the address to send the message to
     * @param scAddress          is the service center address or null to use the current default SMSC
     * @param parts              an <code>ArrayList</code> of strings that, in order, comprise the original message
     * @param sentIntents        if not null, an <code>ArrayList</code> of <code>PendingIntent</code>s (one for each
     *                           message part) that is broadcast when the corresponding message part has been sent. The
     *                           result code will be <code>Activity.RESULT_OK</code> for success, or one of these
     *                           errors:<br> <code>RESULT_ERROR_GENERIC_FAILURE</code><br>
     *                               <code>RESULT_ERROR_RADIO_OFF</code><br>
     *                           <code>RESULT_ERROR_NULL_PDU</code><br> For <code>RESULT_ERROR_GENERIC_FAILURE</code>
     *                           each sentIntent may include the extra "errorCode" containing a radio technology
     *                           specific value, generally only useful for troubleshooting.<br> The per-application
     *                           based SMS control checks sentIntent. If sentIntent is NULL the caller will be checked
     *                           against all unknown applications, which cause smaller number of SMS to be sent in
     *                           checking period.
     * @param deliveryIntents    if not null, an <code>ArrayList</code> of <code>PendingIntent</code>s (one for each
     *                           message part) that is broadcast when the corresponding message part has been delivered
     *                           to the recipient.  The raw pdu of the status report is in the extended data ("pdu").
     * @throws IllegalArgumentException if destinationAddress or data are empty
     */
    public static SmsRequest sendMultipartTextMessage(String destinationAddress,
                                                      String scAddress,
                                                      ArrayList<String> parts,
                                                      ArrayList<PendingIntent> sentIntents,
                                                      ArrayList<PendingIntent> deliveryIntents) {
        RequestParams p = new RequestParams();
        p.opCode = SEND_MULTIPART_TEXT_MESSAGE;
        p.string0 = destinationAddress;
        p.string1 = scAddress;
        p.arrayListOfStrings0 = parts;
        p.arrayListOfPendingIntents0 = sentIntents;
        p.arrayListOfPendingIntents1 = deliveryIntents;
        return new SmsRequest(p);
    }

    /**
     * Send a text based SMS. <p/> <p class="note"><strong>Note:</strong> Using this method requires that your app has
     * the {@link android.Manifest.permission#SEND_SMS} permission.</p> <p/> <p class="note"><strong>Note:</strong>
     * Beginning with Android 4.4 (API level 19), if <em>and only if</em> an app is not selected as the default SMS app,
     * the system automatically writes messages sent using this method to the SMS Provider (the default SMS app is
     * always responsible for writing its sent messages to the SMS Provider). For information about how to behave as the
     * default SMS app, see {@link android.provider.Telephony}.</p>
     *
     * @param destinationAddress the address to send the message to
     * @param scAddress          is the service center address or null to use the current default SMSC
     * @param text               the body of the message to send
     * @param sentIntent         if not NULL this <code>PendingIntent</code> is broadcast when the message is
     *                           successfully sent, or failed. The result code will be <code>Activity.RESULT_OK</code>
     *                           for success, or one of these errors:<br> <code>RESULT_ERROR_GENERIC_FAILURE</code><br>
     *                           <code>RESULT_ERROR_RADIO_OFF</code><br> <code>RESULT_ERROR_NULL_PDU</code><br> For
     *                           <code>RESULT_ERROR_GENERIC_FAILURE</code> the sentIntent may include the extra
     *                           "errorCode" containing a radio technology specific value, generally only useful for
     *                           troubleshooting.<br> The per-application based SMS control checks sentIntent. If
     *                           sentIntent is NULL the caller will be checked against all unknown applications, which
     *                           cause smaller number of SMS to be sent in checking period.
     * @param deliveryIntent     if not NULL this <code>PendingIntent</code> is broadcast when the message is delivered
     *                           to the recipient.  The raw pdu of the status report is in the extended data ("pdu").
     * @throws IllegalArgumentException if destinationAddress or text are empty
     */
    public static SmsRequest sendTextMessage(String destinationAddress,
                                             String scAddress,
                                             String text,
                                             PendingIntent sentIntent,
                                             PendingIntent deliveryIntent) {
        RequestParams p = new RequestParams();
        p.opCode = SEND_TEXT_MESSAGE;
        p.string0 = destinationAddress;
        p.string1 = scAddress;
        p.string2 = text;
        p.pendingIntent0 = sentIntent;
        p.pendingIntent1 = deliveryIntent;
        return new SmsRequest(p);
    }
}
