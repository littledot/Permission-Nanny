package com.sdchang.permissionpolice.lib.request.content;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.Event;
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
        return CURSOR_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        Cursor data = null;
        if (HttpStatus.SC_OK == intent.getIntExtra(Police.STATUS_CODE, 0)) {
            long nonce = intent.getLongExtra(NONCE, 0);
            data = context.getContentResolver().query(PROVIDER.buildUpon().appendPath(Long.toString(nonce)).build(),
                    null, null, null, null);
        }
        mListener.callback(data);
    }
}
