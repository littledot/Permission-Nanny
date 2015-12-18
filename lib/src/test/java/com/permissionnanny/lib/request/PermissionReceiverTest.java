package com.permissionnanny.lib.request;

import android.content.Context;
import android.content.Intent;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.common.test.NannyTestRunner;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.NannyLibTestRunner;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(NannyLibTestRunner.class)
public class PermissionReceiverTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyTestRunner.newTestRules(this);

    PermissionReceiver mReceiver;
    Intent mIntent;
    @Mock Event mEventFilter;
    @Mock Context mContext;

    @Before
    public void setUp() throws Exception {
        when(mEventFilter.filter()).thenReturn("filter");
        mReceiver = new PermissionReceiver();
        mIntent = new Intent();
    }

    @Test
    public void onReceive() throws Exception {
        mIntent.putExtras(new NannyBundle.Builder().server("filter").build());

        mReceiver.addFilter(mEventFilter);
        mReceiver.onReceive(mContext, mIntent);

        verify(mEventFilter).process(mContext, mIntent);
    }

    @Test
    public void onReceive_shouldUnregisterReceiver_whenConnectionCloseIsSet() throws Exception {
        mIntent.putExtras(new NannyBundle.Builder().server("filter").connection(Nanny.CLOSE).build());

        mReceiver.addFilter(mEventFilter);
        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).unregisterReceiver(mReceiver);
    }
}
