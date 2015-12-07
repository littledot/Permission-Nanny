package com.permissionnanny.lib.request;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.location.Criteria;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class RequestParams implements Parcelable {

    public String opCode;
    public boolean boolean0;
    public byte[] byteArray0;
    public double double0;
    public double double1;
    public float float0;
    public int int0;
    public long long0;
    public Account account0;
    public ArrayList<PendingIntent> arrayListOfPendingIntents0;
    public ArrayList<PendingIntent> arrayListOfPendingIntents1;
    public ArrayList<String> arrayListOfStrings0;
    public Bundle bundle0;
    public Bundle bundle1;
    public ContentValues contentValues0;
    public Criteria criteria0;
    public InetAddress inetAddress0;
    public List<String> listOfStrings0;
    public List<String> listOfStrings1;
    public PendingIntent pendingIntent0;
    public PendingIntent pendingIntent1;
    public String string0;
    public String string1;
    public String string2;
    public String[] stringArray0;
    public String[] stringArray1;
    public Uri uri0;
    public WifiConfiguration wifiConfiguration0;

    public RequestParams() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestParams that = (RequestParams) o;

        if (boolean0 != that.boolean0) return false;
        if (Double.compare(that.double0, double0) != 0) return false;
        if (Double.compare(that.double1, double1) != 0) return false;
        if (Float.compare(that.float0, float0) != 0) return false;
        if (int0 != that.int0) return false;
        if (long0 != that.long0) return false;
        if (opCode != null ? !opCode.equals(that.opCode) : that.opCode != null) return false;
        if (!Arrays.equals(byteArray0, that.byteArray0)) return false;
        if (account0 != null ? !account0.equals(that.account0) : that.account0 != null) return false;
        if (arrayListOfPendingIntents0 != null ? !arrayListOfPendingIntents0.equals(that.arrayListOfPendingIntents0)
                : that.arrayListOfPendingIntents0 != null)
            return false;
        if (arrayListOfPendingIntents1 != null ? !arrayListOfPendingIntents1.equals(that.arrayListOfPendingIntents1)
                : that.arrayListOfPendingIntents1 != null)
            return false;
        if (arrayListOfStrings0 != null ? !arrayListOfStrings0.equals(that.arrayListOfStrings0) : that
                .arrayListOfStrings0 != null)
            return false;
        if (bundle0 != null ? !bundle0.equals(that.bundle0) : that.bundle0 != null) return false;
        if (bundle1 != null ? !bundle1.equals(that.bundle1) : that.bundle1 != null) return false;
        if (contentValues0 != null ? !contentValues0.equals(that.contentValues0) : that.contentValues0 != null)
            return false;
        if (criteria0 != null ? !criteria0.equals(that.criteria0) : that.criteria0 != null) return false;
        if (inetAddress0 != null ? !inetAddress0.equals(that.inetAddress0) : that.inetAddress0 != null) return false;
        if (listOfStrings0 != null ? !listOfStrings0.equals(that.listOfStrings0) : that.listOfStrings0 != null)
            return false;
        if (listOfStrings1 != null ? !listOfStrings1.equals(that.listOfStrings1) : that.listOfStrings1 != null)
            return false;
        if (pendingIntent0 != null ? !pendingIntent0.equals(that.pendingIntent0) : that.pendingIntent0 != null)
            return false;
        if (pendingIntent1 != null ? !pendingIntent1.equals(that.pendingIntent1) : that.pendingIntent1 != null)
            return false;
        if (string0 != null ? !string0.equals(that.string0) : that.string0 != null) return false;
        if (string1 != null ? !string1.equals(that.string1) : that.string1 != null) return false;
        if (string2 != null ? !string2.equals(that.string2) : that.string2 != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(stringArray0, that.stringArray0)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(stringArray1, that.stringArray1)) return false;
        if (uri0 != null ? !uri0.equals(that.uri0) : that.uri0 != null) return false;
        return !(wifiConfiguration0 != null ? !wifiConfiguration0.equals(that.wifiConfiguration0) : that
                .wifiConfiguration0 != null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = opCode != null ? opCode.hashCode() : 0;
        result = 31 * result + (boolean0 ? 1 : 0);
        result = 31 * result + (byteArray0 != null ? Arrays.hashCode(byteArray0) : 0);
        temp = Double.doubleToLongBits(double0);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(double1);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (float0 != +0.0f ? Float.floatToIntBits(float0) : 0);
        result = 31 * result + int0;
        result = 31 * result + (int) (long0 ^ (long0 >>> 32));
        result = 31 * result + (account0 != null ? account0.hashCode() : 0);
        result = 31 * result + (arrayListOfPendingIntents0 != null ? arrayListOfPendingIntents0.hashCode() : 0);
        result = 31 * result + (arrayListOfPendingIntents1 != null ? arrayListOfPendingIntents1.hashCode() : 0);
        result = 31 * result + (arrayListOfStrings0 != null ? arrayListOfStrings0.hashCode() : 0);
        result = 31 * result + (bundle0 != null ? bundle0.hashCode() : 0);
        result = 31 * result + (bundle1 != null ? bundle1.hashCode() : 0);
        result = 31 * result + (contentValues0 != null ? contentValues0.hashCode() : 0);
        result = 31 * result + (criteria0 != null ? criteria0.hashCode() : 0);
        result = 31 * result + (inetAddress0 != null ? inetAddress0.hashCode() : 0);
        result = 31 * result + (listOfStrings0 != null ? listOfStrings0.hashCode() : 0);
        result = 31 * result + (listOfStrings1 != null ? listOfStrings1.hashCode() : 0);
        result = 31 * result + (pendingIntent0 != null ? pendingIntent0.hashCode() : 0);
        result = 31 * result + (pendingIntent1 != null ? pendingIntent1.hashCode() : 0);
        result = 31 * result + (string0 != null ? string0.hashCode() : 0);
        result = 31 * result + (string1 != null ? string1.hashCode() : 0);
        result = 31 * result + (string2 != null ? string2.hashCode() : 0);
        result = 31 * result + (stringArray0 != null ? Arrays.hashCode(stringArray0) : 0);
        result = 31 * result + (stringArray1 != null ? Arrays.hashCode(stringArray1) : 0);
        result = 31 * result + (uri0 != null ? uri0.hashCode() : 0);
        result = 31 * result + (wifiConfiguration0 != null ? wifiConfiguration0.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.opCode);
        dest.writeByte(boolean0 ? (byte) 1 : (byte) 0);
        dest.writeByteArray(this.byteArray0);
        dest.writeDouble(this.double0);
        dest.writeDouble(this.double1);
        dest.writeFloat(this.float0);
        dest.writeInt(this.int0);
        dest.writeLong(this.long0);
        dest.writeParcelable(this.account0, 0);
        dest.writeTypedList(arrayListOfPendingIntents0);
        dest.writeTypedList(arrayListOfPendingIntents1);
        dest.writeStringList(this.arrayListOfStrings0);
        dest.writeBundle(bundle0);
        dest.writeBundle(bundle1);
        dest.writeParcelable(this.contentValues0, 0);
        dest.writeParcelable(this.criteria0, 0);
        dest.writeSerializable(this.inetAddress0);
        dest.writeStringList(this.listOfStrings0);
        dest.writeStringList(this.listOfStrings1);
        dest.writeParcelable(this.pendingIntent0, 0);
        dest.writeParcelable(this.pendingIntent1, 0);
        dest.writeString(this.string0);
        dest.writeString(this.string1);
        dest.writeString(this.string2);
        dest.writeStringArray(this.stringArray0);
        dest.writeStringArray(this.stringArray1);
        dest.writeParcelable(this.uri0, 0);
        dest.writeParcelable(this.wifiConfiguration0, 0);
    }

    protected RequestParams(Parcel in) {
        this.opCode = in.readString();
        this.boolean0 = in.readByte() != 0;
        this.byteArray0 = in.createByteArray();
        this.double0 = in.readDouble();
        this.double1 = in.readDouble();
        this.float0 = in.readFloat();
        this.int0 = in.readInt();
        this.long0 = in.readLong();
        this.account0 = in.readParcelable(Account.class.getClassLoader());
        this.arrayListOfPendingIntents0 = in.createTypedArrayList(PendingIntent.CREATOR);
        this.arrayListOfPendingIntents1 = in.createTypedArrayList(PendingIntent.CREATOR);
        this.arrayListOfStrings0 = in.createStringArrayList();
        bundle0 = in.readBundle();
        bundle1 = in.readBundle();
        this.contentValues0 = in.readParcelable(ContentValues.class.getClassLoader());
        this.criteria0 = in.readParcelable(Criteria.class.getClassLoader());
        this.inetAddress0 = (InetAddress) in.readSerializable();
        this.listOfStrings0 = in.createStringArrayList();
        this.listOfStrings1 = in.createStringArrayList();
        this.pendingIntent0 = in.readParcelable(PendingIntent.class.getClassLoader());
        this.pendingIntent1 = in.readParcelable(PendingIntent.class.getClassLoader());
        this.string0 = in.readString();
        this.string1 = in.readString();
        this.string2 = in.readString();
        this.stringArray0 = in.createStringArray();
        this.stringArray1 = in.createStringArray();
        this.uri0 = in.readParcelable(Uri.class.getClassLoader());
        this.wifiConfiguration0 = in.readParcelable(WifiConfiguration.class.getClassLoader());
    }

    public static final Creator<RequestParams> CREATOR = new Creator<RequestParams>() {
        public RequestParams createFromParcel(Parcel source) {return new RequestParams(source);}

        public RequestParams[] newArray(int size) {return new RequestParams[size];}
    };
}
