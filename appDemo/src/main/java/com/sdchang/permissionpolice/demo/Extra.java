package com.sdchang.permissionpolice.demo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public interface Extra<T> {
    View getView(Context context, ViewGroup parent);

    T getValue();
}
