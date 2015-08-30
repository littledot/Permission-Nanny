package com.permissionnanny.demo;

import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 *
 */
public class EzMap {
    private Map<String, Object> mMap = new ArrayMap<>();

    public EzMap put(String k, Object v) {
        mMap.put(k, v);
        return this;
    }

    public <T> T get(String k) {
        return (T) mMap.get(k);
    }
}
