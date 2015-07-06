package com.permissionnanny;

import android.content.Context;
import android.os.Bundle;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public interface ProxyFunction {
    void execute(Context context, RequestParams request, Bundle response);
}
