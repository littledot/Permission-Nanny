package com.sdchang.permissionpolice.request;

import android.net.Uri;
import android.support.annotation.Nullable;
import auto.parcel.AutoParcel;

import java.util.Arrays;
import java.util.List;

@AutoParcel
public abstract class CursorRequest extends BaseRequest {

    public static Builder newBuilder() {
        return new AutoParcel_CursorRequest.Builder();
    }

    @Override
    public int getRequestType() {
        return CURSOR_REQUEST;
    }

    public abstract Uri uri();

    @Nullable
    public abstract List<String> projection();

    @Nullable
    public abstract String selection();

    @Nullable
    public abstract List<String> selectionArgs();

    @Nullable
    public abstract String sortOrder();

    @AutoParcel.Builder
    public static abstract class Builder {

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

        public abstract CursorRequest build();
    }
}
