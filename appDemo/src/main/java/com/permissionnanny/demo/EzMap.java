package com.permissionnanny.demo;

import android.support.v4.util.SimpleArrayMap;

/**
 *
 */
public class EzMap {
    private SimpleArrayMap<String, Object> mMap = new SimpleArrayMap<>();

    public EzMap put(String k, Object v) {
        mMap.put(k, v);
        return this;
    }

    public <T> T get(String k) {
        return (T) mMap.get(k);
    }
}
