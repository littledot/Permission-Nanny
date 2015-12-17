package com.permissionnanny;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.dagger.MockComponentFactory;
import com.permissionnanny.dagger.MockContextComponent;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyException;
import java.util.ArrayList;
import javax.inject.Inject;
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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(NannyAppTestRunner.class)
public class ClientPermissionManifestReceiverTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    ClientPermissionManifestReceiver mReceiver;
    Intent mIntent;
    Bundle mBundle;
    ArrayList<String> mManifest;
    @Mock Context mContext;
    @Mock PendingIntent mSender;
    @Inject AppPermissionManager mAppPermissionManager;
    @Captor ArgumentCaptor<Intent> mResponseCaptor;

    @Before
    public void setUp() throws Exception {
        MockContextComponent component = MockComponentFactory.getContextComponent();
        component.inject(this);
        mReceiver = new ClientPermissionManifestReceiver();
        mReceiver.setComponent(component);
        mIntent = new Intent();
        mBundle = new Bundle();
        mManifest = new ArrayList<>();
    }

    @Test
    public void onReceiveShouldSaveClientPermissionManifest() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);
        mBundle.putParcelable(Nanny.SENDER_IDENTITY, mSender);
        mockPendingIntent(mSender, "com.3rd.party.app");
        mBundle.putStringArrayList(Nanny.PERMISSION_MANIFEST, mManifest);

        mReceiver.onReceive(mContext, mIntent);

        verify(mReceiver.mAppManager).readPermissionManifest("com.3rd.party.app", mManifest);
        verify(mContext).sendBroadcast(mResponseCaptor.capture());
        assertThat(mResponseCaptor.getValue(), equalToIntent(AppTestUtil.new200Response("123")));
    }

    @Test
    public void onReceiveShouldReturn400WhenEntityBodyIsMissing() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");

        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).sendBroadcast(mResponseCaptor.capture());
        assertThat(mResponseCaptor.getValue(), equalToIntent(new400Response("123", Err.NO_ENTITY)));
    }

    @Test
    public void onReceiveShouldReturn400WhenSenderIdentityIsMissing() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);

        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).sendBroadcast(mResponseCaptor.capture());
        assertThat(mResponseCaptor.getValue(), equalToIntent(new400Response("123", Err.NO_SENDER_IDENTITY)));
    }

    @Test
    public void onReceiveShouldReturn400WhenPermissionManifestIsMissing() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);
        mBundle.putParcelable(Nanny.SENDER_IDENTITY, mSender);
        mockPendingIntent(mSender, "com.3rd.party.app");

        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).sendBroadcast(mResponseCaptor.capture());
        assertThat(mResponseCaptor.getValue(), equalToIntent(new400Response("123", Err.NO_PERMISSION_MANIFEST)));
    }

    private Intent new400Response(String clientAddr, String error) {
        return AppTestUtil.new400Response(clientAddr, Nanny.PERMISSION_MANIFEST_SERVICE, new NannyException(error));
    }
}
