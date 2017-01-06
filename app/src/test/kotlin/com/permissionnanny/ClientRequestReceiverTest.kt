package com.permissionnanny

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.permissionnanny.common.test.AndroidMatchers.equalToBundle
import com.permissionnanny.common.test.AndroidMatchers.equalToIntent
import com.permissionnanny.common.test.Mockingbird
import com.permissionnanny.dagger.ContextComponent
import com.permissionnanny.dagger.MockComponentFactory
import com.permissionnanny.data.AppPermission
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.NannyException
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.simple.TelephonyRequest
import com.permissionnanny.lib.request.simple.WifiRequest
import mocklin.Mocklin.eq
import mocklin.Mocklin.same
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import javax.inject.Inject

class ClientRequestReceiverTest : NannyAppTestCase() {

    private lateinit var receiver: ClientRequestReceiver
    private lateinit var intent: Intent
    private lateinit var entity: Bundle
    private lateinit var requestParams: RequestParams
    @Mock internal lateinit var contextComponent: ContextComponent
    @Mock internal lateinit var context: Context
    @Mock internal lateinit var sender: PendingIntent
    @Inject lateinit var appManager: AppPermissionManager
    @Inject lateinit var executor: ProxyExecutor
    @Captor internal lateinit var intentCaptor: ArgumentCaptor<Intent>

    @Before
    fun setUp() {
        val component = MockComponentFactory.contextComponent
        component.inject(this)
        receiver = ClientRequestReceiver()
        receiver.setTestComponent(component)
        intent = Intent()
        entity = Bundle()
        requestParams = RequestParams()
        Mockingbird.mockPendingIntent(sender, "3rd.party.app")
    }

    @Test
    fun onReceiveShouldExecuteProtectionNormalOperation() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")
        intent.putExtra(Nanny.ENTITY_BODY, entity)
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender)
        requestParams.opCode = WifiRequest.GET_CONNECTION_INFO
        entity.putParcelable(Nanny.REQUEST_PARAMS, requestParams)

        receiver.onReceive(context, intent)

        verify(receiver.executor).executeAllow(same(Operation.getOperation(requestParams)!!), same(requestParams), eq("123"))
    }

    @Test
    fun onReceiveShouldStartDialogActivityWhenAlwaysAskProtectionDangerousOperation() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")
        intent.putExtra(Nanny.ENTITY_BODY, entity)
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender)
        requestParams.opCode = TelephonyRequest.GET_DEVICE_ID
        entity.putParcelable(Nanny.REQUEST_PARAMS, requestParams)
        val op = Operation.getOperation(requestParams)!!
        `when`(appManager.getPermissionPrivilege(eq("3rd.party.app"),
                same(op),
                same(requestParams)))
                .thenReturn(AppPermission.ALWAYS_ASK)

        receiver.onReceive(context, intent)

        verify<Context>(context).startActivity(intentCaptor.capture())
        val dialogIntent = intentCaptor.value
        assertThat(dialogIntent.component.className, `is`(ConfirmRequestActivity::class.java.name))
        assertThat(dialogIntent.extras, equalToBundle(dialogIntent.extras))
    }

    @Test
    fun onReceiveShouldExecuteRequestWhenAlwaysAllowProtectionDangerousOperation() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")
        intent.putExtra(Nanny.ENTITY_BODY, entity)
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender)
        requestParams.opCode = TelephonyRequest.GET_DEVICE_ID
        entity.putParcelable(Nanny.REQUEST_PARAMS, requestParams)
        val op = Operation.getOperation(requestParams)!!
        `when`(appManager.getPermissionPrivilege(eq("3rd.party.app"), same(op), same(requestParams)))
                .thenReturn(AppPermission.ALWAYS_ALLOW)

        receiver.onReceive(context, intent)

        verify(receiver.executor).executeAllow(same(op), same(requestParams), eq("123"))
    }

    @Test
    fun onReceiveShouldDenyRequestWhenAlwaysDenyProtectionDangerousOperation() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")
        intent.putExtra(Nanny.ENTITY_BODY, entity)
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender)
        requestParams.opCode = TelephonyRequest.GET_DEVICE_ID
        entity.putParcelable(Nanny.REQUEST_PARAMS, requestParams)
        val op = Operation.getOperation(requestParams)!!
        `when`(appManager.getPermissionPrivilege(eq("3rd.party.app"), same(op), same(requestParams)))
                .thenReturn(AppPermission.ALWAYS_DENY)

        receiver.onReceive(context, intent)

        verify(receiver.executor).executeDeny(same(op), same(requestParams), eq("123"))
    }

    @Test
    fun onReceiveShouldReturn400WhenRequestedOperationIsUnsupported() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")
        intent.putExtra(Nanny.ENTITY_BODY, entity)
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender)
        requestParams.opCode = "rm -rf / --no-preserve-root"
        entity.putParcelable(Nanny.REQUEST_PARAMS, requestParams)

        receiver.onReceive(context, intent)

        verify<Context>(context).sendBroadcast(intentCaptor.capture())
        assertThat(intentCaptor.value, equalToIntent(AppTestUtil.new400Response("123",
                Nanny.AUTHORIZATION_SERVICE, NannyException(Err.UNSUPPORTED_OPCODE, requestParams.opCode))))
    }

    @Test
    fun onReceiveShouldReturn400WhenRequestMissingRequestParams() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")
        intent.putExtra(Nanny.ENTITY_BODY, entity)
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender)

        receiver.onReceive(context, intent)

        verify<Context>(context).sendBroadcast(intentCaptor.capture())
        assertThat(intentCaptor.value, equalToIntent(AppTestUtil.new400Response("123",
                Nanny.AUTHORIZATION_SERVICE, NannyException(Err.NO_REQUEST_PARAMS))))
    }

    @Test
    fun onReceiveShouldReturn400WhenRequestMissingSenderIdentity() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")
        intent.putExtra(Nanny.ENTITY_BODY, entity)

        receiver.onReceive(context, intent)

        verify<Context>(context).sendBroadcast(intentCaptor.capture())
        assertThat(intentCaptor.value, equalToIntent(AppTestUtil.new400Response("123",
                Nanny.AUTHORIZATION_SERVICE, NannyException(Err.NO_SENDER_IDENTITY))))
    }
}
