package com.permissionnanny.lib.request.content;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.RequestParams;

/**
 * A request to Permission Nanny to access {@link android.content.ContentProvider}s that are protected by Android
 * permissions on your behalf.
 * <p/>
 * See {@link PermissionRequest} for details on how to use this class.
 */
public class ContentRequest extends PermissionRequest {

    @PPP public static final String SELECT = "Select";
    @PPP public static final String INSERT = "Insert";
    @PPP public static final String UPDATE = "Update";
    @PPP public static final String DELETE = "Delete";

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

    public ContentRequest(RequestParams params) {
        super(params);
    }

    /**
     * Attach a listener.
     *
     * @param listener Response receiver
     * @return itself
     */
    public ContentRequest listener(@NonNull ContentListener listener) {
        return (ContentRequest) addFilter(new ContentEvent(mParams, listener));
    }

    /**
     * /** Start the request.
     *
     * @param context  Activity, Service, etc.
     * @param reason   Explain to the user why you need to access the resource. This is displayed to the user in a
     *                 dialog when Permission Nanny needs to ask the user for authorization.
     * @param listener Response receiver
     */
    public void startRequest(@NonNull Context context, @Nullable String reason, @NonNull ContentListener listener) {
        listener(listener);
        startRequest(context, reason);
    }

    @Override
    public int getRequestType() {
        return CONTENT_REQUEST;
    }
}
