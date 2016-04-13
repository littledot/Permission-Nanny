package com.permissionnanny.lib.request;

import android.content.Context;
import android.content.Intent;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.NannyLibTestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class AckTest extends NannyLibTestCase {

    Ack mAck;
    Intent mIntent;
    NannyBundle.Builder mBuilder;
    @Mock Context mContext;
    @Captor ArgumentCaptor<Intent> mIntentCaptor;

    @Before
    public void setUp() throws Exception {
        mAck = new Ack();
        mIntent = new Intent();
        mBuilder = new NannyBundle.Builder();
    }

    @Test
    public void sendAck() throws Exception {
        mIntent.setAction("clientAddr")
                .putExtras(mBuilder.ackAddress("ackAddr").build());

        mAck.sendAck(mContext, mIntent);

        verify(mContext).sendBroadcast(mIntentCaptor.capture());
        assertThat(mIntentCaptor.getValue().getAction(), is("ackAddr"));
        assertThat(mIntentCaptor.getValue().getStringExtra(Nanny.PROTOCOL_VERSION), is(Nanny.PPP_0_1));
        assertThat(mIntentCaptor.getValue().getStringExtra(Nanny.CLIENT_ADDRESS), is("clientAddr"));
    }
}
