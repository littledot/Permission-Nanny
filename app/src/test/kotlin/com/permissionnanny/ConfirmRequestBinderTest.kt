package com.permissionnanny

import android.app.Activity
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.StyleSpan
import com.permissionnanny.dagger.MockComponentFactory
import com.permissionnanny.dagger.MockContextComponent
import com.permissionnanny.data.AppPermissionManager
import com.permissionnanny.lib.NannyBundle
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.simple.WifiRequest
import com.permissionnanny.simple.WifiOperation
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock

class ConfirmRequestBinderTest : NannyAppTestCase() {

    private lateinit var binder: ConfirmRequestBinder
    private lateinit var component: MockContextComponent
    private lateinit var requestParams: RequestParams
    @Mock private lateinit var activity: Activity
    @Mock private lateinit var nannyBundle: NannyBundle
    @Mock private lateinit var packageManager: PackageManager
    @Mock private lateinit var appInfo: ApplicationInfo
    @Mock private lateinit var icon: Drawable
    @Mock private lateinit var proxyExecutor: ProxyExecutor
    @Mock private lateinit var appPermissionManager: AppPermissionManager

    @Before
    fun setUp() {
        component = MockComponentFactory.contextComponent
        component.inject(this)
        given(activity.packageManager).willReturn(packageManager)
        requestParams = RequestParams()
    }

    @Test
    fun getDialogTitleShouldReturnAppLabel() {
        given<String>(nannyBundle.senderIdentity).willReturn("3rd.party.app")
        given(packageManager.getApplicationInfo("3rd.party.app", 0)).willReturn(appInfo)
        given(packageManager.getApplicationLabel(appInfo)).willReturn("3rd Party App")
        requestParams.opCode = WifiRequest.GET_CONNECTION_INFO
        given<RequestParams>(nannyBundle.request).willReturn(requestParams)
        given(activity.getText(WifiOperation.getOperation(WifiRequest.GET_CONNECTION_INFO)!!.dialogTitle))
                .willReturn("wants your connection info.")
        binder = ConfirmRequestBinder(activity, nannyBundle, proxyExecutor, appPermissionManager)

        val ans = binder.dialogTitle

        assertThat(ans.toString(), equalTo("3rd Party App wants your connection info."))
        val spans = ans.getSpans(0, ans.length, Any::class.java)
        assertThat(spans.size, equalTo(1))
        assertThat((spans[0] as StyleSpan).style, equalTo(Typeface.BOLD))
    }

    @Test
    fun getDialogTitleShouldReturnPackageNameWhenAppLabelCannotBeFound() {
        given<String>(nannyBundle.senderIdentity).willReturn("3rd.party.app")
        given(packageManager.getApplicationInfo("3rd.party.app", 0)).willReturn(appInfo)
        given(packageManager.getApplicationLabel(appInfo)).willReturn(null)
        requestParams.opCode = WifiRequest.GET_CONNECTION_INFO
        given<RequestParams>(nannyBundle.request).willReturn(requestParams)
        given(activity.getText(WifiOperation.getOperation(WifiRequest.GET_CONNECTION_INFO)!!.dialogTitle))
                .willReturn("wants your connection info.")
        binder = ConfirmRequestBinder(activity, nannyBundle, proxyExecutor, appPermissionManager)

        val ans = binder.dialogTitle

        assertThat(ans.toString(), equalTo("3rd.party.app wants your connection info."))
        val spans = ans.getSpans(0, ans.length, Any::class.java)
        assertThat(spans.size, equalTo(1))
        assertThat((spans[0] as StyleSpan).style, equalTo(Typeface.BOLD))
    }

    @Test
    fun getDialogTitleShouldReturnPackageNameWhenAppInfoCannotBeFound() {
        given<String>(nannyBundle.senderIdentity).willReturn("3rd.party.app")
        given(packageManager.getApplicationInfo("3rd.party.app", 0)).willReturn(null)
        given(packageManager.getApplicationLabel(appInfo)).willReturn(null)
        requestParams.opCode = WifiRequest.GET_CONNECTION_INFO
        given<RequestParams>(nannyBundle.request).willReturn(requestParams)
        given(activity.getText(WifiOperation.getOperation(WifiRequest.GET_CONNECTION_INFO)!!.dialogTitle))
                .willReturn("wants your connection info.")
        binder = ConfirmRequestBinder(activity, nannyBundle, proxyExecutor, appPermissionManager)

        val ans = binder.dialogTitle

        assertThat(ans.toString(), equalTo("3rd.party.app wants your connection info."))
        val spans = ans.getSpans(0, ans.length, Any::class.java)
        assertThat(spans.size, equalTo(1))
        assertThat((spans[0] as StyleSpan).style, equalTo(Typeface.BOLD))
    }

    @Test
    fun getDialogIconShouldReturnAppIcon() {
        given<String>(nannyBundle.senderIdentity).willReturn("3rd.party.app")
        given(packageManager.getApplicationInfo("3rd.party.app", 0)).willReturn(appInfo)
        given(packageManager.getApplicationIcon(appInfo)).willReturn(icon)
        requestParams.opCode = WifiRequest.GET_CONNECTION_INFO
        given<RequestParams>(nannyBundle.request).willReturn(requestParams)
        binder = ConfirmRequestBinder(activity, nannyBundle, proxyExecutor, appPermissionManager)

        val ans = binder.dialogIcon

        assertThat(ans, sameInstance<Drawable>(icon))
    }

    @Test
    fun getDialogIconShouldReturnNullWhenAppIconCannotBeFound() {
        given<String>(nannyBundle.senderIdentity).willReturn("3rd.party.app")
        given(packageManager.getApplicationInfo("3rd.party.app", 0)).willReturn(appInfo)
        given(packageManager.getApplicationIcon(appInfo)).willReturn(null)
        requestParams.opCode = WifiRequest.GET_CONNECTION_INFO
        given<RequestParams>(nannyBundle.request).willReturn(requestParams)
        binder = ConfirmRequestBinder(activity, nannyBundle, proxyExecutor, appPermissionManager)

        val ans = binder.dialogIcon

        assertThat(ans, nullValue())
    }
}
