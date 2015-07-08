package com.permissionnanny.lib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class Nanny {
    /** Request/Response field: Protocol version of the request the client is using. Type: string */
    public static final String PROTOCOL_VERSION = "Protocol-Version";
    /** Request/Response value: Permission Police Protocol v1.0 */
    public static final String PPP_1_0 = "PPP/1.0";

    /** Response field: Result status code. Type: int */
    public static final String STATUS_CODE = "Status-Code";
    /** Response value: Request succeeded. */
    public static final int SC_OK = 200;
    /** Response value: Server could not process request due to incorrect request parameters. */
    public static final int SC_BAD_REQUEST = 400;
    /** Response value: Client is not authorized to make a request. */
    public static final int SC_UNAUTHORIZED = 401;
    /** Response value: User denied your request. */
    public static final int SC_FORBIDDEN = 403;

    /** Request field: Address client is listening on. */
    public static final String CLIENT_ADDRESS = "Client-Address";

    /** Response field: Connection options. Type: string */
    public static final String CONNECTION = "Connection";
    /**
     * Response value: Client shall receive no further responses from the server; it is safe to unregister receivers or
     * unbind services.*
     */
    public static final String CLOSE = "Close";

    /**
     * Request/Response field: Entity encoding; the receiving end should use the appropriately-typed {@link
     * Intent#getExtras()} method to decode the entity. Type: string
     */
    public static final String CONTENT_ENCODING = "Content-Encoding";
    /** Request/Response value: Entity is a {@link Bundle}; use {@link Intent#getBundleExtra(String)} to decode. */
    public static final String ENCODING_BUNDLE = "encoding/bundle";
    /**
     * Request/Response value: Entity is a {@link Serializable}; use {@link Intent#getSerializableExtra(String)} to
     * decode.
     */
    public static final String ENCODING_SERIALIZABLE = "encoding/serializable";
    /**
     * Request/Response value: Entity is an {@link ArrayList ArrayList&lt;String&gt;}; use {@link
     * Intent#getStringArrayListExtra(String)} to decode.
     */
    public static final String ENCODING_STRING_ARRAY_LIST = "encoding/string-array-list";

    /** Request/Response field: Entity class type. Type: string */
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String TYPE_STRING_ARRAY_LIST = ArrayList.class.getCanonicalName() + "<String>";

    /** Response field: Service that handled the request. Type: string */
    public static final String SERVER = "Server";
    /** Response value: Service that authorizes requests. */
    public static final String AUTHORIZATION_SERVICE = "AuthorizationService";
    /** Response value: Service that delivers location updates. */
    public static final String LOCATION_SERVICE = "LocationService";
    /** Response value: Service that delivers GPS status updates. */
    public static final String GPS_STATUS_SERVICE = "GpsStatusService";
    /** Response value: Service that delivers NMEA updates. */
    public static final String NMEA_SERVICE = "NmeaService";

    /** Request/Response field: Data payload. Type: see {@link #CONTENT_ENCODING} and {@link #CONTENT_TYPE}. */
    public static final String ENTITY_BODY = "Entity-Body";
    /** Response field: Error payload. Type: see {@link #CONTENT_ENCODING} & {@link #CONTENT_TYPE}. */
    public static final String ENTITY_ERROR = "Entity-Error";

    // experimental
    /** Permission Nanny release build package name. */
    public static final String SERVER_PACKAGE = "com.permissionnanny";
    /** Permission Nanny debug build package name. */
    public static final String SERVER_DEBUG_PACKAGE = "com.permissionnanny.debug";
    /** Permission Nanny's app ID changes depending on the build type to avoid conflict with each other. */
    public static final String SERVER_APP_ID = BuildConfig.DEBUG ? SERVER_DEBUG_PACKAGE : SERVER_PACKAGE;

    /** Server Component that handles client requests. */
    public static final String CLIENT_REQUEST_RECEIVER = SERVER_PACKAGE + ".ClientRequestReceiver";

    /** Server Component that listens for client permission usages. */
    public static final String CLIENT_PERMISSION_USAGE_RECEIVER = SERVER_PACKAGE + ".ClientPermissionUsageReceiver";

    /** Broadcast Action: Sent when Permission Nanny wants to know which permissions clients are using. */
    public static final String ACTION_GET_PERMISSION_USAGES = SERVER_PACKAGE + ".GET_PERMISSION_USAGES";

    /** Authority that resolves to Permission Nanny's cursor request content provider. */
    public static final String PROVIDER_AUTHORITY = SERVER_APP_ID + ".cursor_content_provider";

    public static final String ACK_SERVER = "ackServer";

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
