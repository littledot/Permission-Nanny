package com.sdchang.permissionpolice.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import com.sdchang.permissionpolice.lib.request.content.CursorRequest;

import java.util.List;

/**
 *
 */
public class CursorContentProvider extends ContentProvider {
    // TODO #1: Set a TTL for approved CursorRequests.
    static LongSparseArray<CursorRequest> approvedRequests = new LongSparseArray<>();

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
            CursorRequest request = validateRequest(uri);
            return request == null ? null : new CrossProcessCursorWrapper(getContext().getContentResolver()
                    .query(request.uri(), toArray(request.projection()), request.selection(),
                            toArray(request.selectionArgs()), request.sortOrder()));
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        CursorRequest request = validateRequest(uri);
        return request == null ? null : getContext().getContentResolver()
                .insert(request.uri(), request.contentValues());
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        CursorRequest request = validateRequest(uri);
        return request == null ? 0 : getContext().getContentResolver()
                .delete(request.uri(), request.selection(), toArray(request.selectionArgs()));
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        CursorRequest request = validateRequest(uri);
        return request == null ? 0 : getContext().getContentResolver()
                .update(request.uri(), request.contentValues(), request.selection(), toArray(request.selectionArgs()));
    }

    /**
     * @param uri
     * @return
     */
    private CursorRequest validateRequest(Uri uri) {
        long nonce;
        try {
            nonce = Long.parseLong(uri.getLastPathSegment());
        } catch (NumberFormatException e) {
            return null;
        }

        CursorRequest request = approvedRequests.get(nonce);
        approvedRequests.remove(nonce);
        return request;
    }

    private String[] toArray(@Nullable List<String> list) {
        return list != null ? list.toArray(new String[list.size()]) : null;
    }
}
