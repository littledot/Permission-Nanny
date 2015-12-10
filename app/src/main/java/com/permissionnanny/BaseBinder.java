package com.permissionnanny;

import android.content.Context;
import com.permissionnanny.dagger.ContextComponent;

/**
 * The root of all Binders.
 */
public class BaseBinder {

    private ContextComponent mComponent;

    public ContextComponent getComponent(Context context) {
        if (mComponent == null) {
            mComponent = App.newContextComponent(context);
        }
        return mComponent;
    }
}
