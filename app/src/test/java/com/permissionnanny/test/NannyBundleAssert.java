package com.permissionnanny.test;

import android.content.Intent;
import android.os.Bundle;
import org.assertj.android.api.os.BundleAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class NannyBundleAssert extends BundleAssert {
    public NannyBundleAssert(Bundle actual) {
        super(actual);
    }

    public NannyBundleAssert hasExtras(Bundle expected) {
        isNotNull();

        // Size must match
        hasSize(expected.size());

        // Content must match
        for (String key : actual.keySet()) {
            assertThat(actual.get(key)).isEqualTo(expected.get(key));
        }
        return this;
    }

    public NannyBundleAssert hasExtras(Intent intent) {
        hasExtras(intent.getExtras());
        return this;
    }
}
