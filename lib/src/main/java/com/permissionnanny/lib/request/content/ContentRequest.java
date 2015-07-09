package com.permissionnanny.lib.request.content;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.RequestParams;

public class ContentRequest extends PermissionRequest {

    public static final String SELECT = "Select";
    public static final String INSERT = "Insert";
    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";

    public ContentRequest(RequestParams params) {
        super(params);
    }

    @Override
    public int getRequestType() {
        return CONTENT_REQUEST;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private RequestParams params = new RequestParams();

        public Builder select() {
            params.opCode = SELECT;
            return this;
        }

        public Builder insert() {
            params.opCode = INSERT;
            return this;
        }

        public Builder update() {
            params.opCode = UPDATE;
            return this;
        }

        public Builder delete() {
            params.opCode = DELETE;
            return this;
        }

        public Builder uri(Uri uri) {
            params.uri0 = uri;
            return this;
        }

        public Builder projection(@Nullable String[] projection) {
            params.stringArray0 = projection;
            return this;
        }

        public Builder selection(@Nullable String selection) {
            params.string0 = selection;
            return this;
        }

        public Builder selectionArgs(@Nullable String[] selectionArgs) {
            params.stringArray1 = selectionArgs;
            return this;
        }

        public Builder sortOrder(@Nullable String sortOrder) {
            params.string1 = sortOrder;
            return this;
        }

        public Builder contentValues(@Nullable ContentValues contentValues) {
            params.contentValues0 = contentValues;
            return this;
        }

        public ContentRequest build() {
            return new ContentRequest(params);
        }
    }

    /**
     * @param context
     * @param reason
     * @param listener
     */
    public void startRequest(Context context, String reason, ContentListener listener) {
        // begin handshake
        addFilter(new ContentEvent(mParams, listener));
        super.startRequest(context, reason);
    }
}
