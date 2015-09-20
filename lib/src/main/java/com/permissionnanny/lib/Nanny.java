package com.permissionnanny.lib;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.content.ContentRequest;
import com.permissionnanny.lib.request.simple.LocationRequest;
import com.permissionnanny.lib.request.simple.WifiRequest;

/**
 * <h1>Permission Nanny</h1>
 * <p/>
 * Permission Nanny is an application that can access resources which are protected by permissions on your behalf, so
 * that your application does not need to declare permission usage in your AndroidManifest.xml. With Permission Nanny,
 * it is possible for your application to not require <b><i>any</i></b> permissions at all, yet still be able to access
 * permission-protected resources.
 * <p/>
 * From a high-level perspective, Permission Nanny acts as a proxy server between client applications and the Android
 * operating system. When a client needs to access a resource that is protected by Android permissions, the client will
 * send a request to Permission Nanny. Permission Nanny will then show a dialog to the user, asking the user for
 * authorization to grant the client access to the resource. If the user allows the request, Permission Nanny will
 * access the resource and return results to the client; if the user denies the request, Permission Nanny will simply
 * return an error response.
 * <p/>
 * <h1>Permission Police Protocol</h1>
 * <p/>
 * Clients communicate with Permission Nanny using broadcast Intents following the Permission Police Protocol (PPP). PPP
 * is heavily inspired by HTTP with a few minor tweaks, borrowing attributes such as status codes, headers and entity.
 * There are 3 handshake flows depending on the type of the request - One-shot, Ongoing and Content Query.
 * <p/>
 * <i>If you are not interested in low-level details of how the handshakes are implemented and would like to know how to
 * make requests and listen for responses using the SDK or integrating your application with Permission Nanny, please
 * feel free to skip the rest and proceed to {@link PermissionRequest}</i>.
 * <p/>
 * <h2>One-shot Request Handshake Flow</h2>
 * <p/>
 * A request that accesses a resource at a single point in time - such as {@link WifiRequest#getConnectionInfo()} - is
 * considered a one-shot request.
 * <p/>
 * Clients start the flow by broadcasting an Intent to Permission Nanny's ClientRequestReceiver, a BroadcastReceiver
 * that validates incoming Intents. The Intent <b>must</b> contain a {@link #PROTOCOL_VERSION} String and an {@link
 * #ENTITY_BODY} Bundle containing valid request metadata. Request metadata <b>must</b> contain a {@link
 * #SENDER_IDENTITY} so that Permission Nanny knows who sent the request, {@link #REQUEST_PARAMS} to know what resource
 * to access. Request metadata <b>may</b> contain a {@link #REQUEST_REASON} String to explain to the user why the client
 * needs access to the resource. The request <b>may</b> also contain a {@link #CLIENT_ADDRESS} String to tell Permission
 * Nanny where to deliver the response. If {@link #CLIENT_ADDRESS} is empty, Permission Nanny will not return a response
 * and you will not know the status of your request, nor be able to retrieve the requested resources.
 * <pre>
 *  {
 *      {@link #PROTOCOL_VERSION}*
 *      {@link #CLIENT_ADDRESS}
 *      {@link #ENTITY_BODY}* = {
 *          {@link #SENDER_IDENTITY}*
 *          {@link #REQUEST_PARAMS}*
 *          {@link #REQUEST_REASON}
 *      }
 *  }
 * </pre>
 * Permission Nanny will return an authorization response. The response Intent <b>must</b> contain a {@link
 * #PROTOCOL_VERSION} String, a {@link #STATUS_CODE} integer similar to HTTP, a {@link #CONNECTION} String fixed to
 * {@link #CLOSE} and a {@link #SERVER} String fixed to {@link #AUTHORIZATION_SERVICE}. If {@link #STATUS_CODE}
 * indicates success, the response <b>must</b> contain an {@link #ENTITY_BODY} Bundle containing the requested resource;
 * otherwise, the response <b>must</b> contain an {@link #ENTITY_ERROR}.
 * <pre>
 *  {
 *      {@link #PROTOCOL_VERSION}*
 *      {@link #STATUS_CODE}*
 *      {@link #CONNECTION}* = {@link #CLOSE}
 *      {@link #SERVER}* = {@link #AUTHORIZATION_SERVICE}
 *      {@link #ENTITY_BODY}
 *      {@link #ENTITY_ERROR}
 *  }
 * </pre>
 * <h4>Privacy and {@linkplain #CLIENT_ADDRESS}</h4>
 * <p/>
 * Because PPP is implemented using broadcast Intents, anyone with the correct IntentFilters could intercept the
 * communication between clients and Permission Nanny. To ensure no 3rd parties can intercept Permission Nanny's
 * responses, whenever the client makes a request, it uses SecureRandom to generate a nonce. The client's response
 * BroadcastReceiver will be listening for Intents whose action and nonce match. This nonce is known as the {@link
 * #CLIENT_ADDRESS} that the client's response receiver is listening on and is packaged in the request so that
 * Permission Nanny knows where to send responses to.
 * <p/>
 * <h2>Ongoing Request Handshake Flow</h2>
 * <p/>
 * A request that accesses a stream of resources over a period of time and requires an Android callback - such as {@link
 * LocationRequest#requestLocationUpdates(long, float, Criteria, LocationListener, Looper)} is considered an ongoing
 * request.
 * <p/>
 * The requirements for an ongoing request is exactly the same as one-shot requests.
 * <p/>
 * Permission Nanny will return one authorization response followed by a series of resource responses.
 * <p/>
 * <h4>Authorization Response</h4>
 * <p/>
 * Similar to one-shot responses, the authorization response <b>must</b> contain {@link #PROTOCOL_VERSION}, {@link
 * #STATUS_CODE} and {@link #SERVER}. But it's {@link #CONNECTION} String <b>must not</b> be set to {@link #CLOSE}. In
 * addition, it <b>must not</b> contain an {@link #ENTITY_BODY}; resources are packaged in the subsequent resource
 * responses. It <b>may</b> contain an {@link #ENTITY_ERROR} if the request failed.
 * <pre>
 *  {
 *      {@link #PROTOCOL_VERSION}*
 *      {@link #STATUS_CODE}*
 *      {@link #SERVER} = {@link #AUTHORIZATION_SERVICE}*
 *      {@link #ENTITY_ERROR}
 *  }
 * </pre>
 * <h4>Resource Response</h4>
 * <p/>
 * If the user authorizes the request, a series of resource responses will follow the authorization response. Resource
 * responses <b>must</b> contain {@link #PROTOCOL_VERSION}, {@link #STATUS_CODE}, {@link #SERVER} and {@link
 * #ENTITY_BODY}. {@link #SERVER} <b>must not</b> be set to {@link #AUTHORIZATION_SERVICE}. {@link #ENTITY_BODY}
 * <b>must</b> contain the requested resource and an {@link #ACK_SERVER_ADDRESS}, which is used by the client the send
 * acknowledgements for resource responses.
 * <pre>
 *  {
 *      {@link #PROTOCOL_VERSION}*
 *      {@link #STATUS_CODE}*
 *      {@link #SERVER}*
 *      {@link #ENTITY_BODY}* = {
 *          {@link #ACK_SERVER_ADDRESS}*
 *      }
 *      {@link #ENTITY_ERROR}
 *  }
 * </pre>
 * <h4>Acknowledging Ongoing Resource Responses</h4>
 * <p/>
 * When the client receives an ongoing resource response, it <b>must</b> let Permission Nanny know by sending an
 * acknowledgement Intent to Permission Nanny. If no acknowledgement is sent, Permission Nanny will deem the client
 * dormant, stop delivering resources and tell the client to close its connection. The client can make another request
 * to re-establish the connection.
 * <p/>
 * The acknowledgement <b>must</b> contain {@link #PROTOCOL_VERSION} and {@link #CLIENT_ADDRESS}.
 * <pre>
 *  {
 *      {@link #PROTOCOL_VERSION}*
 *      {@link #CLIENT_ADDRESS}*
 *  }
 * </pre>
 * <h2>Content Query Handshake Flow</h2>
 * <p/>
 * A request that queries resources stored in {@link android.content.ContentProvider}s is considered a content query.
 * <b>Requests that insert, update and delete resources in {@link android.content.ContentProvider}s are one-shot
 * requests, not content queries.</b>
 * <p/>
 * The requirements for a content query is exactly the same as one-shot requests. Clients start the flow by sending a
 * content query to Permission Nanny. If the user authorizes the query, Permission Nanny will return a content response.
 * The content response <b>must</b> contain an {@link #ENTITY_BODY} Bundle, which <b>must</b> only contain a {@link
 * ContentRequest#SELECT} URI. The client <b>must</b> then make a 2nd request to Permission Nanny's content provider
 * with the URI appended to {@link #PROVIDER_AUTHORITY}. The 2nd request will be handled by Permission Nanny's
 * ProxyContentProvider which will execute the content query and return results.
 * <pre>
 *  {
 *      {@link #PROTOCOL_VERSION}*
 *      {@link #STATUS_CODE}*
 *      {@link #CONNECTION}* = {@link #CLOSE}
 *      {@link #SERVER}* = {@link #AUTHORIZATION_SERVICE}
 *      {@link #ENTITY_BODY} = {
 *          {@link ContentRequest#SELECT}*
 *      }
 *      {@link #ENTITY_ERROR}
 *  }
 * </pre>
 * <h4>Query URI and {@linkplain android.database.CrossProcessCursorWrapper}</h4>
 * <p/>
 * Android 15 introduced the {@link android.database.CrossProcessCursorWrapper}, which facilitated sending Cursor data
 * between processes. Permission Nanny uses this mechanism to deliver content resources to the client. After the 1st
 * handshake, Permission Nanny uses SecureRandom to generate a key to cache the query parameters; the key is returned to
 * the client as the {@link ContentRequest#SELECT} URI. The client then appends the key to the {@link
 * #PROVIDER_AUTHORITY} and starts the 2nd handshake with ProxyContentProvider.
 * <p/>
 * <i>If you would like to know how to make requests and listen for responses using the SDK or integrating your
 * application with Permission Nanny, please proceed to {@link PermissionRequest}</i>.
 */
public class Nanny {
    /** Request/Response field: Protocol version of the request the client is using. Type: {@link String} */
    @PPP public static final String PROTOCOL_VERSION = "Protocol-Version";
    /** Request/Response value: Permission Police Protocol v0.1 */
    @PPP public static final String PPP_0_1 = "PPP/0.1";

    /** Response field: Result status code. Type: int */
    @PPP public static final String STATUS_CODE = "Status-Code";
    /** Response value: Request succeeded. */
    @PPP public static final int SC_OK = 200;
    /** Response value: Server could not process request due to incorrect request parameters. */
    @PPP public static final int SC_BAD_REQUEST = 400;
    /** Response value: Client is not authorized to make a request. */
    @PPP public static final int SC_UNAUTHORIZED = 401;
    /** Response value: User denied your request. */
    @PPP public static final int SC_FORBIDDEN = 403;
    /** Response value: User does not have Permission Nanny installed. */
    @PPP public static final int SC_NOT_FOUND = 404;
    /** Response value: Server waited too long for the client to respond. (eg: ACKs for ongoing requests) */
    @PPP public static final int SC_TIMEOUT = 408;

    /** Request field: Address client is listening on. */
    @PPP public static final String CLIENT_ADDRESS = "Client-Address";

    /** Response field: Connection options. Type: {@link String} */
    @PPP public static final String CONNECTION = "Connection";
    /**
     * Response value: Client shall receive no further responses from the server; it is safe to unregister receivers or
     * unbind services.*
     */
    @PPP public static final String CLOSE = "Close";

    /** Response field: Service that handled the request. Type: {@link String} */
    @PPP public static final String SERVER = "Server";
    /** Response value: Service that authorizes requests. */
    @PPP public static final String AUTHORIZATION_SERVICE = "AuthorizationService";
    /** Response value: Service that manages the permission manifest. */
    @PPP public static final String PERMISSION_MANIFEST_SERVICE = "PermissionManifestService";
    /** Response value: Service that delivers location updates. */
    @PPP public static final String LOCATION_SERVICE = "LocationService";
    /** Response value: Service that delivers GPS status updates. */
    @PPP public static final String GPS_STATUS_SERVICE = "GpsStatusService";
    /** Response value: Service that delivers NMEA updates. */
    @PPP public static final String NMEA_SERVICE = "NmeaService";

    /** Request/Response field: Resource payload. Type: {@link Bundle}. */
    @PPP public static final String ENTITY_BODY = "Entity-Body";
    /** Response field: Error payload. Type: {@link NannyException}. */
    @PPP public static final String ENTITY_ERROR = "Entity-Error";

    /** Entity field: */
    @PPP public static final String TYPE = "Type";
    /** Entity field: */
    @PPP public static final String REQUEST_PARAMS = "RequestParams";
    /** @deprecated Use {@link #REQUEST_RATIONALE} instead. */
    @PPP @Deprecated public static final String REQUEST_REASON = "RequestReason";
    /** Entity field: */
    @PPP public static final String REQUEST_RATIONALE = "RequestRationale";
    /** Entity field: */
    @PPP public static final String SENDER_IDENTITY = "SenderIdentity";
    /** Entity field: */
    @PPP public static final String PERMISSION_MANIFEST = "PermissionManifest";
    /** Entity field: */
    @PPP public static final String ACK_SERVER_ADDRESS = "AckServerAddress";

    // experimental
    /** Permission Nanny's root package name. */
    @PPP public static final String SERVER_PACKAGE_NAME = "com.permissionnanny";
    /** Permission Nanny's Release build application ID. */
    @PPP public static final String SERVER_APP_ID = SERVER_PACKAGE_NAME;
    /** Permission Nanny's Debug build application ID. */
    public static final String SERVER_DEBUG_APP_ID = SERVER_PACKAGE_NAME + ".debug";

    /** Server Component that handles client requests. */
    @PPP public static final String CLIENT_REQUEST_RECEIVER = SERVER_PACKAGE_NAME + ".ClientRequestReceiver";

    /** Server Component that listens for client permission usages. */
    @PPP public static final String CLIENT_PERMISSION_MANIFEST_RECEIVER = SERVER_PACKAGE_NAME +
            ".ClientPermissionManifestReceiver";

    /** Broadcast Action: Sent when Permission Nanny wants to know which permissions clients are using. */
    @PPP public static final String ACTION_GET_PERMISSION_MANIFEST = SERVER_PACKAGE_NAME + ".GET_PERMISSION_MANIFEST";

    /** Authority that resolves to Permission Nanny's proxy content provider. */
    @PPP public static final String PROVIDER_AUTHORITY = ".proxy_content_provider";
    @PPP public static final Uri PROVIDER = Uri.parse("content://" + SERVER_APP_ID + PROVIDER_AUTHORITY);
    @PPP public static final Uri DEBUG_PROVIDER = Uri.parse("content://" + SERVER_DEBUG_APP_ID + PROVIDER_AUTHORITY);

    private static boolean debugBuild;

    /**
     * Configure the SDK to communicate with Permission Nanny's Debug or Release build variant. The Release build is
     * published to app stores, while Debug build is used for development.
     *
     * @param targetDebugBuild {@code true} to issue requests to a Debug build, {@code false} to target a Release build
     */
    @VisibleForTesting
    public static void configureServer(boolean targetDebugBuild) {
        debugBuild = targetDebugBuild;
    }

    /**
     * Return Permission Nanny's app ID, which changes depending on the build variant to avoid conflict with each
     * other.
     */
    public static String getServerAppId() {
        return debugBuild ? SERVER_DEBUG_APP_ID : SERVER_APP_ID;
    }

    /**
     * Return Permission Nanny's proxy content provider's URI, which changes depending on the build variant to avoid
     * conflict with each other.
     */
    public static Uri getProxyContentProvider() {
        return debugBuild ? DEBUG_PROVIDER : PROVIDER;
    }

    /**
     * Checks if Permission Nanny is installed.
     *
     * @param context Activity, Service, etc.
     * @return {@code true} if Permission Nanny is installed
     */
    public static boolean isPermissionNannyInstalled(@NonNull Context context) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo server = null;
        try {
            server = pm.getApplicationInfo(getServerAppId(), 0);
        } catch (PackageManager.NameNotFoundException e) {/* Nothing to see here. */}
        return server != null;
    }

    /**
     * Validates if the sender of an Intent is Permission Nanny.
     *
     * @param intent Unvalidated Intent
     * @return {@code true} if Intent is from Permission Nanny.
     * @throws NannyException if someone is trying to impersonate Permission Nanny
     */
    public static boolean isIntentFromPermissionNanny(Intent intent) throws NannyException {
        Bundle entity = intent.getBundleExtra(Nanny.ENTITY_BODY);
        if (entity == null) {
            throw new NannyException("ENTITY_BODY is missing.");
        }
        PendingIntent sender = entity.getParcelable(Nanny.SENDER_IDENTITY);
        if (sender == null) {
            throw new NannyException("SENDER_IDENTITY is missing.");
        }
        String senderPackage = sender.getIntentSender().getTargetPackage();
        if (!Nanny.getServerAppId().equals(senderPackage)) {
            throw new NannyException(senderPackage + " is attempting to impersonate Permission Nanny.");
        }
        return true;
    }
}
