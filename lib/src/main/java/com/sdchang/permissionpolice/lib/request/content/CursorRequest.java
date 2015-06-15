package com.sdchang.permissionpolice.lib.request.content;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import auto.parcel.AutoParcel;
import com.sdchang.permissionpolice.lib.request.BaseRequest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

@AutoParcel
public abstract class CursorRequest extends BaseRequest {

    @Override
    public int getRequestType() {
        return CURSOR_REQUEST;
    }

    @Op
    public abstract int operation();

    public abstract Uri uri();

    @Nullable
    public abstract List<String> projection();

    @Nullable
    public abstract String selection();

    @Nullable
    public abstract List<String> selectionArgs();

    @Nullable
    public abstract String sortOrder();

    @Nullable
    public abstract ContentValues contentValues();

    public static Builder newBuilder() {
        return new AutoParcel_CursorRequest.Builder();
    }

    @AutoParcel.Builder
    public abstract static class Builder {

        public abstract Builder operation(@Op int operation);

        public Builder select() {
            operation(SELECT);
            return this;
        }

        public Builder insert() {
            operation(INSERT);
            return this;
        }

        public Builder update() {
            operation(UPDATE);
            return this;
        }

        public Builder delete() {
            operation(DELETE);
            return this;
        }

        public abstract Builder uri(Uri uri);

        public abstract Builder projection(@Nullable List<String> projection);

        public Builder projection(@Nullable String[] projection) {
            projection(Arrays.asList(projection));
            return this;
        }

        public abstract Builder selection(@Nullable String selection);

        public abstract Builder selectionArgs(@Nullable List<String> selectionArgs);

        public Builder selectionArgs(@Nullable String[] selectionArgs) {
            selectionArgs(Arrays.asList(selectionArgs));
            return this;
        }

        public abstract Builder sortOrder(@Nullable String sortOrder);

        public abstract Builder contentValues(@Nullable ContentValues contentValues);

        public abstract CursorRequest build();

//        auto-parcel 0.3 does not support query methods in builders
//        abstract List<String> projection();
//
//        abstract Builder hasProjection(boolean has);
//
//        abstract List<String> selectionArgs();
//
//        abstract Builder hasSelectionArgs(boolean has);
//
//        public CursorRequest validateAndBuild() {
//            hasProjection(projection() != null);
//            hasSelectionArgs(selectionArgs() != null);
//            return build();
//        }

        /**
         * @param context
         * @param reason
         */
        public void startRequest(@NonNull Context context, @NonNull String reason, CursorListener listener) {
            build().startRequest(context, reason, listener);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SELECT, INSERT, UPDATE, DELETE})
    public @interface Op {}

    public static final int SELECT = 0;
    public static final int INSERT = 1;
    public static final int UPDATE = 1 << 1;
    public static final int DELETE = 1 << 2;

    /**
     * @param context
     * @param reason
     * @param listener
     */
    public void startRequest(Context context, String reason, CursorListener listener) {
        // begin handshake
        addFilter(new CursorEvent(this, listener));
        super.startRequest(context, reason);
    }
}
