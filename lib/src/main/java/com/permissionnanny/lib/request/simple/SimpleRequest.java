package com.permissionnanny.lib.request.simple;

import android.support.annotation.NonNull;
import com.permissionnanny.lib.BundleEvent;
import com.permissionnanny.lib.BundleListener;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public class SimpleRequest extends PermissionRequest {
    public SimpleRequest(RequestParams params) {
        super(params);
    }

    @Override
    public int getRequestType() {
        return SIMPLE_REQUEST;
    }

    /**
     * Attach a listener.
     *
     * @param listener Result receiver
     * @return itself
     */
    public PermissionRequest listener(@NonNull BundleListener listener) {
        return addFilter(new BundleEvent(listener));
    }
}
