package com.permissionnanny

import android.content.Context
import android.content.Intent
import com.permissionnanny.data.AppPermissionManager
import timber.log.Timber
import javax.inject.Inject

/**

 */
class UninstallReceiver : BaseReceiver() {

    @Inject internal lateinit var appManager: AppPermissionManager

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        getComponent(context).inject(this)

        if (intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) { // Upgrade flow
            return
        }

        Timber.wtf("removed package=" + intent.data)
        val appPackage = intent.data.schemeSpecificPart
        appManager.removeApp(appPackage)
    }
}
