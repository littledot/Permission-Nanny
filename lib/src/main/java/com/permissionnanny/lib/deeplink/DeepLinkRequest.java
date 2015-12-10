package com.permissionnanny.lib.deeplink;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringDef;
import com.permissionnanny.lib.C;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.NannyRequest;
import com.permissionnanny.lib.request.PermissionEvent;
import com.permissionnanny.lib.request.simple.SimpleListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <h1>Deep Linking into Permission Nanny</h1>
 * <p/>
 * Permission Nanny exposes APIs that allow clients to navigate their users directly into the app, aka "deep linking".
 * This can be useful when clients would like users to perform an action that can only be executed within Permission
 * Nanny, such as re-enabling permissions.
 * <p/>
 * To deep link into Permission Nanny, clients need to complete the deep link handshake flow.
 * <p/>
 * <h1>Deep Link Handshake Flow</h1>
 * <p/>
 * Clients start the flow by broadcasting an Intent to this receiver. The Intent <b>must</b> contain the usual request
 * metadata. In addition, the Intent <b>must</b> contain a {@link Nanny#DEEP_LINK_TARGET} String to indicate the target
 * Activity which the client would like to deep link into.
 * <pre>
 *     Client ---> Server
 *     {
 *         {@link Nanny#PROTOCOL_VERSION}*
 *         {@link Nanny#CLIENT_ADDRESS}
 *         {@link Nanny#ENTITY_BODY}* = {
 *             {@link Nanny#SENDER_IDENTITY}*
 *             {@link Nanny#DEEP_LINK_TARGET}*
 *         }
 *     }
 * </pre>
 * If the request is valid, the server will return a success response.
 * <pre>
 *     Client <--- Server
 *     {
 *         {@link Nanny#PROTOCOL_VERSION}*
 *         {@link Nanny#STATUS_CODE}* = {@link Nanny#SC_OK}
 *         {@link Nanny#CONNECTION}* = {@link Nanny#CLOSE}
 *         {@link Nanny#SERVER}* = {@link Nanny#AUTHORIZATION_SERVICE}
 *     }
 * </pre>
 * Otherwise, the server will return a failure response containing the error.
 * <pre>
 *     Client <--- Server
 *     {
 *         {@link Nanny#PROTOCOL_VERSION}*
 *         {@link Nanny#STATUS_CODE}*
 *         {@link Nanny#CONNECTION}* = {@link Nanny#CLOSE}
 *         {@link Nanny#SERVER}* = {@link Nanny#AUTHORIZATION_SERVICE}
 *         {@link Nanny#ENTITY_ERROR}*
 *     }
 * </pre>
 */
public class DeepLinkRequest extends NannyRequest {

    /**
     * Annotates ElementTypes that represent valid deep link targets.
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef(Nanny.MANAGE_APPLICATIONS_SETTINGS)
    public @interface Res {}

    private final String mDeepLinkTarget;

    /**
     * Create a new request.
     *
     * @param deepLinkTarget Target Activity to deep link into; must be a {@link Res}.
     */
    public DeepLinkRequest(@Res String deepLinkTarget) {
        mDeepLinkTarget = deepLinkTarget;
    }

    /**
     * Attaches a listener.
     *
     * @param listener Response receiver
     * @return itself
     */
    public DeepLinkRequest listener(SimpleListener listener) {
        return (DeepLinkRequest) addFilter(new PermissionEvent(listener));
    }

    /**
     * Starts the request.
     *
     * @param context Activity, Service, etc.
     */
    public void startRequest(Context context) {
        setPayload(newBroadcastIntent(context));
        super.startRequest(context);
    }

    protected Intent newBroadcastIntent(Context context) {
        NannyBundle.Builder builder = new NannyBundle.Builder()
                .sender(PendingIntent.getBroadcast(context, 0, C.EMPTY_INTENT, 0))
                .clientAddress(mClientAddr)
                .deepLinkTarget(mDeepLinkTarget);
        return new Intent()
                .setClassName(Nanny.getServerAppId(), Nanny.CLIENT_DEEP_LINK_RECEIVER)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .putExtras(builder.build());
    }
}
