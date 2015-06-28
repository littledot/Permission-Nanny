package com.sdchang.permissionpolice;

import android.content.Context;
import android.os.Bundle;
import com.sdchang.permissionpolice.lib.request.RequestParams;

/**
 *
 */
public interface ProxyFunction {
    void execute(Context context, RequestParams request, Bundle response);
}
