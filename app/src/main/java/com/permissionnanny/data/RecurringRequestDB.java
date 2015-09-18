package com.permissionnanny.data;

import com.permissionnanny.lib.request.RequestParams;
import io.snapdb.CryIterator;
import io.snapdb.SnapDB;

/**
 */
public class RecurringRequestDB {

    private SnapDB mDB;

    public RecurringRequestDB(SnapDB db) {
        mDB = db;
    }

    public void open() {
        mDB.open();
    }

    public void putRecurringRequest(String clientId, RequestParams request) {
        mDB.put(clientId, request);
    }

    public CryIterator<? extends RequestParams> getRecurringRequests() {
        return mDB.iterator(RequestParams.class);
    }

    public void delRecurringRequest(String clientId) {
        mDB.del(clientId);
    }
}
