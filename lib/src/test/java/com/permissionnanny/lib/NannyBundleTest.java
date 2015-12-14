package com.permissionnanny.lib;

import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcelable;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.common.test.NannyTestRunner;
import com.permissionnanny.lib.request.RequestParams;
import java.io.Serializable;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(NannyLibTestRunner.class)
public class NannyBundleTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyTestRunner.newTestRules(this);

    private NannyBundle.Builder mBuilder;
    private Bundle mEntityBody;
    private NannyException mNannyException;
    private RequestParams mRequestParams;
    @Mock private PendingIntent mSender;
    @Mock private IntentSender mIntentSender;

    @Before
    public void setUp() throws Exception {
        when(mSender.getIntentSender()).thenReturn(mIntentSender);
        when(mIntentSender.getTargetPackage()).thenReturn("sender");
        mBuilder = new NannyBundle.Builder();
        mEntityBody = new Bundle();
        mNannyException = new NannyException("oops!");
        mRequestParams = new RequestParams();
    }

    @Test
    public void builder_shouldFormatBundleAccordingToPPP() throws Exception {
        mEntityBody.putString("customKey", "val");

        Bundle actual = simpleBundle();

        assertThat(actual.getString(Nanny.PROTOCOL_VERSION), is(Nanny.PPP_0_1));
        assertThat(actual.getInt(Nanny.STATUS_CODE), is(1));
        assertThat(actual.getString(Nanny.CLIENT_ADDRESS), is("a"));
        assertThat(actual.getString(Nanny.CONNECTION), is("b"));
        assertThat(actual.getString(Nanny.SERVER), is("c"));
        assertThat(actual.getSerializable(Nanny.ENTITY_ERROR), Matchers.<Serializable>sameInstance(mNannyException));

        Bundle actualBody = actual.getBundle(Nanny.ENTITY_BODY);
        assertThat(actualBody, notNullValue());
        assertThat(actualBody.getString("customKey"), is("val"));
        assertThat(actualBody.getParcelable(Nanny.SENDER_IDENTITY), Matchers.<Parcelable>sameInstance(mSender));
        assertThat(actualBody.getParcelable(Nanny.REQUEST_PARAMS), Matchers.<Parcelable>sameInstance(mRequestParams));
        assertThat(actualBody.getString(Nanny.REQUEST_RATIONALE), is("d"));
        assertThat(actualBody.getString(Nanny.REQUEST_REASON), is("d"));
        assertThat(actualBody.getString(Nanny.DEEP_LINK_TARGET), is("e"));
        assertThat(actualBody.getString(Nanny.ACK_SERVER_ADDRESS), is("f"));
    }

    @Test
    public void getMethods_shouldParseBundleAccordingToPPP() throws Exception {
        mEntityBody.putString("customKey", "val");

        NannyBundle actual = new NannyBundle(simpleBundle());

        assertThat(actual.getProtocol(), is(Nanny.PPP_0_1));
        assertThat(actual.getStatusCode(), is(1));
        assertThat(actual.getClientAddress(), is("a"));
        assertThat(actual.getConnection(), is("b"));
        assertThat(actual.getServer(), is("c"));
        assertThat(actual.getError(), Matchers.<Throwable>sameInstance(mNannyException));
        assertThat(actual.getEntityBody(), notNullValue());
        assertThat(actual.getEntityBody().getString("customKey"), is("val"));
        assertThat(actual.getSenderIdentity(), is("sender"));
        assertThat(actual.getRequest(), sameInstance(mRequestParams));
        assertThat(actual.getRequestRationale(), is("d"));
        assertThat(actual.getDeepLinkTarget(), is("e"));
        assertThat(actual.getAckAddress(), is("f"));
    }

    @Test
    public void getMethods_shouldHandleNullsGracefully() throws Exception {
        NannyBundle actual = new NannyBundle(mBuilder.build());

        assertThat(actual.getSenderIdentity(), nullValue());
        assertThat(actual.getRequest(), nullValue());
        assertThat(actual.getRequestRationale(), nullValue());
        assertThat(actual.getDeepLinkTarget(), is(""));
        assertThat(actual.getAckAddress(), nullValue());
    }

    @Test
    public void getRequestRationale_shouldTryRequeustReason_whenRequestRationaleIsNull() throws Exception {
        mEntityBody.putString(Nanny.REQUEST_REASON, "reason");

        NannyBundle actual = new NannyBundle(mBuilder.entity(mEntityBody).build());

        assertThat(actual.getRequestRationale(), is("reason"));
    }

    private Bundle simpleBundle() {
        return mBuilder.statusCode(1)
                .clientAddress("a")
                .connection("b")
                .server("c")
                .error(mNannyException)
                .entity(mEntityBody)
                .sender(mSender)
                .params(mRequestParams)
                .rationale("d")
                .deepLinkTarget("e")
                .ackAddress("f")
                .build();
    }
}
