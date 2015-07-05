package com.sdchang.permissionnanny.demo.extra;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public interface Extra<T> {
    View getView(Context context, ViewGroup parent, String label);

    T getValue();
}
