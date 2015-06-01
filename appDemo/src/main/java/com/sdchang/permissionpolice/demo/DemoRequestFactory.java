package com.sdchang.permissionpolice.demo;

import android.app.Dialog;
import android.content.Context;
import com.sdchang.permissionpolice.lib.request.BaseRequest;

/**
 *
 */
public interface DemoRequestFactory {
    abstract BaseRequest getRequest(int position);

    abstract int getCount();

    abstract String getLabel(int position);

    abstract boolean hasExtras(int position);

    Dialog buildDialog(Context context, int position);
}
