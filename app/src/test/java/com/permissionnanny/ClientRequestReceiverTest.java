package com.permissionnanny;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.common.test.Mockingbird;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.dagger.ContextComponent;
import com.permissionnanny.data.AppPermission;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.TelephonyRequest;
import com.permissionnanny.lib.request.simple.WifiRequest;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static com.permissionnanny.common.test.AndroidMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(NannyAppTestRunner.class)
public class ClientRequestReceiverTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    ClientRequestReceiver target;
    Intent intent;
    Bundle entity;
    RequestParams request;
    @Mock ContextComponent component;
    @Mock AppPermissionManager appManager;
    @Mock ProxyExecutor executor;
    @Mock Context context;
    @Mock PendingIntent sender;
    @Captor ArgumentCaptor<Intent> intentCaptor;

    @Before
    public void setUp() throws Exception {
        target = new ClientRequestReceiver();
        target.mAppManager = appManager;
        target.mExecutor = executor;
        intent = new Intent();
        entity = new Bundle();
        request = new RequestParams();
        when(context.getApplicationContext()).thenReturn(new RoboApp());
        Mockingbird.mockPendingIntent(sender, "3rd.party.app");
    }

    @Test
    public void onReceiveShouldExecuteProtectionNormalOperation() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        intent.putExtra(Nanny.ENTITY_BODY, entity);
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender);
        request.opCode = WifiRequest.GET_CONNECTION_INFO;
        entity.putParcelable(Nanny.REQUEST_PARAMS, request);

        target.onReceive(context, intent);

        verify(target.mExecutor).executeAllow((Operation) notNull(), same(request), eq("123"));
    }

    @Test
    public void onReceiveShouldStartDialogActivityWhenAlwaysAskProtectionDangerousOperation() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        intent.putExtra(Nanny.ENTITY_BODY, entity);
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender);
        request.opCode = TelephonyRequest.GET_DEVICE_ID;
        entity.putParcelable(Nanny.REQUEST_PARAMS, request);
        when(target.mAppManager.getPermissionPrivilege(eq("3rd.party.app"), (Operation) notNull(), same(request)))
                .thenReturn(AppPermission.ALWAYS_ASK);

        target.onReceive(context, intent);

        verify(context).startActivity(intentCaptor.capture());
        Intent dialogIntent = intentCaptor.getValue();
        assertThat(dialogIntent.getComponent().getClassName(), is(ConfirmRequestActivity.class.getName()));
        assertThat(dialogIntent.getExtras(), equalToBundle(dialogIntent.getExtras()));
    }

    @Test
    public void onReceiveShouldExecuteRequestWhenAlwaysAllowProtectionDangerousOperation() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        intent.putExtra(Nanny.ENTITY_BODY, entity);
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender);
        request.opCode = TelephonyRequest.GET_DEVICE_ID;
        entity.putParcelable(Nanny.REQUEST_PARAMS, request);
        when(target.mAppManager.getPermissionPrivilege(eq("3rd.party.app"), (Operation) notNull(), same(request)))
                .thenReturn(AppPermission.ALWAYS_ALLOW);

        target.onReceive(context, intent);

        verify(target.mExecutor).executeAllow((Operation) notNull(), same(request), eq("123"));
    }

    @Test
    public void onReceiveShouldDenyRequestWhenAlwaysDenyProtectionDangerousOperation() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        intent.putExtra(Nanny.ENTITY_BODY, entity);
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender);
        request.opCode = TelephonyRequest.GET_DEVICE_ID;
        entity.putParcelable(Nanny.REQUEST_PARAMS, request);
        when(target.mAppManager.getPermissionPrivilege(eq("3rd.party.app"), (Operation) notNull(), same(request)))
                .thenReturn(AppPermission.ALWAYS_DENY);

        target.onReceive(context, intent);

        verify(target.mExecutor).executeDeny((Operation) notNull(), same(request), eq("123"));
    }

    @Test
    public void onReceiveShouldReturn400WhenRequestedOperationIsUnsupported() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        intent.putExtra(Nanny.ENTITY_BODY, entity);
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender);
        request.opCode = "rm -rf / --no-preserve-root";
        entity.putParcelable(Nanny.REQUEST_PARAMS, request);

        target.onReceive(context, intent);

        verify(context).sendBroadcast(intentCaptor.capture());
        assertThat(intentCaptor.getValue(), equalToIntent(AppTestUtil.new400Response("123",
                Nanny.AUTHORIZATION_SERVICE, new NannyException(Err.UNSUPPORTED_OPCODE, request.opCode))));
    }

    @Test
    public void onReceiveShouldReturn400WhenRequestMissingRequestParams() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        intent.putExtra(Nanny.ENTITY_BODY, entity);
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender);

        target.onReceive(context, intent);

        verify(context).sendBroadcast(intentCaptor.capture());
        assertThat(intentCaptor.getValue(), equalToIntent(AppTestUtil.new400Response("123",
                Nanny.AUTHORIZATION_SERVICE, new NannyException(Err.NO_REQUEST_PARAMS))));
    }

    @Test
    public void onReceiveShouldReturn400WhenRequestMissingSenderIdentity() throws Exception {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        intent.putExtra(Nanny.ENTITY_BODY, entity);

        target.onReceive(context, intent);

        verify(context).sendBroadcast(intentCaptor.capture());
        assertThat(intentCaptor.getValue(), equalToIntent(AppTestUtil.new400Response("123",
                Nanny.AUTHORIZATION_SERVICE, new NannyException(Err.NO_SENDER_IDENTITY))));
    }
}
