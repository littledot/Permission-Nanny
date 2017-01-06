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

    private lateinit var mReceiver: UninstallReceiver
    private lateinit var mIntent: Intent
    @Mock private lateinit var mContext: Context
    @Inject lateinit var mAppPermissionManager: AppPermissionManager

    @Before
    fun setUp() {
        val component = DaggerMockContextComponent.builder()
                .appComponent(MockComponentFactory.appComponent)
                .contextModule(MockContextModule()).build()
        component.inject(this)
        mReceiver = UninstallReceiver()
        mReceiver.setTestComponent(component)
        mIntent = Intent()
    }

    @Test
    fun onReceive() {
        mIntent.setAction(Intent.ACTION_PACKAGE_REMOVED)
                .putExtra(Intent.EXTRA_REPLACING, false).data = Uri.parse("package:3rd.party.app")

        mReceiver.onReceive(mContext, mIntent)

        verify<AppPermissionManager>(mAppPermissionManager).removeApp("3rd.party.app")
    }

    @Test
    fun onReceive_shouldIgnoreIntent_whenUpgradeFlow() {
        mIntent.setAction(Intent.ACTION_PACKAGE_REMOVED)
                .putExtra(Intent.EXTRA_REPLACING, true).data = Uri.parse("package:3rd.party.app")

        mReceiver.onReceive(mContext, mIntent)

        verify<AppPermissionManager>(mAppPermissionManager, never()).removeApp("3rd.party.app")
    }
}
