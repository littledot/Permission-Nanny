package com.permissionnanny.lib.request.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.request.PermissionEvent;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.RequestParams;

/**
 * A request to Permission Nanny to access manager resources - such as {@link android.telephony.TelephonyManager} and
 * {@link android.location.LocationManager} - that are protected by Android permissions on your behalf.
 */
public class SimpleRequest extends PermissionRequest {

    public SimpleRequest(RequestParams params) {
        super(params);
    }

    /**
     * Attach a listener.
     *
     * @param listener Response receiver
     * @return itself
     */
    public SimpleRequest listener(@NonNull SimpleListener listener) {
        return (SimpleRequest) addFilter(new PermissionEvent(listener));
    }

    /**
     * Start the request.
     *
     * @param context  Activity, Service, etc.
     * @param reason   Explain to the user why you need to access the resource. This is displayed to the user in a
     *                 dialog when Permission Nanny needs to ask the user for authorization.
     * @param listener Response receiver
     */
    public void startRequest(@NonNull Context context, @Nullable String reason, @NonNull SimpleListener listener) {
        listener(listener);
        startRequest(context, reason);
    }

    @Override
    public int getRequestType() {
        return SIMPLE_REQUEST;
    }
}
