package com.sdchang.permissionpolice.db;

import android.support.v4.util.SimpleArrayMap;
import com.esotericsoftware.kryo.Kryo;
import com.litl.leveldb.DB;
import com.litl.leveldb.WriteBatch;

import javax.inject.Inject;
import java.nio.ByteBuffer;

/**
 *
 */
public class CryDB {

    private DB mDB;
    private Cryo mCry;

    @Inject
    public CryDB(DB db, Kryo kryo) {
        mDB = db;
        mCry = new Cryo(kryo);
    }

    public void open() {
        mDB.open();
    }

    public void close() {
        mDB.close();
    }

    public void destroy() {
        mDB.close();
        mDB.destroy();
    }

    public void put(String key, Object val) {
        byte[] bytes = mCry.serialize(val);
        mDB.put(key.getBytes(), bytes);
    }

    public <T> T get(String key, Class<T> type) {
        byte[] data = mDB.get(key.getBytes());
        return mCry.deserialize(data, type);
    }

    public <T> T get(String key) {
        byte[] data = mDB.get(key.getBytes());
        return mCry.deserialize(data);
    }

    public <V> void putAtomic(SimpleArrayMap<String, V> map) {
        WriteBatch op = new WriteBatch();

        for (int i = 0, len = map.size(); i < len; i++) {
            String key = map.keyAt(i);
            V val = map.valueAt(i);

            ByteBuffer keyBuf = ByteBuffer.wrap(key.getBytes());
            byte[] bytes = mCry.serialize(val);
            ByteBuffer valBuf = ByteBuffer.wrap(bytes);

            op.put(keyBuf, valBuf);
        }
        mDB.write(op);
    }

    public void del(String key) {
        mDB.delete(key.getBytes());
    }

    public <T> CryIterator<T> iterator(Class<T> type) {
        return new CryIterator<>(mCry, mDB.iterator(), type);
    }
}
