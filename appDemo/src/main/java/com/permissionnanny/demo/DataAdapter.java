package com.permissionnanny.demo;

import android.os.Bundle;

public interface DataAdapter {
    void onResponse(int position, Bundle response);

    void onDisplay(int position, String data);
}