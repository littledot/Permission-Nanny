package com.sdchang.permissionnanny;

import android.content.Context;
import android.os.Bundle;
import com.sdchang.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public interface ProxyFunction {
    void execute(Context context, RequestParams request, Bundle response);
}
