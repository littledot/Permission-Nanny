package com.permissionnanny.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.lib.request.simple.SimpleListener;

/**
 *
 */
public class ResponseDisplayListener implements SimpleListener {
    private DataAdapter mAdapter;
    private int mPosition;

    public ResponseDisplayListener(int position, DataAdapter adapter) {
        mAdapter = adapter;
        mPosition = position;
    }

    @Override
    public void onResponse(@NonNull Bundle response) {
        mAdapter.onResponse(mPosition, response);
        mAdapter.onData(mPosition, BundleUtil.toString(response));
    }
}
