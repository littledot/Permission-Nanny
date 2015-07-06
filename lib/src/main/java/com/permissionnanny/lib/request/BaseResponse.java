package com.permissionnanny.lib.request;

import android.os.Bundle;
import com.permissionnanny.lib.Nanny;
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
        return HttpStatus.SC_OK == mResponse.getInt(Nanny.STATUS_CODE);
    }
}

