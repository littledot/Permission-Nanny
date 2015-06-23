package com.sdchang.permissionpolice.lib.request.content;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import com.sdchang.permissionpolice.lib.request.PermissionRequest;
import com.sdchang.permissionpolice.lib.request.RequestParams;

import java.util.Arrays;
import java.util.List;

public class CursorRequest extends PermissionRequest {

    public static final String SELECT = "Select";
    public static final String INSERT = "Insert";
    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";

    public CursorRequest(RequestParams params) {
        super(params);
    }

    @Override
    public int getRequestType() {
        return CURSOR_REQUEST;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        RequestParams.Builder builder = RequestParams.newBuilder();

        public Builder select() {
            builder.opCode(SELECT);
            return this;
        }

        public Builder insert() {
            builder.opCode(INSERT);
            return this;
        }

        public Builder update() {
            builder.opCode(UPDATE);
            return this;
        }

        public Builder delete() {
            builder.opCode(DELETE);
            return this;
        }

        public Builder uri(Uri uri) {
            builder.uri0(uri);
            return this;
        }

        public Builder projection(@Nullable List<String> projection) {
            builder.listOfStrings0(projection);
            return this;
        }

        public Builder projection(@Nullable String[] projection) {
            projection(Arrays.asList(projection));
            return this;
        }

        public Builder selection(@Nullable String selection) {
            builder.string0(selection);
            return this;
        }

        public Builder selectionArgs(@Nullable List<String> selectionArgs) {
            builder.listOfStrings1(selectionArgs);
            return this;
        }

        public Builder selectionArgs(@Nullable String[] selectionArgs) {
            selectionArgs(Arrays.asList(selectionArgs));
            return this;
        }

        public Builder sortOrder(@Nullable String sortOrder) {
            builder.string1(sortOrder);
            return this;
        }

        public Builder contentValues(@Nullable ContentValues contentValues) {
            builder.contentValues(contentValues);
            return this;
        }

        public CursorRequest build() {
            RequestParams params = builder.build();
            return new CursorRequest(params);
        }
    }

    /**
     * @param context
     * @param reason
     * @param listener
     */
    public void startRequest(Context context, String reason, CursorListener listener) {
        // begin handshake
        addFilter(new CursorEvent(mParams, listener));
        super.startRequest(context, reason);
    }
}
