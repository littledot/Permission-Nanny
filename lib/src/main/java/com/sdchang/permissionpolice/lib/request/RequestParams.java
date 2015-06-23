package com.sdchang.permissionpolice.lib.request;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.location.Criteria;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import auto.parcel.AutoParcel;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@AutoParcel
public abstract class RequestParams implements Parcelable {

    public static Builder newBuilder() {
        return new AutoParcel_RequestParams.Builder();
    }

    public abstract String opCode();

    @Nullable
    public abstract boolean boolean0();

    @Nullable
    public abstract byte[] byteArray0();

    @Nullable
    public abstract double double0();

    @Nullable
    public abstract double double1();

    @Nullable
    public abstract float float0();

    @Nullable
    public abstract int int0();

    @Nullable
    public abstract long long0();

    @Nullable
    public abstract short short0();

    @Nullable
    public abstract ArrayList<PendingIntent> arrayListOfPendingIntents0();

    @Nullable
    public abstract ArrayList<PendingIntent> arrayListOfPendingIntents1();

    @Nullable
    public abstract ArrayList<String> arrayListOfStrings0();

    @Nullable
    public abstract Bundle bundle0();

    @Nullable
    public abstract ContentValues contentValues();

    @Nullable
    public abstract Criteria criteria0();

    @Nullable
    public abstract InetAddress inetAddress0();

    @Nullable
    public abstract List<String> listOfStrings0();

    @Nullable
    public abstract List<String> listOfStrings1();

    @Nullable
    public abstract PendingIntent pendingIntent0();

    @Nullable
    public abstract PendingIntent pendingIntent1();

    @Nullable
    public abstract String string0();

    @Nullable
    public abstract String string1();

    @Nullable
    public abstract String string2();

    @Nullable
    public abstract Uri uri0();

    @Nullable
    public abstract WifiConfiguration wifiConfiguration0();

    @AutoParcel.Builder
    public static abstract class Builder {
        public abstract Builder opCode(String value);

        public abstract Builder boolean0(boolean value);

        public abstract Builder byteArray0(byte[] value);

        public abstract Builder double0(double value);

        public abstract Builder double1(double value);

        public abstract Builder float0(float value);

        public abstract Builder int0(int value);

        public abstract Builder long0(long value);

        public abstract Builder short0(short value);

        public abstract Builder arrayListOfPendingIntents0(ArrayList<PendingIntent> value);

        public abstract Builder arrayListOfPendingIntents1(ArrayList<PendingIntent> value);

        public abstract Builder arrayListOfStrings0(ArrayList<String> value);

        public abstract Builder bundle0(Bundle value);

        public abstract Builder contentValues(ContentValues value);

        public abstract Builder criteria0(Criteria value);

        public abstract Builder inetAddress0(InetAddress value);

        public abstract Builder listOfStrings0(List<String> value);

        public abstract Builder listOfStrings1(List<String> value);

        public abstract Builder pendingIntent0(PendingIntent value);

        public abstract Builder pendingIntent1(PendingIntent value);

        public abstract Builder string0(String value);

        public abstract Builder string1(String value);

        public abstract Builder string2(String value);

        public abstract Builder uri0(Uri value);

        public abstract Builder wifiConfiguration0(WifiConfiguration value);

        public abstract RequestParams build();
    }
}
