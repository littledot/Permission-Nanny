package com.sdchang.permissionpolice.lib.request;

import android.os.Bundle;
import com.sdchang.permissionpolice.lib.Police;

/**
 *
 */
public class BaseResponse {

    protected final Bundle mResponse;

    public BaseResponse(Bundle response) {
        mResponse = response;
    }

    public boolean isApproved() {
        return mResponse.getBoolean(Police.APPROVED);
    }
}

