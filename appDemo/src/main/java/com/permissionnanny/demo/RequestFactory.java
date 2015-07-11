package com.permissionnanny.demo;

import android.app.Dialog;
import android.content.Context;

/**
 *
 */
public interface RequestFactory {

    int getCount();

    String getLabel(int position);

    boolean hasExtras(int position);

    Dialog buildDialog(Context context, int position);
}
