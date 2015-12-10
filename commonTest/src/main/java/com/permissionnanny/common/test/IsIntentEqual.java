package com.permissionnanny.common.test;

import android.content.Intent;
import android.support.annotation.Nullable;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 *
 */
public class IsIntentEqual extends BaseMatcher<Intent> {

    private Intent mExpected;
    private IsBundleEqual mBundleMatcher;

    public IsIntentEqual(@Nullable Intent expected) {
        mExpected = expected;
        if (mExpected != null) {
            mBundleMatcher = new IsBundleEqual(expected.getExtras());
        }
    }

    @Override
    public boolean matches(Object actual) {
        if (mExpected == null) {
            return actual == null;
        }

        // Type must match
        if (!(actual instanceof Intent)) {
            return false;
        }

        // Bundle must match
        Intent actualIntent = (Intent) actual;
        if (!mBundleMatcher.matches(actualIntent.getExtras())) {
            return false;
        }

        // Metadata & extra data must match
        return mExpected.filterEquals(actualIntent) && mExpected.equals(actualIntent);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(mExpected);
    }
}
