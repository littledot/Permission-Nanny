package com.permissionnanny.lib.request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.simple.SimpleListener;

/**
 *
 */
public class PermissionEvent implements Event {

    private SimpleListener mListener;

    public PermissionEvent(SimpleListener listener) {
        mListener = listener;
    }

    @Override
    public String filter() {
        return Nanny.AUTHORIZATION_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        Bundle response = intent.getExtras();
        mListener.onResponse(response);
    }
}
