package com.sdchang.permissionpolice.lib.request.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import com.sdchang.permissionpolice.lib.Police;

/**
 *
 */
public class CursorRequestHandshakeReceiver extends BroadcastReceiver {

    public static final Uri PROVIDER = new Builder().scheme("content").authority(Police.PROVIDER_AUTHORITY).build();
    public static final String ACTION_FILTER = "ppResult";
    public static final String NONCE = "nonce";

    CursorRequest mRequest;
    CursorListener mListener;

    public CursorRequestHandshakeReceiver(CursorRequest request, CursorListener listener) {
        mRequest = request;
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.unregisterReceiver(this);

        long nonce = intent.getLongExtra(NONCE, 0);
        Cursor data = context.getContentResolver().query(PROVIDER.buildUpon().appendPath(Long.toString(nonce)).build()
                , null, null, null, null);
        mListener.callback(data);
    }
}
