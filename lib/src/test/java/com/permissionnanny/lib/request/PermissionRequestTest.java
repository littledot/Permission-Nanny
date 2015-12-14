package com.permissionnanny.lib.request;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.common.test.NannyTestRunner;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyLibTestRunner;
import com.permissionnanny.lib.request.simple.SimpleListener;
import com.permissionnanny.lib.request.simple.SimpleRequest;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(NannyLibTestRunner.class)
public class PermissionRequestTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyTestRunner.newTestRules(this);

    SimpleRequest target;
    RequestParams requestParams;
    @Mock SimpleListener listener;
    @Mock Context ctx;
    @Mock PackageManager pm;
    @Mock ApplicationInfo appInfo;
    @Captor ArgumentCaptor<Intent> intentCaptor;
    @Captor ArgumentCaptor<Bundle> bundleCaptor;
    @Captor ArgumentCaptor<IntentFilter> filterCaptor;

    @Before
    public void setUp() throws Exception {
        requestParams = new RequestParams();
        target = new SimpleRequest(requestParams);
    }

    @Test
    public void startRequest_ShouldSendBroadcastWithExpectedParameters() throws Exception {
        when(ctx.getPackageManager()).thenReturn(pm);
        when(pm.getApplicationInfo(anyString(), anyInt())).thenReturn(appInfo);
        target.listener(listener);

        target.startRequest(ctx, "rationale");

        verify(ctx).registerReceiver((BroadcastReceiver) notNull(), filterCaptor.capture());
        verify(ctx).sendBroadcast(intentCaptor.capture());
        Intent request = intentCaptor.getValue();
        assertThat(request.getComponent().getPackageName(), is(Nanny.SERVER_APP_ID));
        assertThat(request.getStringExtra(Nanny.CLIENT_ADDRESS), is(filterCaptor.getValue().getAction(0)));
        assertThat(request.hasExtra(Nanny.PROTOCOL_VERSION), is(true));
        Bundle entity = request.getBundleExtra(Nanny.ENTITY_BODY);
        assertThat(entity.containsKey(Nanny.SENDER_IDENTITY), is(true));
        assertThat(entity.containsKey(Nanny.REQUEST_PARAMS), is(true));
        assertThat(entity.containsKey(Nanny.REQUEST_RATIONALE), is(true));
    }

    @Test
    public void startRequest_ShouldNotSendClientAddress_WhenNoListenerIsAttached() throws Exception {
        when(ctx.getPackageManager()).thenReturn(pm);
        when(pm.getApplicationInfo(anyString(), anyInt())).thenReturn(appInfo);

        target.startRequest(ctx, null);

        verify(ctx, never()).registerReceiver((BroadcastReceiver) any(), (IntentFilter) any());
        verify(ctx).sendBroadcast(intentCaptor.capture());
        assertThat(intentCaptor.getValue().hasExtra(Nanny.CLIENT_ADDRESS), is(false));
    }
}
