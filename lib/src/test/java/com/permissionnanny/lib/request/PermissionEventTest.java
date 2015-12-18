package com.permissionnanny.lib.request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.common.test.NannyTestRunner;
import com.permissionnanny.lib.NannyLibTestRunner;
import com.permissionnanny.lib.request.simple.SimpleListener;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(NannyLibTestRunner.class)
public class PermissionEventTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyTestRunner.newTestRules(this);

    PermissionEvent mEventFilter;
    Intent mIntent;
    @Mock SimpleListener mSimpleListener;
    @Mock Context mContext;
    @Captor ArgumentCaptor<Bundle> mBundleCaptor;

    @Before
    public void setUp() throws Exception {
        mEventFilter = new PermissionEvent(mSimpleListener);
        mIntent = new Intent();
    }

    @Test
    public void process() throws Exception {
        mIntent.putExtra("key", "val");

        mEventFilter.process(mContext, mIntent);

        verify(mSimpleListener).onResponse(mBundleCaptor.capture());
        assertThat(mBundleCaptor.getValue().getString("key"), is("val"));
    }
}
