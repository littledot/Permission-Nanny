package com.sdchang.permissionpolice;

import com.snappydb.DB;
import com.snappydb.KeyIterator;
import com.snappydb.SnappydbException;
import timber.log.Timber;

/**
 *
 */
public class MySnappy {
    DB mDB;

    public MySnappy(DB db) {
        mDB = db;
    }

    public <T> T get(String key, Class<T> type) {
        try {
            return mDB.getObject(key, type);
        } catch (SnappydbException e) {
            Timber.e(e, "snappy.get()");
            return null;
        }
    }

    public void put(String key, Object val) {
        try {
            mDB.put(key, val);
        } catch (SnappydbException e) {
            Timber.e(e, "snappy.put()");
        }
    }

    public void del(String key) {
        try {
            mDB.del(key);
        } catch (SnappydbException e) {
            Timber.e(e, "snappy.del()");
        }
    }

    public KeyIterator iterator() {
        try {
            return mDB.allKeysIterator();
        } catch (SnappydbException e) {
            Timber.e(e, "snappy.all()");
        }
        return null;
    }

    public String[] allKeys() {
        KeyIterator iterator = iterator();
        if (iterator.hasNext()) {
            return iterator.next(Integer.MAX_VALUE);
        }
        return new String[0];
    }
}
