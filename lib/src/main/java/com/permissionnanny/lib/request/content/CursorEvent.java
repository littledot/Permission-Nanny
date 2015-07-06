package com.permissionnanny.lib.request.content;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;
import org.apache.http.HttpStatus;

/**
 *
 */
public class CursorEvent implements Event {
    public static final Uri PROVIDER = new Builder().scheme("content").authority(Nanny.PROVIDER_AUTHORITY).build();
    public static final String NONCE = "nonce";

    private RequestParams mRequest;
    private CursorListener mListener;

    public CursorEvent(RequestParams request, CursorListener listener) {
        mRequest = request;
        mListener = listener;
    }

    @Override
    public String filter() {
        return Nanny.AUTHORIZATION_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        Uri authorized = null;
        if (HttpStatus.SC_OK == intent.getIntExtra(Nanny.STATUS_CODE, 0)) {
            long nonce = intent.getBundleExtra(Nanny.ENTITY_BODY).getLong(NONCE, 0);
            authorized = PROVIDER.buildUpon().appendPath(Long.toString(nonce)).build();
        }

        Bundle result = intent.getExtras();
        Cursor data = null;
        Uri inserted = null;
        int rowsUpdated = 0;
        int rowsDeleted = 0;

        ContentResolver cr = context.getContentResolver();
        switch (mRequest.opCode) {
        case CursorRequest.SELECT:
            data = authorized == null ? null : cr.query(authorized, null, null, null, null);
            break;
        case CursorRequest.INSERT:
            inserted = authorized == null ? null : cr.insert(authorized, null);
            break;
        case CursorRequest.UPDATE:
            rowsUpdated = authorized == null ? 0 : cr.update(authorized, null, null, null);
            break;
        case CursorRequest.DELETE:
            rowsDeleted = authorized == null ? 0 : cr.delete(authorized, null, null);
            break;
        }

        mListener.onResult(result, data, inserted, rowsUpdated, rowsDeleted);
    }
}