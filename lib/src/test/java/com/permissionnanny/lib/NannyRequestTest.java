package com.permissionnanny.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.common.test.NannyTestRunner;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(NannyLibTestRunner.class)
//@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest(Nanny.class)
public class NannyRequestTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyTestRunner.newTestRules(this);

    private NannyRequest mTarget;
    private Intent mIntent;
    @Mock private Event mEvent;
    @Mock private Context mContext;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Nanny.class);
        mTarget = new MockNannyRequest();
        mIntent = new Intent();
    }

    @Test
    public void addFilter_shouldInitPermissionReceiver() throws Exception {
        mTarget.addFilter(mEvent);

        assertThat(mTarget.hasReceiver(), is(true));
    }

    @Test
    public void addFilter_shouldDoNothing_whenEventIsNull() throws Exception {
        mTarget.addFilter(null);

        assertThat(mTarget.hasReceiver(), is(false));
    }

    @Test
    public void startRequest() throws Exception {
        when(Nanny.isPermissionNannyInstalled(mContext)).thenReturn(true);

        mTarget.addFilter(mEvent);
        mTarget.setPayload(mIntent);
        mTarget.startRequest(mContext);

        verify(mContext).registerReceiver((BroadcastReceiver) notNull(), (IntentFilter) notNull());
        verify(mContext).sendBroadcast(mIntent);
    }

    public static class MockNannyRequest extends NannyRequest {}
}
