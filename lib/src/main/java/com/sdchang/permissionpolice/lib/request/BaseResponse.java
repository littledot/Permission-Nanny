package com.sdchang.permissionpolice.lib.request;

import android.os.Bundle;
import com.sdchang.permissionpolice.lib.Police;
import org.apache.http.HttpStatus;

/**
 *
 */
public class BaseResponse {

    protected final Bundle mResponse;

    public BaseResponse(Bundle response) {
        mResponse = response;
    }

    public boolean isApproved() {
        return HttpStatus.SC_OK == mResponse.getInt(Police.STATUS_CODE);
    }
}

