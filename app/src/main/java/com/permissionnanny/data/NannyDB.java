package com.permissionnanny.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;
import org.iq80.leveldb.impl.Iq80DBFactory;

/**
 *
 */
public class NannyDB {
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final File mFile;
    private final DB mDB;
    private final Cryo mCryo;

    public NannyDB(File file, Cryo cryo) throws IOException {
        mFile = file;
        Options opt = new Options();
        opt.createIfMissing(true);
        mDB = Iq80DBFactory.factory.open(file, opt);
        mCryo = cryo;
    }

    public void open() {/* No-op: Already open in constructor. */}

    public void close() throws IOException {
        mDB.close();
    }

    public void destroy() throws IOException {
        mDB.close();
        Iq80DBFactory.factory.destroy(mFile, null);
    }

    public <T> T get(@NonNull String key, @NonNull Class<T> type) {
        byte[] data = mDB.get(key.getBytes(UTF_8));
        return mCryo.deserialize(data, type);
    }

    public <T> T get(@NonNull String key) {
        byte[] data = mDB.get(key.getBytes(UTF_8));
        return mCryo.deserialize(data);
    }

    public void put(@NonNull String key, @NonNull Object val) {
        byte[] bytes = mCryo.serialize(val);
        mDB.put(key.getBytes(UTF_8), bytes);
    }

    public void del(@NonNull String key) {
        mDB.delete(key.getBytes(UTF_8));
    }

    public ArrayList<String> findKeys(@NonNull String startsWith) {
        DBIterator it = mDB.iterator();
        it.seek(startsWith.getBytes(UTF_8));
        ArrayList<String> list = new ArrayList<>();
        while (it.hasNext()) {
            list.add(new String(it.next().getKey()));
        }
        return list;
    }

    public <T> ArrayList<T> findVals(@Nullable String startsWith, @NonNull Class<T> type) {
        DBIterator it = mDB.iterator();
        if (!TextUtils.isEmpty(startsWith)) {
            it.seek(startsWith.getBytes(UTF_8));
        }

        ArrayList<T> list = new ArrayList<>();
        while (it.hasNext()) {
            byte[] value = it.next().getValue();
            T data = mCryo.deserialize(value, type);
            if (data != null) {
                list.add(data);
            }
        }
        return list;
    }

    public <V> ArrayMap<String, V> findVal(@Nullable String startsWith, @NonNull Class<V> type) {
        DBIterator it = mDB.iterator();
        if (!TextUtils.isEmpty(startsWith)) {
            it.seek(startsWith.getBytes());
        }

        ArrayMap<String, V> map = new ArrayMap<>();
        while (it.hasNext()) {
            Map.Entry<byte[], byte[]> next = it.next();
            map.put(new String(next.getKey()), mCryo.deserialize(next.getValue(), type));
        }
        return map;
    }

    public <V> void putAtomic(SimpleArrayMap<String, V> map) {
        WriteBatch op = mDB.createWriteBatch();

        for (int i = 0, len = map.size(); i < len; i++) {
            op.put(map.keyAt(i).getBytes(), mCryo.serialize(map.valueAt(i)));
        }
        mDB.write(op);
    }
}
