package com.permissionnanny.lib;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.request.PermissionRequest;

import javax.inject.Inject;

/**
 * A client to facilitate communication with Permission Nanny.
 */
public class NannyClient {

    private Context mContext;

    @Inject
    public NannyClient(Context context) {
        mContext = context;
    }

    /**
     * Checks if Permission Nanny is installed.
     *
     * @return {@code true} if Permission Nanny is installed
     */
    public boolean isPermissionNannyInstalled() {
        PackageManager pm = mContext.getPackageManager();
        ApplicationInfo server = null;
        try {
            server = pm.getApplicationInfo(Nanny.getServerAppId(), 0);
        } catch (PackageManager.NameNotFoundException e) {/* Nothing to see here. */}
        return server != null;
    }

    /**
     * Start the request.
     *
     * @param request Request for permission-protected resources
     * @param reason  Explain to the user why you need to access the resource. This is displayed to the user in a dialog
     *                when Permission Nanny needs to ask the user for authorization.
     */
    public void startRequest(@NonNull PermissionRequest request, @Nullable String reason) {
        request.startRequest(mContext, reason);
    }
}
