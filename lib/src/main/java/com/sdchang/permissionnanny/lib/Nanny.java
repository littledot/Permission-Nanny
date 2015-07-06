package com.sdchang.permissionnanny.lib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.io.Serializable;

/**
 *
 */
public class Nanny {
    /** Request field: Protocol version of the request the client is using. */
    public static final String PROTOCOL_VERSION = "Protocol-Version";
    /** Request value: Permission Nanny Protocol v1.0 */
    public static final String PNP_1_0 = "PNP/1.0";

    /** Response field: Result status code. */
    public static final String STATUS_CODE = "Status-Code";
    /** Response value: Request succeeded. */
    public static final int SC_OK = 200;
    /** Response value: Server could not process request due to incorrect request parameters. */
    public static final int SC_BAD_REQUEST = 400;
    // TODO #44: Respond with 403 instead of 401 when user denies a request
    /** Response value: Client is not authorized to make a request. */
    public static final int SC_UNAUTHORIZED = 401;
    /** Response value: User denied your request. */
    public static final int SC_FORBIDDEN = 403;

    /** Request/Response field: Data payload. */
    public static final String ENTITY_BODY = "Entity-Body";
    /** Response field: Error payload. */
    public static final String ENTITY_ERROR = "Entity-Error";

    /** Response field: Connection options. */
    public static final String CONNECTION = "Connection";
    /**
     * Response value: Client shall receive no further responses from the server; it is safe to unregister receivers or
     * unbind services.*
     */
    public static final String CLOSE = "Close";

    /** Request/Response field: Entity class type. */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * Request/Response field: Entity encoding; the receiving end should use the appropriately-typed {@link
     * Intent#getExtras()} method to decode the entity.
     */
    public static final String CONTENT_ENCODING = "Content-Encoding";
    /** Request/Response value: Entity is a {@link Bundle}; use {@link Intent#getBundleExtra(String)} to decode. */
    public static final String ENCODING_BUNDLE = "encoding/bundle";
    /**
     * Request/Response value: Entity is a {@link Serializable}; use {@link Intent#getSerializableExtra(String)} to
     * decode.
     */
    public static final String ENCODING_SERIALIZABLE = "encoding/serializable";

    /** Response field: Service that handled the request. */
    public static final String SERVER = "Server";
    /** Response value: Service that authorizes requests. */
    public static final String AUTHORIZATION_SERVICE = "AuthorizationService";
    /** Response value: Service that delivers location updates. */
    public static final String LOCATION_SERVICE = "LocationService";
    /** Response value: Service that delivers GPS status updates. */
    public static final String GPS_STATUS_SERVICE = "GpsStatusService";
    /** Response value: Service that delivers NMEA updates. */
    public static final String NMEA_SERVICE = "NmeaService";

    // experimental
    /** Authority that resolves to Permission Nanny's cursor request content provider. */
    public static final String PROVIDER_AUTHORITY = "com.permissionnanny.cursor_content_provider";

    /** Broadcast Action: Sent when Permission Nanny wants to know which permissions do you plan to use. */
    public static final String ACTION_GET_PERMISSION_USAGES = "com.permissionnanny.GET_PERMISSION_USAGES";

    public static final String ACK_SERVER = "ackServer";

    public static boolean isPermissionPoliceInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo server = null;
        try {
            server = pm.getApplicationInfo("com.permissionpolice", 0);
        } catch (PackageManager.NameNotFoundException e) {/* Nothing to see here. */}
        return server == null;
    }
}
