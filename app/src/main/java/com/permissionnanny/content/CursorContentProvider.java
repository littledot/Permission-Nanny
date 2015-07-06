package com.permissionnanny.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.util.LongSparseArray;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public class CursorContentProvider extends ContentProvider {
    // TODO #1: Set a TTL for approved CursorRequests.
    static LongSparseArray<RequestParams> approvedRequests = new LongSparseArray<>();

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return getContext().getContentResolver().getType(uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (VERSION.SDK_INT >= 15) {
            RequestParams request = validateRequest(uri);
            return request == null ? null : new CrossProcessCursorWrapper(getContext().getContentResolver()
                    .query(request.uri0, request.stringArray0, request.string0, request.stringArray1, request.string1));
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        RequestParams request = validateRequest(uri);
        return request == null ? null : getContext().getContentResolver()
                .insert(request.uri0, request.contentValues0);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        RequestParams request = validateRequest(uri);
        return request == null ? 0 : getContext().getContentResolver()
                .delete(request.uri0, request.string0, request.stringArray1);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        RequestParams request = validateRequest(uri);
        return request == null ? 0 : getContext().getContentResolver()
                .update(request.uri0, request.contentValues0, request.string0, request.stringArray1);
    }

    /**
     * @param uri
     * @return
     */
    private RequestParams validateRequest(Uri uri) {
        long nonce;
        try {
            nonce = Long.parseLong(uri.getLastPathSegment());
        } catch (NumberFormatException e) {
            return null;
        }

        RequestParams request = approvedRequests.get(nonce);
        approvedRequests.remove(nonce);
        return request;
    }
}
