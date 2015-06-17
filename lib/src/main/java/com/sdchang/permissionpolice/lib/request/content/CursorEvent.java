package com.sdchang.permissionpolice.lib.request.content;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import com.sdchang.permissionpolice.lib.Event;
import com.sdchang.permissionpolice.lib.Police;
import org.apache.http.HttpStatus;

/**
 *
 */
public class CursorEvent implements Event {
    public static final Uri PROVIDER = new Builder().scheme("content").authority(Police.PROVIDER_AUTHORITY).build();
    public static final String NONCE = "nonce";
    public static final String CURSOR_SERVICE = "cursorService";

    private CursorRequest mRequest;
    private CursorListener mListener;

    public CursorEvent(CursorRequest request, CursorListener listener) {
        mRequest = request;
        mListener = listener;
    }

    @Override
    public String filter() {
        return Police.AUTHORIZATION_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        Uri authorized = null;
        if (HttpStatus.SC_OK == intent.getIntExtra(Police.STATUS_CODE, 0)) {
            long nonce = intent.getBundleExtra(Police.ENTITY_BODY).getLong(NONCE, 0);
            authorized = PROVIDER.buildUpon().appendPath(Long.toString(nonce)).build();
        }

        Bundle result = intent.getExtras();
        Cursor data = null;
        Uri inserted = null;
        int rowsUpdated = 0;
        int rowsDeleted = 0;

        ContentResolver cr = context.getContentResolver();
        if (CursorRequest.SELECT == mRequest.operation()) {
            data = authorized == null ? null : cr.query(authorized, null, null, null, null);
        } else if (CursorRequest.INSERT == mRequest.operation()) {
            inserted = authorized == null ? null : cr.insert(authorized, null);
        } else if (CursorRequest.UPDATE == mRequest.operation()) {
            rowsUpdated = authorized == null ? 0 : cr.update(authorized, null, null, null);
        } else if (CursorRequest.DELETE == mRequest.operation()) {
            rowsDeleted = authorized == null ? 0 : cr.delete(authorized, null, null);
        }

        mListener.onResult(result, data, inserted, rowsUpdated, rowsDeleted);
    }
}
