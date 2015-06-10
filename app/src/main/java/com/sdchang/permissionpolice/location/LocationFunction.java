package com.sdchang.permissionpolice.location;

import android.location.LocationManager;
import android.os.Bundle;
import com.sdchang.permissionpolice.lib.request.location.LocationRequest;

/**
 *
 */
interface LocationFunction {
    void execute(LocationManager lm, LocationRequest request, Bundle response) throws Throwable;
}
