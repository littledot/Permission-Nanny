package com.permissionnanny.lib;

import android.content.Context;
import android.content.Intent;

public interface Event {
    String filter();

    void process(Context context, Intent intent);
}
