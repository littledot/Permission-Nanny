package com.permissionnanny.lib.request.simple;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.os.Bundle;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.common.test.NannyTestRunner;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyLibTestRunner;
import com.permissionnanny.lib.request.Ack;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

@RunWith(NannyLibTestRunner.class)
public class GpsStatusEventTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyTestRunner.newTestRules(this);

    GpsStatusEvent mRoute;
    Intent mIntent;
    Bundle mBundle;
    @Mock GpsStatus.Listener mListener;
    @Mock Ack mAck;
    @Mock Context mContext;

    @Before
    public void setUp() throws Exception {
        mRoute = new GpsStatusEvent(mListener, mAck);
        mIntent = new Intent();
        mBundle = new Bundle();
    }

    @Test
    public void process() throws Exception {
        mBundle.putInt(GpsStatusEvent.EVENT, 123);
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);

        mRoute.process(mContext, mIntent);

        verify(mAck).sendAck(mContext, mIntent);
        verify(mListener).onGpsStatusChanged(123);
    }
}
