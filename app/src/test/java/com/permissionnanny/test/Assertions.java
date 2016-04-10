package com.permissionnanny.test;

import android.content.Intent;
import android.os.Bundle;

public class Assertions {
    public static NannyIntentAssert assertThat(Intent actual) {
        return new NannyIntentAssert(actual);
    }

    public static NannyBundleAssert assertThat(Bundle actual) {
        return new NannyBundleAssert(actual);
    }
}
