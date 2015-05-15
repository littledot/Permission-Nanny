package com.sdchang.permissionpolice.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.net.Uri;
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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        long nonce;
        try {
            nonce = Long.parseLong(uri.getLastPathSegment());
        } catch (NumberFormatException e) {
            return null;
        }
        CursorRequest request = approvedRequests.get(nonce);
        approvedRequests.remove(nonce);
        Cursor cursor = request == null ? null : new CrossProcessCursorWrapper(getContext().getContentResolver().query(
                request.uri(), toArray(request.projection()), request.selection(), toArray(request.selectionArgs()), request.sortOrder()));
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private String[] toArray(@Nullable List<String> list) {
        return list != null ? list.toArray(new String[list.size()]) : null;
    }
}
