package com.permissionnanny.common.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 *
 */
public class IsBundleEqual extends BaseMatcher<Bundle> {

    private final Bundle mExpected;

    public IsBundleEqual(@Nullable Bundle expected) {
        mExpected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        if (mExpected == null) {
            return actual == null;
        }

        // Type must match
        if (!(actual instanceof Bundle)) {
            return false;
        }
        Bundle actualBundle = (Bundle) actual;

        // Size must match
        if (mExpected.size() != actualBundle.size()) {
            return false;
        }

        // Content must match
        for (String key : mExpected.keySet()) {
            if (!TestUtil.deepEquals(mExpected.get(key), actualBundle.get(key))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(mExpected);
    }
}
