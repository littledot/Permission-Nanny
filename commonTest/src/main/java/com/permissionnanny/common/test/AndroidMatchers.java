package com.permissionnanny.common.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 *
 */
public class AndroidMatchers {

    public static IsIntentEqual equalToIntent(@Nullable Intent expected) {
        return new IsIntentEqual(expected);
    }

    public static IsBundleEqual equalToBundle(@Nullable Bundle expected) {
        return new IsBundleEqual(expected);
    }
}
