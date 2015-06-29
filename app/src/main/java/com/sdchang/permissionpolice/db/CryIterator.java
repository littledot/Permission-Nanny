package com.sdchang.permissionpolice.db;

import com.litl.leveldb.Iterator;

/**
 *
 */
public class CryIterator<T> {
    private Cryo mKryo;
    private Iterator mIterator;
    private Class<T> mType;

    private int mPos;

    public CryIterator(Cryo kryo, Iterator iterator, Class<T> type) {
        mKryo = kryo;
        mIterator = iterator;
        mType = type;
        mPos = -1;
    }

    public boolean moveToNext() {
        if (-1 == mPos++) {
            mIterator.seekToFirst();
            return mIterator.isValid();
        }
        mIterator.next();
        return mIterator.isValid();
    }

    public String key() {
        byte[] bytes = mIterator.getKey();
        return new String(bytes);
    }

    public T val() {
        byte[] bytes = mIterator.getValue();
        return mKryo.deserialize(bytes, mType);
    }

    public void close() {
        mIterator.close();
    }
}
