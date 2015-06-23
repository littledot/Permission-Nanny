package com.sdchang.permissionpolice.location;

import android.location.LocationManager;
import android.os.Bundle;
import com.sdchang.permissionpolice.lib.request.RequestParams;

/**
 *
 */
interface LocationFunction {
    void execute(LocationManager lm, RequestParams request, Bundle response) throws Throwable;
}
