package com.permissionnanny.lib.request.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.request.PermissionEvent;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
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
     * @param reason   Explain to the user why you need to access the feature. This is displayed to the user in a dialog
     *                 when Permission Nanny needs to ask the user for authorization.
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
