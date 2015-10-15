package com.permissionnanny.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.permissionnanny.lib.request.simple.SimpleListener;

/**
 *
 */
public class ResponseListener implements SimpleListener {
    private DataAdapter mAdapter;
    private int mPosition;

    public ResponseListener(int position, DataAdapter adapter) {
        mAdapter = adapter;
        mPosition = position;
    }

    @Override
    public void onResponse(@NonNull Bundle response) {
        mAdapter.onResponse(mPosition, response);
    }
}
