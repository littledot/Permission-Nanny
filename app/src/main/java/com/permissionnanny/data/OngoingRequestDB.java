package com.permissionnanny.data;

import com.permissionnanny.lib.request.RequestParams;
import io.snapdb.CryIterator;
import io.snapdb.SnapDB;

/**
 */
public class OngoingRequestDB {

    private SnapDB mDB;

    public OngoingRequestDB(SnapDB db) {
        mDB = db;
    }

    public void open() {
        mDB.open();
    }

    public void putOngoingRequest(String clientId, RequestParams request) {
        mDB.put(clientId, request);
    }

    public CryIterator<? extends RequestParams> getOngoingRequests() {
        return mDB.iterator(RequestParams.class);
    }

    public void delOngoingRequest(String clientId) {
        mDB.del(clientId);
    }
}
