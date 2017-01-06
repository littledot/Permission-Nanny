package com.permissionnanny

import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import com.permissionnanny.data.AppPermission
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.lib.NannyBundle
import com.permissionnanny.lib.request.RequestParams

/**

 */
open class ConfirmRequestBinder(
        activity: Activity,
        private val bundle: NannyBundle,
        private val executor: ProxyExecutor,
        private val appManager: AppPermissionManager)
    : BaseBinder() {

    @VisibleForTesting internal var view: ConfirmRequestView
    private val context: Context
    private val packageManager: PackageManager
    private val clientAddr: String?
    private val appPackage: String
    private val appInfo: ApplicationInfo?
    private val request: RequestParams
    private val operation: Operation

    var rememberPreference: Boolean = false
        @VisibleForTesting set // FIXME: public -> internal

    init {
        view = ConfirmRequestView(activity, this, TextDialogStubView(this))
        context = activity
        packageManager = context.packageManager
        clientAddr = bundle.clientAddress
        appPackage = bundle.senderIdentity!!
        appInfo = Util.getApplicationInfo(context, appPackage)
        request = bundle.request!!
        operation = Operation.getOperation(request)!!
    }

    open val dialogTitle: Spanned
        get() {
            var label: CharSequence = appPackage
            if (appInfo != null) {
                label = packageManager.getApplicationLabel(appInfo) ?: label
            }
            val boldAppLabel = SpannableStringBuilder(label)
            boldAppLabel.setSpan(StyleSpan(Typeface.BOLD), 0, label.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return boldAppLabel.append(' ').append(context.getText(operation.dialogTitle))
        }

    open val dialogIcon: Drawable?
        get() = if (appInfo != null) packageManager.getApplicationIcon(appInfo) else null

    open val dialogBody: CharSequence
        get() = bundle.requestRationale!!

    open fun preOnCreate(state: Bundle?) {
        view.preOnCreate(state)
    }

    open fun onCreate(state: Bundle?) {
        view.onCreate(state)
        view.bindViews()
    }

    open fun onBackPressed() {
        executeDeny()
    }

    open fun changeRememberPreference(remember: Boolean) {
        rememberPreference = remember
        view.bindViews()
    }

    open fun executeAllow() {
        if (rememberPreference) {
            appManager.changePrivilege(appPackage, operation, request, AppPermission.ALWAYS_ALLOW);
        }
        executor.executeAllow(operation, request, clientAddr)
    }

    open fun executeDeny() {
        if (rememberPreference) {
            appManager.changePrivilege(appPackage, operation, request, AppPermission.ALWAYS_DENY);
        }
        executor.executeDeny(operation, request, clientAddr)
    }
}
