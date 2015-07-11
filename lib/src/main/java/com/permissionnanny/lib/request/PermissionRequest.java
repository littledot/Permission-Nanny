package com.permissionnanny.lib.request;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.request.simple.SimpleListener;
import com.permissionnanny.lib.C;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.content.ContentListener;
import com.permissionnanny.lib.request.content.ContentRequest;
import com.permissionnanny.lib.request.simple.LocationRequest;
import com.permissionnanny.lib.request.simple.SimpleRequest;
import com.permissionnanny.lib.request.simple.WifiRequest;

import java.security.SecureRandom;

/**
 * A request to Permission Nanny to access features and components that are protected by Android permissions on your
 * behalf.
 * <p/>
 * <h1>Request Types</h1>
 * <p/>
 * Requests can be classified into 2 categories - {@link SimpleRequest} and {@link ContentRequest}.
 * <p/>
 * <h1>{@linkplain SimpleRequest}s</h1>
 * <p/>
 * Requests handled by dedicated Android managers - such as {@link android.telephony.TelephonyManager} and {@link
 * android.telephony.SmsManager} - are considered simple requests.
 * <p/>
 * <h2>How to Create a {@linkplain SimpleRequest}</h2>
 * <p/>
 * Use one of the subclass factories - such as {@link ContentRequest} and {@link LocationRequest} - to create requests.
 * To minimize confusion, each request factory closely mimics Android's managers. eg: {@link LocationRequest} mimics
 * {@link android.location.LocationManager}, {@link WifiRequest} mimics {@link android.net.wifi.WifiManager}. The
 * creator methods exposed in request factories also mirror those in Android managers.
 * <p/>
 * <h2>How to Listen for a Response for a {@linkplain SimpleRequest}</h2>
 * <p/>
 * After creating a request, attach a listener to it via {@link SimpleRequest#listener(SimpleListener)} to receive
 * results for a request. No matter if the user allows or denies your request, Permission Nanny will return the results
 * in a Bundle. The response Bundle consists of 2 components: metadata entries describing the response such as {@link
 * Nanny#PROTOCOL_VERSION} and {@link Nanny#STATUS_CODE}; and entity data which contains the result payload. Entity data
 * is structured as a nested Bundle within the response Bundle indexed at {@link Nanny#ENTITY_BODY}. If Permission Nanny
 * encountered a failure while executing your request, a nested {@link NannyException} indexed at {@link
 * Nanny#ENTITY_ERROR} will provide you details of what went wrong.
 * <p/>
 * For one-shot requests, the result payload is indexed using the method name within the entity data Bundle. For
 * example, if you were to make a {@link WifiRequest#getConnectionInfo()} request, the result of that request would be
 * indexed at {@link WifiRequest#GET_CONNECTION_INFO} within a Bundle indexed at {@link Nanny#ENTITY_BODY} of the
 * response Bundle.
 * <pre>
 * <code>
 *  WifiRequest request = WifiRequest.getConnectionInfo().listener(new BundleListener() {
 *      public void onResponse(Bundle response) {
 *          Bundle entity = response.getBundle(Nanny.ENTITY_BODY);
 *          if (Nanny.SC_OK != response.getInt(Nanny.STATUS_CODE)) {
 *              NannyException error = (NannyException) entity.getSerializable(Nanny.ENTITY_ERROR);
 *              return;
 *          }
 *          WifiInfo info = entity.getParcelable(WifiRequest.GET_CONNECTION_INFO);
 *      }
 *  }).startRequest(context, "Trust me");
 * </code>
 * </pre>
 * For ongoing requests that require you to provide an Android listener - such as {@link
 * LocationRequest#requestLocationUpdates(long, float, Criteria, LocationListener, Looper)}, the response Bundle will
 * only contain metadata and no entity data because the result payload is delivered directly to the listener interface
 * provided to the request.
 * <p/>
 * <h1>{@linkplain ContentRequest}s</h1>
 * <p/>
 * Requests handled by {@link android.content.ContentProvider}s and {@link android.content.ContentResolver}s are
 * considered content requests.
 * <p/>
 * <h2>How to Create a {@linkplain ContentRequest}</h2>
 * <p/>
 * Use a {@link ContentRequest.Builder} to craft your query. The parameters the builder accepts closely resembles to
 * {@link android.content.ContentResolver} queries. As of now, only single queries such as .query(), .insert(),
 * .update() and .delete() are supported; .applyBatch() and .bulkInsert() are future work.
 * <p/>
 * <h2>How to Listen for a Response for a {@linkplain ContentRequest}</h2>
 * <p/>
 * Similar to {@link SimpleRequest}s, attach a listener via {@link ContentRequest#listener(ContentListener)} to receive
 * results for a request. Permission Nanny will return response metadata in a Bundle as described above. Depending on
 * the type of the content request, result payload will be delivered through the appropriate parameter.
 * <pre>
 * <code>
 *  ContentRequest request = ContentRequest.newBuilder().select()
 *          .uri(ContactsContract.Contacts.CONTENT_URI)
 *          .sortOrder(BaseColumns._ID + " limit 5")
 *          .build();
 *  request.listener(new ContentListener() {
 *      public void onResponse(@NonNull Bundle response, Cursor data, Uri inserted, int rowsUpdated, int rowsDeleted) {
 *          Bundle entity = response.getBundle(Nanny.ENTITY_BODY);
 *          if (Nanny.SC_OK != response.getInt(Nanny.STATUS_CODE)) {
 *              NannyException error = (NannyException) entity.getSerializable(Nanny.ENTITY_ERROR);
 *              return;
 *          }
 *          String[] columns = data.getColumnNames();
 *      }
 *  }).startRequest(context, "Trust me");
 * </code>
 * </pre>
 * <p/>
 * <h1>How to Start a Request</h1>
 * <p/>
 * Send the request to Permission Nanny via {@link #startRequest(Context, String)}.
 */
public abstract class PermissionRequest {

    @PPP public static final int SIMPLE_REQUEST = 1;
    @PPP public static final int CONTENT_REQUEST = 2;

    protected PermissionReceiver mReceiver;
    protected RequestParams mParams;

    public PermissionRequest(RequestParams params) {
        mParams = params;
    }

    /**
     * Start the request.
     *
     * @param context Activity, Service, etc.
     * @param reason  Explain to the user why you need to access the feature. This is displayed to the user in a dialog
     *                when Permission Nanny needs to ask the user for authorization.
     */
    public void startRequest(@NonNull Context context, @Nullable String reason) {
        String clientId = null;
        if (mReceiver != null) {
            clientId = Long.toString(new SecureRandom().nextLong());
            context.registerReceiver(mReceiver, new IntentFilter(clientId));
        }
        context.sendBroadcast(newBroadcastIntent(context, reason, clientId));
    }

    protected abstract int getRequestType();

    protected PermissionRequest addFilter(Event event) {
        if (mReceiver == null) {
            mReceiver = new PermissionReceiver();
        }
        mReceiver.addFilter(event);
        return this;
    }

    private Intent newBroadcastIntent(Context context, String reason, @Nullable String clientFilter) {
        Intent intent = new Intent()
                .setClassName(Nanny.SERVER_APP_ID, Nanny.CLIENT_REQUEST_RECEIVER)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        return decorateIntent(intent, context, reason, clientFilter);
    }

    private Intent newBindServiceIntent() {
        return new Intent().setClassName("com.sdchang.permissionpolice",
                "com.sdchang.permissionpolice.ExternalRequestService");
    }

    private Intent decorateIntent(Intent intent, Context context, String reason, @Nullable String clientFilter) {
        Bundle entity = new Bundle();
        entity.putParcelable(Nanny.SENDER_IDENTITY, PendingIntent.getBroadcast(context, 0, C.EMPTY_INTENT, 0));
        entity.putInt(Nanny.TYPE, getRequestType());
        entity.putParcelable(Nanny.REQUEST_PARAMS, mParams);
        entity.putString(Nanny.REQUEST_REASON, reason);

        if (clientFilter != null) {
            intent.putExtra(Nanny.CLIENT_ADDRESS, clientFilter);
        }
        return intent.putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.ENTITY_BODY, entity);
    }
}
