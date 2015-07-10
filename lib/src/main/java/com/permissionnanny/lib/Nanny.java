package com.permissionnanny.lib;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 *
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
    /** Response value: Service that delivers location updates. */
    @PPP public static final String LOCATION_SERVICE = "LocationService";
    /** Response value: Service that delivers GPS status updates. */
    @PPP public static final String GPS_STATUS_SERVICE = "GpsStatusService";
    /** Response value: Service that delivers NMEA updates. */
    @PPP public static final String NMEA_SERVICE = "NmeaService";

    /** Request/Response field: Data payload. Type: {@link Bundle}. */
    @PPP public static final String ENTITY_BODY = "Entity-Body";
    /** Response field: Error payload. Type: {@link NannyException}. */
    @PPP public static final String ENTITY_ERROR = "Entity-Error";

    /** Entity field: */
    @PPP public static final String TYPE = "Type";
    /** Entity field: */
    @PPP public static final String REQUEST_PARAMS = "RequestParams";
    /** Entity field: */
    @PPP public static final String REQUEST_REASON = "RequestReason";
    /** Entity field: */
    @PPP public static final String SENDER_IDENTITY = "SenderIdentity";
    /** Entity field: */
    @PPP public static final String PERMISSION_MANIFEST = "PermissionManifest";
    /** Entity field: */
    @PPP public static final String ACK_SERVER_ADDRESS = "AckServerAddress";

    // experimental
    /** Permission Nanny release build package name. */
    @PPP public static final String SERVER_PACKAGE = "com.permissionnanny";
    /** Permission Nanny debug build package name. */
    public static final String SERVER_DEBUG_PACKAGE = "com.permissionnanny.debug";
    /** Permission Nanny's app ID changes depending on the build type to avoid conflict with each other. */
    public static final String SERVER_APP_ID = BuildConfig.DEBUG ? SERVER_DEBUG_PACKAGE : SERVER_PACKAGE;

    /** Server Component that handles client requests. */
    @PPP public static final String CLIENT_REQUEST_RECEIVER = SERVER_PACKAGE + ".ClientRequestReceiver";

    /** Server Component that listens for client permission usages. */
    @PPP public static final String CLIENT_PERMISSION_MANIFEST_RECEIVER = SERVER_PACKAGE +
            ".ClientPermissionManifestReceiver";

    /** Broadcast Action: Sent when Permission Nanny wants to know which permissions clients are using. */
    @PPP public static final String ACTION_GET_PERMISSION_MANIFEST = SERVER_PACKAGE + ".GET_PERMISSION_MANIFEST";

    /** Authority that resolves to Permission Nanny's proxy content provider. */
    @PPP public static final String PROVIDER_AUTHORITY = SERVER_APP_ID + ".proxy_content_provider";

    /**
     * Checks if Permission Nanny is installed.
     *
     * @param context Activity, Service, etc.
     * @return {@code true} if Permission Nanny is installed
     */
    public static boolean isPermissionNannyInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo server = null;
        try {
            server = pm.getApplicationInfo(SERVER_APP_ID, 0);
        } catch (PackageManager.NameNotFoundException e) {/* Nothing to see here. */}
        return server != null;
    }
}
