package com.sdchang.permissionpolice.lib.request.wifi;

import android.os.Parcelable;
import auto.parcel.AutoParcel;

/**
 *
 */
@AutoParcel
public abstract class WifiManagerResponse implements Parcelable {
    public abstract int networkId();

    public abstract boolean success();

    public static WifiManagerResponse create(int networkId) {
        return new AutoParcel_WifiManagerResponse(networkId, false);
    }

    public static WifiManagerResponse create(boolean success) {
        return new AutoParcel_WifiManagerResponse(0, success);
    }
}
