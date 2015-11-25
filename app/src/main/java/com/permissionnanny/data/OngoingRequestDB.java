package com.permissionnanny.data;

import android.support.v4.util.ArrayMap;
import com.permissionnanny.lib.request.RequestParams;

/**
 */
public class OngoingRequestDB {

    private final NannyDB mDB;

    public OngoingRequestDB(NannyDB db) {
        mDB = db;
    }

    public void open() {
        mDB.open();
    }

    public void putOngoingRequest(String clientId, RequestParams request) {
        mDB.put(clientId, request);
    }

    public ArrayMap<String, RequestParams> getOngoingRequests() {
        return mDB.findVal(null, RequestParams.class);
    }

    public void delOngoingRequest(String clientId) {
        mDB.del(clientId);
    }
}
