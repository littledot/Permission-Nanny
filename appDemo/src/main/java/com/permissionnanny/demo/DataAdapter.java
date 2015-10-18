package com.permissionnanny.demo;

import android.os.Bundle;

public interface DataAdapter {
    void onResponse(int position, Bundle response);

    void onData(int position, String data);
}