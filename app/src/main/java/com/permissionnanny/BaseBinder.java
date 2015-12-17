package com.permissionnanny;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import com.permissionnanny.dagger.ContextComponent;

/**
 * The root of all Binders.
 */
public class BaseBinder {

    private ContextComponent mComponent;

    @VisibleForTesting
    protected BaseBinder(ContextComponent component) {
        mComponent = component;
    }

    public ContextComponent getComponent(Context context) {
        if (mComponent == null) {
            mComponent = App.newContextComponent(context);
        }
        return mComponent;
    }
}
