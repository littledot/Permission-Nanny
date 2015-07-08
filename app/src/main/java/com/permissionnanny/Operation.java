package com.permissionnanny;

import android.support.annotation.StringRes;
import com.permissionnanny.content.ContentOperation;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.operation.ProxyOperation;

/**
 *
 */
public class Operation {
    public static Operation getOperation(RequestParams request, int type) {
        return type == PermissionRequest.CURSOR_REQUEST ?
                ContentOperation.getOperation(request) : ProxyOperation.getOperation(request);
    }

    @StringRes public final int mDialogTitle;
    public final int mMinSdk;

    public Operation(int dialogTitle, int minSdk) {
        mDialogTitle = dialogTitle;
        mMinSdk = minSdk;
    }
}
