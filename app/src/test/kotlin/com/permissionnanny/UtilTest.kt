package com.permissionnanny

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import org.hamcrest.Matchers.nullValue
import org.hamcrest.Matchers.sameInstance
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Matchers.anyInt
import org.mockito.Matchers.anyString
import org.mockito.Mock

class UtilTest : NannyAppTestCase() {

    private var applicationInfo = ApplicationInfo()
    @Mock private lateinit var context: Context
    @Mock private lateinit var packageManager: PackageManager

    @Before
    fun setUp() {
        given(context.packageManager).willReturn(packageManager)
    }

    @Test
    fun getApplicationInfo() {
        given(packageManager.getApplicationInfo("a", 0)).willReturn(applicationInfo)

        val actual = Util.getApplicationInfo(context, "a")

        assertThat(actual, sameInstance(applicationInfo))
    }

    @Test
    fun getApplicationInfo_shouldReturnNull_whenQueryThrows() {
        given(packageManager.getApplicationInfo(anyString(), anyInt()))
                .willThrow(PackageManager.NameNotFoundException())

        val actual = Util.getApplicationInfo(context, "a")

        assertThat(actual, nullValue())
    }
}
