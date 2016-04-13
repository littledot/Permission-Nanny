package com.permissionnanny.lib.request.content;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public class ContentEvent implements Event {

    private RequestParams mRequest;
    private ContentListener mListener;

    public ContentEvent(RequestParams request, ContentListener listener) {
        mRequest = request;
        mListener = listener;
    }

    @Override
    public String filter() {
        return Nanny.AUTHORIZATION_SERVICE;
    }

    @Override
    public void process(Context context, Intent intent) {
        switch (mRequest.opCode) {
            case ContentRequest.SELECT:
                Uri authorized = null;
                if (Nanny.SC_OK == intent.getIntExtra(Nanny.STATUS_CODE, 0)) {
                    long uriPath = intent.getBundleExtra(Nanny.ENTITY_BODY).getLong(mRequest.opCode, 0);
                    authorized = Nanny.getProxyContentProvider().buildUpon().appendPath(Long.toString(uriPath)).build();
                }

                ContentResolver cr = context.getContentResolver();
                Cursor data = authorized == null ? null : cr.query(authorized, null, null, null, null);
                mListener.onResponse(intent.getExtras(), data);
                break;
            default:
                mListener.onResponse(intent.getExtras(), null);
        }
    }
}
