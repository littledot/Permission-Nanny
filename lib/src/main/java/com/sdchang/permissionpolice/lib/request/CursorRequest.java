package com.sdchang.permissionpolice.lib.request;

import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import auto.parcel.AutoParcel;

import java.util.Arrays;
import java.util.List;

@AutoParcel
public abstract class CursorRequest extends BaseRequest {

    public static final ClassLoader classLoader = AutoParcel_CursorRequest.class.getClassLoader();

    public static Builder newBuilder() {
        return new AutoParcel_CursorRequest.Builder();
    }

    @Override
    public int getRequestType() {
        return CURSOR_REQUEST;
    }

    public void startRequest(Context context, String reason, CursorListener listener) {
        // begin handshake
        context.registerReceiver(new CursorRequestPermissionReceiver(this, listener), new IntentFilter("ppResult"));
        context.sendBroadcast(newIntent(context, reason));
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
    public abstract static class Builder {

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
}
