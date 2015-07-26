package com.permissionnanny.lib.request;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.simple.SimpleListener;
import com.permissionnanny.lib.request.simple.SimpleRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class PermissionRequestTest {

    SimpleRequest target;
    @Mock SimpleListener listener;
    @Mock Context ctx;
    @Mock PackageManager pm;
    @Mock ApplicationInfo appInfo;
    @Captor ArgumentCaptor<Intent> intentCaptor;
    @Captor ArgumentCaptor<Bundle> bundleCaptor;
    @Captor ArgumentCaptor<IntentFilter> filter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        target = new SimpleRequest(null);
    }

    @Test
    public void startRequestShouldSendBroadcastWithExpectedParameters() throws Exception {
        when(ctx.getPackageManager()).thenReturn(pm);
        when(pm.getApplicationInfo(anyString(), anyInt())).thenReturn(appInfo);
        target.listener(listener);

        target.startRequest(ctx, null);

        verify(ctx).registerReceiver((BroadcastReceiver) notNull(), filter.capture());
        verify(ctx).sendBroadcast(intentCaptor.capture());
        Intent request = intentCaptor.getValue();
        assertThat(request.getComponent().getPackageName(), is(Nanny.SERVER_APP_ID));
        assertThat(request.getStringExtra(Nanny.CLIENT_ADDRESS), is(filter.getValue().getAction(0)));
        assertThat(request.hasExtra(Nanny.PROTOCOL_VERSION), is(true));
        Bundle entity = request.getBundleExtra(Nanny.ENTITY_BODY);
        assertThat(entity.containsKey(Nanny.SENDER_IDENTITY), is(true));
        assertThat(entity.containsKey(Nanny.REQUEST_PARAMS), is(true));
        assertThat(entity.containsKey(Nanny.REQUEST_REASON), is(true));
    }

    @Test
    public void startRequestShouldNotSendClientAddressWhenNoListenerIsAttached() throws Exception {
        when(ctx.getPackageManager()).thenReturn(pm);
        when(pm.getApplicationInfo(anyString(), anyInt())).thenReturn(appInfo);

        target.startRequest(ctx, null);

        verify(ctx, never()).registerReceiver((BroadcastReceiver) any(), (IntentFilter) any());
        verify(ctx).sendBroadcast(intentCaptor.capture());
        assertThat(intentCaptor.getValue().hasExtra(Nanny.CLIENT_ADDRESS), is(false));
    }

    @Test
    public void startRequestShouldReturn404AndNotSendBroadcastWhenServerIsNotInstalled() throws Exception {
        when(ctx.getPackageManager()).thenReturn(pm);
        target.listener(listener);

        target.startRequest(ctx, null);

        verify(listener).onResponse(bundleCaptor.capture());
        Bundle response = bundleCaptor.getValue();
        assertThat(response.getInt(Nanny.STATUS_CODE), is(Nanny.SC_NOT_FOUND));
        assertThat(response.getBundle(Nanny.ENTITY_BODY).getSerializable(Nanny.ENTITY_ERROR), not(nullValue()));
        verify(ctx, never()).registerReceiver((BroadcastReceiver) any(), (IntentFilter) any());
        verify(ctx, never()).sendBroadcast((Intent) any());
    }
}
