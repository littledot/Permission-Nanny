package com.permissionnanny

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.permissionnanny.common.test.AndroidMatchers.equalToIntent
import com.permissionnanny.common.test.Mockingbird.mockPendingIntent
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.NannyException
import com.permissionnanny.missioncontrol.AppControlActivity
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify

class ClientDeepLinkReceiverTest : NannyAppTestCase() {

    private lateinit var receiver: ClientDeepLinkReceiver
    private lateinit var intent: Intent
    private lateinit var bundle: Bundle
    @Mock private lateinit var context: Context
    @Mock private lateinit var pendingIntent: PendingIntent
    @Captor private lateinit var intentCaptor: ArgumentCaptor<Intent>

    @Before
    fun setUp() {
        mockPendingIntent(pendingIntent, "senderPkg")
        receiver = ClientDeepLinkReceiver()
        intent = Intent()
        bundle = Bundle()
    }

    @Test
    fun onReceive_shouldStartActivityAndReturn200() {
        bundle.putParcelable(Nanny.SENDER_IDENTITY, pendingIntent)
        bundle.putString(Nanny.DEEP_LINK_TARGET, Nanny.MANAGE_APPLICATIONS_SETTINGS)
        intent.putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.CLIENT_ADDRESS, "clientAddr")
                .putExtra(Nanny.ENTITY_BODY, bundle)

        receiver.onReceive(context, intent)

        verify(context).startActivity(intentCaptor.capture())
        var capturedIntent = intentCaptor.allValues[0]
        assertThat(capturedIntent.component.className, equalTo(AppControlActivity::class.java.name))
        assertThat(capturedIntent.flags, equalTo(Intent.FLAG_ACTIVITY_NEW_TASK))

        verify(context).sendBroadcast(intentCaptor.capture())
        capturedIntent = intentCaptor.allValues[1]
        assertThat(capturedIntent, equalToIntent(AppTestUtil.new200AuthServiceResponse("clientAddr")))
    }

    @Test
    fun onReceive_shouldReturn400_whenNoSenderIdentity() {
        bundle.putString(Nanny.DEEP_LINK_TARGET, Nanny.MANAGE_APPLICATIONS_SETTINGS)
        intent.putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.CLIENT_ADDRESS, "clientAddr")
                .putExtra(Nanny.ENTITY_BODY, bundle)

        receiver.onReceive(context, intent)

        verify(context).sendBroadcast(intentCaptor.capture())
        val capturedIntent = intentCaptor.value
        assertThat(capturedIntent, equalToIntent(AppTestUtil.new400AuthServiceResponse(
                "clientAddr", Err.NO_SENDER_IDENTITY)))
    }

    @Test
    fun onReceive_shouldReturn400_whenNoDeepLinkTarget() {
        bundle.putParcelable(Nanny.SENDER_IDENTITY, pendingIntent)
        intent.putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.CLIENT_ADDRESS, "clientAddr")
                .putExtra(Nanny.ENTITY_BODY, bundle)

        receiver.onReceive(context, intent)

        verify(context).sendBroadcast(intentCaptor.capture())
        val capturedIntent = intentCaptor.value
        assertThat(capturedIntent, equalToIntent(AppTestUtil.new400AuthServiceResponse(
                "clientAddr", NannyException(Err.UNSUPPORTED_DEEP_LINK_TARGET, ""))))
    }
}
