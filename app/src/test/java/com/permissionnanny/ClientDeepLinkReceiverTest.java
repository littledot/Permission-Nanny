package com.permissionnanny;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.missioncontrol.AppControlActivity;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static com.permissionnanny.common.test.AndroidMatchers.equalToIntent;
import static com.permissionnanny.common.test.Mockingbird.mockPendingIntent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(NannyAppTestRunner.class)
public class ClientDeepLinkReceiverTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    ClientDeepLinkReceiver mReceiver;
    Intent mIntent;
    Bundle mBundle;
    @Mock Context mContext;
    @Mock PendingIntent mPendingIntent;
    @Captor ArgumentCaptor<Intent> mIntentCaptor;

    @Before
    public void setUp() throws Exception {
        mockPendingIntent(mPendingIntent, "senderPkg");
        mReceiver = new ClientDeepLinkReceiver();
        mIntent = new Intent();
        mBundle = new Bundle();
    }

    @Test
    public void onReceive_shouldStartActivityAndReturn200() throws Exception {
        mBundle.putParcelable(Nanny.SENDER_IDENTITY, mPendingIntent);
        mBundle.putString(Nanny.DEEP_LINK_TARGET, Nanny.MANAGE_APPLICATIONS_SETTINGS);
        mIntent.putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.CLIENT_ADDRESS, "clientAddr")
                .putExtra(Nanny.ENTITY_BODY, mBundle);

        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).startActivity(mIntentCaptor.capture());
        Intent capturedIntent = mIntentCaptor.getAllValues().get(0);
        assertThat(capturedIntent.getComponent().getClassName(), is(AppControlActivity.class.getName()));
        assertThat(capturedIntent.getFlags(), is(Intent.FLAG_ACTIVITY_NEW_TASK));

        verify(mContext).sendBroadcast(mIntentCaptor.capture());
        capturedIntent = mIntentCaptor.getAllValues().get(1);
        assertThat(capturedIntent, equalToIntent(AppTestUtil.new200AuthServiceResponse("clientAddr")));
    }

    @Test
    public void onReceive_shouldReturn400_whenNoSenderIdentity() throws Exception {
        mBundle.putString(Nanny.DEEP_LINK_TARGET, Nanny.MANAGE_APPLICATIONS_SETTINGS);
        mIntent.putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.CLIENT_ADDRESS, "clientAddr")
                .putExtra(Nanny.ENTITY_BODY, mBundle);

        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).sendBroadcast(mIntentCaptor.capture());
        Intent capturedIntent = mIntentCaptor.getValue();
        assertThat(capturedIntent, equalToIntent(AppTestUtil.new400AuthServiceResponse(
                "clientAddr", Err.NO_SENDER_IDENTITY)));
    }

    @Test
    public void onReceive_shouldReturn400_whenNoDeepLinkTarget() throws Exception {
        mBundle.putParcelable(Nanny.SENDER_IDENTITY, mPendingIntent);
        mIntent.putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.CLIENT_ADDRESS, "clientAddr")
                .putExtra(Nanny.ENTITY_BODY, mBundle);

        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).sendBroadcast(mIntentCaptor.capture());
        Intent capturedIntent = mIntentCaptor.getValue();
        assertThat(capturedIntent, equalToIntent(AppTestUtil.new400AuthServiceResponse(
                "clientAddr", new NannyException(Err.UNSUPPORTED_DEEP_LINK_TARGET, ""))));
    }
}
