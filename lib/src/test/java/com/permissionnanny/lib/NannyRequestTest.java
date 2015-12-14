package com.permissionnanny.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.common.test.NannyTestRunner;
import com.permissionnanny.lib.request.PermissionEvent;
import com.permissionnanny.lib.request.simple.SimpleListener;
import java.util.concurrent.CountDownLatch;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(NannyLibTestRunner.class)
@PrepareForTest(Nanny.class)
public class NannyRequestTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyTestRunner.newTestRules(this);

    private NannyRequest mNannyRequest;
    private Intent mIntent;
    @Mock private Event mEvent;
    @Mock private Context mContext;
    @Captor private ArgumentCaptor<IntentFilter> mIntentFilterCaptor;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Nanny.class);
        mNannyRequest = new NannyRequest();
        mIntent = new Intent();
    }

    @Test
    public void addFilter_shouldInitPermissionReceiver() throws Exception {
        mNannyRequest.addFilter(mEvent);

        assertThat(mNannyRequest.hasReceiver(), is(true));
    }

    @Test
    public void addFilter_shouldDoNothing_whenEventIsNull() throws Exception {
        mNannyRequest.addFilter(null);

        assertThat(mNannyRequest.hasReceiver(), is(false));
    }

    @Test
    public void startRequest() throws Exception {
        when(Nanny.isPermissionNannyInstalled(mContext)).thenReturn(true);

        mNannyRequest.addFilter(mEvent);
        mNannyRequest.setPayload(mIntent);
        mNannyRequest.startRequest(mContext);

        verify(mContext).registerReceiver((BroadcastReceiver) notNull(), mIntentFilterCaptor.capture());
        assertThat(mIntentFilterCaptor.getValue().countActions(), is(1));
        assertThat(mIntentFilterCaptor.getValue().getAction(0), is(mNannyRequest.mClientAddr));
        verify(mContext).sendBroadcast(mIntent);
    }

    @Test(expected = IllegalStateException.class)
    public void startRequest_shouldThrow_whenNoPayload() throws Exception {
        when(Nanny.isPermissionNannyInstalled(mContext)).thenReturn(true);

        mNannyRequest.addFilter(mEvent);
        mNannyRequest.startRequest(mContext);
    }

    @Test
    public void stop() throws Exception {
        when(Nanny.isPermissionNannyInstalled(mContext)).thenReturn(true);

        mNannyRequest.addFilter(mEvent);
        mNannyRequest.setPayload(mIntent);
        mNannyRequest.startRequest(mContext);
        mNannyRequest.stop(mContext);

        verify(mContext).unregisterReceiver((BroadcastReceiver) notNull());
    }

    @Test
    public void startRequest_shouldReceive404_whenServerIsNotInstalled() throws Exception {
        when(Nanny.isPermissionNannyInstalled(mContext)).thenReturn(false);

        final CountDownLatch latch = new CountDownLatch(1);
        mNannyRequest.addFilter(new PermissionEvent(new SimpleListener() {
            @Override
            public void onResponse(@NonNull Bundle response) {
                assertThat(response.getInt(Nanny.STATUS_CODE), is(404));
                assertThat(response.getString(Nanny.SERVER), is(Nanny.AUTHORIZATION_SERVICE));
                NannyException error = (NannyException) response.getSerializable(Nanny.ENTITY_ERROR);
                assertThat(error.getMessage(), is("Permission Nanny is not installed."));
                latch.countDown();
            }
        }));
        mNannyRequest.setPayload(mIntent);
        mNannyRequest.startRequest(mContext);
        assertThat(latch.getCount(), is(0L));
    }
}
