package com.sdchang.permissionpolice.dagger;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.location.Criteria;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sdchang.permissionpolice.App;
import com.sdchang.permissionpolice.C;
import com.sdchang.permissionpolice.MySnappy;
import com.sdchang.permissionpolice.lib.request.RequestParams;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import dagger.Module;
import dagger.Provides;
import net.engio.mbassy.bus.MBassador;
import timber.log.Timber;

import javax.inject.Singleton;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Module
public class AppModule {

    App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    Context provideContext() {
        return mApp;
    }

    public static class Bus extends MBassador<Object> {}

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    Kryo providesKryo() {
        Kryo kryo = new Kryo();
        kryo.addDefaultSerializer(RequestParams.class,
                new Serializer<RequestParams>() {
                    @Override
                    public void write(Kryo kryo, Output out, RequestParams o) {
                        // TODO #35: How to maintain backwards compatibility?
                        kryo.register(ArrayList.class);
                        kryo.register(Bundle.class);
                        kryo.register(ContentValues.class);
                        kryo.register(Criteria.class);
                        kryo.register(InetAddress.class);
                        kryo.register(List.class);
                        kryo.register(PendingIntent.class);
                        kryo.register(Uri.class);
                        kryo.register(WifiConfiguration.class);

                        out.writeString(o.opCode());
                        out.writeBoolean(o.boolean0());
                        kryo.writeObjectOrNull(out, o.byteArray0(), byte[].class);
                        out.writeDouble(o.double0());
                        out.writeDouble(o.double1());
                        out.writeFloat(o.float0());
                        out.writeInt(o.int0());
                        out.writeLong(o.long0());
                        out.writeShort(o.short0());
                        kryo.writeObjectOrNull(out, o.arrayListOfPendingIntents0(), ArrayList.class);
                        kryo.writeObjectOrNull(out, o.arrayListOfPendingIntents1(), ArrayList.class);
                        kryo.writeObjectOrNull(out, o.arrayListOfStrings0(), ArrayList.class);
                        kryo.writeObjectOrNull(out, o.bundle0(), Bundle.class);
                        kryo.writeObjectOrNull(out, o.contentValues(), ContentValues.class);
                        kryo.writeObjectOrNull(out, o.criteria0(), Criteria.class);
                        kryo.writeObjectOrNull(out, o.inetAddress0(), InetAddress.class);
                        kryo.writeObjectOrNull(out, o.listOfStrings0(), List.class);
                        kryo.writeObjectOrNull(out, o.listOfStrings1(), List.class);
                        kryo.writeObjectOrNull(out, o.pendingIntent0(), PendingIntent.class);
                        kryo.writeObjectOrNull(out, o.pendingIntent1(), PendingIntent.class);
                        out.writeString(o.string0());
                        out.writeString(o.string1());
                        out.writeString(o.string2());
                        kryo.writeObjectOrNull(out, o.uri0(), Uri.class);
                        kryo.writeObjectOrNull(out, o.wifiConfiguration0(), WifiConfiguration.class);
                    }

                    @Override
                    public RequestParams read(Kryo kryo, Input in, Class<RequestParams> type) {
                        return RequestParams.newBuilder()
                                .opCode(in.readString())
                                .boolean0(in.readBoolean())
                                .byteArray0(kryo.readObjectOrNull(in, byte[].class))
                                .double0(in.readDouble())
                                .double1(in.readDouble())
                                .float0(in.readFloat())
                                .int0(in.readInt())
                                .long0(in.readLong())
                                .short0(in.readShort())
                                .arrayListOfPendingIntents0(kryo.readObjectOrNull(in, ArrayList.class))
                                .arrayListOfPendingIntents1(kryo.readObjectOrNull(in, ArrayList.class))
                                .arrayListOfStrings0(kryo.readObjectOrNull(in, ArrayList.class))
                                .bundle0(kryo.readObjectOrNull(in, Bundle.class))
                                .contentValues(kryo.readObjectOrNull(in, ContentValues.class))
                                .criteria0(kryo.readObjectOrNull(in, Criteria.class))
                                .inetAddress0(kryo.readObjectOrNull(in, InetAddress.class))
                                .listOfStrings0(kryo.readObjectOrNull(in, List.class))
                                .listOfStrings1(kryo.readObjectOrNull(in, List.class))
                                .pendingIntent0(kryo.readObjectOrNull(in, PendingIntent.class))
                                .pendingIntent1(kryo.readObjectOrNull(in, PendingIntent.class))
                                .string0(in.readString())
                                .string1(in.readString())
                                .string2(in.readString())
                                .uri0(kryo.readObjectOrNull(in, Uri.class))
                                .wifiConfiguration0(kryo.readObjectOrNull(in, WifiConfiguration.class))
                                .build();
                    }
                });
        return kryo;
    }

    @Provides
    @Singleton
    @Type(C.TYPE_ONGOING_REQUESTS)
    MySnappy provideMySnappy(Context context, Kryo kryo) {
        try {
            // TODO #36: Migrate to android-leveldb
            return new MySnappy(DBFactory.open(context, kryo));
        } catch (SnappydbException e) {
            Timber.e(e, "SnappyDB TYPE_ONGOING_REQUESTS:");
        }
        return null;
    }

    @Provides
    @Singleton
    @Type(C.TYPE_APP_PERMISSION_CONFIG)
    MySnappy providePermissionUsageDatabase(Context context, Kryo kryo) {
        try {
            // TODO #36: Migrate to android-leveldb
            return new MySnappy(DBFactory.open(context, "clientPermissionUsage", kryo));
        } catch (SnappydbException e) {
            Timber.e(e, "SnappyDB TYPE_APP_PERMISSION_CONFIG:");
        }
        return null;
    }
}
