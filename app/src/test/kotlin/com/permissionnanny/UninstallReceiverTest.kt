package com.permissionnanny

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.permissionnanny.dagger.DaggerMockContextComponent
import com.permissionnanny.dagger.MockComponentFactory
import com.permissionnanny.dagger.MockContextModule
import com.permissionnanny.data.AppPermissionManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import javax.inject.Inject

class UninstallReceiverTest : NannyAppTestCase() {

    internal var receiver = UninstallReceiver()
    internal var intent = Intent()
    @Mock internal lateinit var context: Context
    @Inject internal lateinit var appPermissionManager: AppPermissionManager

    @Before
    fun setUp() {
        val component = DaggerMockContextComponent.builder()
                .appComponent(MockComponentFactory.getAppComponent())
                .contextModule(MockContextModule()).build()
        component.inject(this)
        receiver.setTestComponent(component)
    }

    @Test
    fun onReceive() {
        intent.setAction(Intent.ACTION_PACKAGE_REMOVED)
                .putExtra(Intent.EXTRA_REPLACING, false).data = Uri.parse("package:3rd.party.app")

        receiver.onReceive(context, intent)

        verify(appPermissionManager).removeApp("3rd.party.app")
    }

    @Test
    fun onReceive_shouldIgnoreIntent_whenUpgradeFlow() {
        intent.setAction(Intent.ACTION_PACKAGE_REMOVED)
                .putExtra(Intent.EXTRA_REPLACING, true).data = Uri.parse("package:3rd.party.app")

        receiver.onReceive(context, intent)

        verify(appPermissionManager, never()).removeApp("3rd.party.app")
    }
}
