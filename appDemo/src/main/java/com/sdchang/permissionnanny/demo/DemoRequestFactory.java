package com.sdchang.permissionnanny.demo;

import android.app.Dialog;
import android.content.Context;
import com.sdchang.permissionnanny.lib.request.PermissionRequest;

/**
 *
 */
public interface DemoRequestFactory {
    abstract PermissionRequest getRequest(int position);

    abstract int getCount();

    abstract String getLabel(int position);

    abstract boolean hasExtras(int position);

    Dialog buildDialog(Context context, int position);
}
