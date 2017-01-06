package com.permissionnanny

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.permissionnanny.common.test.AndroidMatchers.equalToIntent
import com.permissionnanny.common.test.Mockingbird.mockPendingIntent
import com.permissionnanny.dagger.MockComponentFactory
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.NannyException
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import java.util.*
import javax.inject.Inject

class ClientPermissionManifestReceiverTest : NannyAppTestCase() {

    private lateinit var receiver: ClientPermissionManifestReceiver
    private lateinit var intent: Intent
    private lateinit var bundle: Bundle
    private lateinit var manifest: ArrayList<String>
    @Mock internal lateinit var context: Context
    @Mock internal lateinit var sender: PendingIntent
    @Inject internal lateinit var appPermissionManager: AppPermissionManager
    @Captor internal lateinit var responseCaptor: ArgumentCaptor<Intent>

    @Before
    fun setUp() {
        val component = MockComponentFactory.contextComponent
        component.inject(this)
        receiver = ClientPermissionManifestReceiver()
        receiver.setTestComponent(component)
        intent = Intent()
        bundle = Bundle()
        manifest = ArrayList<String>()
    }

    @Test
    fun onReceiveShouldSaveClientPermissionManifest() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")
        intent.putExtra(Nanny.ENTITY_BODY, bundle)
        bundle.putParcelable(Nanny.SENDER_IDENTITY, sender)
        mockPendingIntent(sender, "com.3rd.party.app")
        bundle.putStringArrayList(Nanny.PERMISSION_MANIFEST, manifest)

        receiver.onReceive(context, intent)

        verify(receiver.appManager).readPermissionManifest("com.3rd.party.app", manifest)
        verify(context).sendBroadcast(responseCaptor.capture())
        assertThat(responseCaptor.value, equalToIntent(AppTestUtil.new200Response("123")))
    }

    @Test
    fun onReceiveShouldReturn400WhenEntityBodyIsMissing() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")

        receiver.onReceive(context, intent)

        verify(context).sendBroadcast(responseCaptor.capture())
        assertThat(responseCaptor.value, equalToIntent(new400Response("123", Err.NO_ENTITY)))
    }

    @Test
    fun onReceiveShouldReturn400WhenSenderIdentityIsMissing() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")
        intent.putExtra(Nanny.ENTITY_BODY, bundle)

        receiver.onReceive(context, intent)

        verify(context).sendBroadcast(responseCaptor.capture())
        assertThat(responseCaptor.value, equalToIntent(new400Response("123", Err.NO_SENDER_IDENTITY)))
    }

    @Test
    fun onReceiveShouldReturn400WhenPermissionManifestIsMissing() {
        intent.putExtra(Nanny.CLIENT_ADDRESS, "123")
        intent.putExtra(Nanny.ENTITY_BODY, bundle)
        bundle.putParcelable(Nanny.SENDER_IDENTITY, sender)
        mockPendingIntent(sender, "com.3rd.party.app")

        receiver.onReceive(context, intent)

        verify(context).sendBroadcast(responseCaptor.capture())
        assertThat(responseCaptor.value, equalToIntent(new400Response("123", Err
                .NO_PERMISSION_MANIFEST)))
    }

    private fun new400Response(clientAddr: String, error: String): Intent {
        return AppTestUtil.new400Response(clientAddr, Nanny.PERMISSION_MANIFEST_SERVICE, NannyException(error))
    }
}
