package com.permissionnanny;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.common.test.Mockingbird;
import com.permissionnanny.dagger.ContextComponent;
import com.permissionnanny.dagger.MockComponentFactory;
import com.permissionnanny.dagger.MockContextComponent;
import com.permissionnanny.data.AppPermission;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyException;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.TelephonyRequest;
import com.permissionnanny.lib.request.simple.WifiRequest;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static com.permissionnanny.common.test.AndroidMatchers.equalToBundle;
import static com.permissionnanny.common.test.AndroidMatchers.equalToIntent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.notNull;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientRequestReceiverTest extends NannyAppTestCase {

    ClientRequestReceiver mReceiver;
    Intent mIntent;
    Bundle mEntity;
    RequestParams mRequestParams;
    @Mock ContextComponent mContextComponent;
    @Mock Context mContext;
    @Mock PendingIntent mSender;
    @Inject AppPermissionManager mAppManager;
    @Inject ProxyExecutor mExecutor;
    @Captor ArgumentCaptor<Intent> mIntentCaptor;

    @Before
    public void setUp() throws Exception {
        MockContextComponent component = MockComponentFactory.getContextComponent();
        component.inject(this);
        mReceiver = new ClientRequestReceiver();
        mReceiver.setTestComponent(component);
        mIntent = new Intent();
        mEntity = new Bundle();
        mRequestParams = new RequestParams();
        Mockingbird.mockPendingIntent(mSender, "3rd.party.app");
    }

    @Test
    public void onReceiveShouldExecuteProtectionNormalOperation() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        mIntent.putExtra(Nanny.ENTITY_BODY, mEntity);
        mEntity.putParcelable(Nanny.SENDER_IDENTITY, mSender);
        mRequestParams.opCode = WifiRequest.GET_CONNECTION_INFO;
        mEntity.putParcelable(Nanny.REQUEST_PARAMS, mRequestParams);

        mReceiver.onReceive(mContext, mIntent);

        verify(mReceiver.mExecutor).executeAllow((Operation) notNull(), same(mRequestParams), eq("123"));
    }

    @Test
    public void onReceiveShouldStartDialogActivityWhenAlwaysAskProtectionDangerousOperation() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        mIntent.putExtra(Nanny.ENTITY_BODY, mEntity);
        mEntity.putParcelable(Nanny.SENDER_IDENTITY, mSender);
        mRequestParams.opCode = TelephonyRequest.GET_DEVICE_ID;
        mEntity.putParcelable(Nanny.REQUEST_PARAMS, mRequestParams);
        when(mAppManager.getPermissionPrivilege(eq("3rd.party.app"), (Operation) notNull(), same(mRequestParams)))
                .thenReturn(AppPermission.ALWAYS_ASK);

        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).startActivity(mIntentCaptor.capture());
        Intent dialogIntent = mIntentCaptor.getValue();
        assertThat(dialogIntent.getComponent().getClassName(), is(ConfirmRequestActivity.class.getName()));
        assertThat(dialogIntent.getExtras(), equalToBundle(dialogIntent.getExtras()));
    }

    @Test
    public void onReceiveShouldExecuteRequestWhenAlwaysAllowProtectionDangerousOperation() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        mIntent.putExtra(Nanny.ENTITY_BODY, mEntity);
        mEntity.putParcelable(Nanny.SENDER_IDENTITY, mSender);
        mRequestParams.opCode = TelephonyRequest.GET_DEVICE_ID;
        mEntity.putParcelable(Nanny.REQUEST_PARAMS, mRequestParams);
        when(mAppManager.getPermissionPrivilege(eq("3rd.party.app"), (Operation) notNull(), same(mRequestParams)))
                .thenReturn(AppPermission.ALWAYS_ALLOW);

        mReceiver.onReceive(mContext, mIntent);

        verify(mReceiver.mExecutor).executeAllow((Operation) notNull(), same(mRequestParams), eq("123"));
    }

    @Test
    public void onReceiveShouldDenyRequestWhenAlwaysDenyProtectionDangerousOperation() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        mIntent.putExtra(Nanny.ENTITY_BODY, mEntity);
        mEntity.putParcelable(Nanny.SENDER_IDENTITY, mSender);
        mRequestParams.opCode = TelephonyRequest.GET_DEVICE_ID;
        mEntity.putParcelable(Nanny.REQUEST_PARAMS, mRequestParams);
        when(mAppManager.getPermissionPrivilege(eq("3rd.party.app"), (Operation) notNull(), same(mRequestParams)))
                .thenReturn(AppPermission.ALWAYS_DENY);

        mReceiver.onReceive(mContext, mIntent);

        verify(mReceiver.mExecutor).executeDeny((Operation) notNull(), same(mRequestParams), eq("123"));
    }

    @Test
    public void onReceiveShouldReturn400WhenRequestedOperationIsUnsupported() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        mIntent.putExtra(Nanny.ENTITY_BODY, mEntity);
        mEntity.putParcelable(Nanny.SENDER_IDENTITY, mSender);
        mRequestParams.opCode = "rm -rf / --no-preserve-root";
        mEntity.putParcelable(Nanny.REQUEST_PARAMS, mRequestParams);

        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).sendBroadcast(mIntentCaptor.capture());
        assertThat(mIntentCaptor.getValue(), equalToIntent(AppTestUtil.new400Response("123",
                Nanny.AUTHORIZATION_SERVICE, new NannyException(Err.UNSUPPORTED_OPCODE, mRequestParams.opCode))));
    }

    @Test
    public void onReceiveShouldReturn400WhenRequestMissingRequestParams() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        mIntent.putExtra(Nanny.ENTITY_BODY, mEntity);
        mEntity.putParcelable(Nanny.SENDER_IDENTITY, mSender);

        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).sendBroadcast(mIntentCaptor.capture());
        assertThat(mIntentCaptor.getValue(), equalToIntent(AppTestUtil.new400Response("123",
                Nanny.AUTHORIZATION_SERVICE, new NannyException(Err.NO_REQUEST_PARAMS))));
    }

    @Test
    public void onReceiveShouldReturn400WhenRequestMissingSenderIdentity() throws Exception {
        mIntent.putExtra(Nanny.CLIENT_ADDRESS, "123");
        mIntent.putExtra(Nanny.ENTITY_BODY, mEntity);

        mReceiver.onReceive(mContext, mIntent);

        verify(mContext).sendBroadcast(mIntentCaptor.capture());
        assertThat(mIntentCaptor.getValue(), equalToIntent(AppTestUtil.new400Response("123",
                Nanny.AUTHORIZATION_SERVICE, new NannyException(Err.NO_SENDER_IDENTITY))));
    }
}
