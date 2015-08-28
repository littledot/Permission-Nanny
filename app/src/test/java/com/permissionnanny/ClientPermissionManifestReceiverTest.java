package com.permissionnanny;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.missioncontrol.PermissionConfigDataManager;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;

import static com.permissionnanny.common.test.AndroidMatchers.*;
import static com.permissionnanny.common.test.Mockingbird.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(NannyAppTestRunner.class)
public class ClientPermissionManifestReceiverTest {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    ClientPermissionManifestReceiver target;
    Intent intent;
    Bundle bundle;
    ArrayList<String> manifest;
    @Mock Context context;
    @Mock PermissionConfigDataManager mgr;
    @Mock PendingIntent sender;
    @Captor ArgumentCaptor<Intent> responseCaptor;

    @Before
    public void setUp() throws Exception {
        target = new ClientPermissionManifestReceiver();
        target.mConfigManager = mgr;
        intent = new Intent();
        bundle = new Bundle();
        manifest = new ArrayList<>();
        when(context.getApplicationContext()).thenReturn(new RoboApp());
    }

    @Test
    public void onReceiveShouldSaveClientPermissionManifest() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        intent.putExtra(Nanny.ENTITY_BODY, bundle);
        bundle.putParcelable(Nanny.SENDER_IDENTITY, sender);
        mockPendingIntent(sender, "com.3rd.party.app");
        bundle.putStringArrayList(Nanny.PERMISSION_MANIFEST, manifest);

        target.onReceive(context, intent);

        verify(target.mConfigManager).registerApp("com.3rd.party.app", manifest);
        verify(context).sendBroadcast(responseCaptor.capture());
        assertThat(responseCaptor.getValue(), equalToIntent(newOkResponse("123")));
    }

    @Test
    public void onReceiveShouldReturnBadRequestWhenEntityBodyIsMissing() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");

        target.onReceive(context, intent);

        verify(context).sendBroadcast(responseCaptor.capture());
        assertThat(responseCaptor.getValue(), equalToIntent(newBadRequestResponse("123", Err.NO_ENTITY)));
    }

    @Test
    public void onReceiveShouldReturnBadRequestWhenSenderIdentityIsMissing() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        intent.putExtra(Nanny.ENTITY_BODY, bundle);

        target.onReceive(context, intent);

        verify(context).sendBroadcast(responseCaptor.capture());
        assertThat(responseCaptor.getValue(), equalToIntent(newBadRequestResponse("123", Err.NO_SENDER_IDENTITY)));
    }

    @Test
    public void onReceiveShouldReturnBadRequestWhenPermissionManifestIsMissing() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        intent.putExtra(Nanny.ENTITY_BODY, bundle);
        bundle.putParcelable(Nanny.SENDER_IDENTITY, sender);
        mockPendingIntent(sender, "com.3rd.party.app");

        target.onReceive(context, intent);

        verify(context).sendBroadcast(responseCaptor.capture());
        assertThat(responseCaptor.getValue(), equalToIntent(newBadRequestResponse("123", Err.NO_PERMISSION_MANIFEST)));
    }

    private Intent newBadRequestResponse(String clientAddr, String error) {
        return new Intent(clientAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.SERVER, Nanny.PERMISSION_MANIFEST_SERVICE)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_BAD_REQUEST)
                .putExtra(Nanny.CONNECTION, Nanny.CLOSE)
                .putExtra(Nanny.ENTITY_ERROR, new NannyException(error));
    }

    private Intent newOkResponse(String clientAddr) {
        return new Intent(clientAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.SERVER, Nanny.PERMISSION_MANIFEST_SERVICE)
                .putExtra(Nanny.STATUS_CODE, Nanny.SC_OK)
                .putExtra(Nanny.CONNECTION, Nanny.CLOSE);
    }
}
