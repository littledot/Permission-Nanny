package com.permissionnanny.content

import android.content.pm.ProviderInfo
import android.net.Uri
import com.permissionnanny.NannyAppTestCase
import com.permissionnanny.lib.request.RequestParams
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.robolectric.RuntimeEnvironment

class ProxyContentProviderTest : NannyAppTestCase() {

    private lateinit var proxyContentProvider: ProxyContentProvider
    private lateinit var requestParams: RequestParams
    private lateinit var uri: Uri
    @Mock private lateinit var providerInfo: ProviderInfo

    @Before
    fun setUp() {
        requestParams = RequestParams()
        uri = Uri.parse("content://authority/123")
        proxyContentProvider = ProxyContentProvider()
        proxyContentProvider.attachInfo(RuntimeEnvironment.application, providerInfo)
    }

    @Test
    fun query() {
        ProxyContentProvider.approvedRequests.put(123L, requestParams)

        val ans = proxyContentProvider.query(uri, null, null, null, null)

        assertThat(ans, notNullValue())
    }

    @Test
    fun query_shouldReturnNull_whenDuplicateRequests() {
        ProxyContentProvider.approvedRequests.put(123L, requestParams)

        val ans = proxyContentProvider.query(uri, null, null, null, null)
        val ans1 = proxyContentProvider.query(uri, null, null, null, null)

        assertThat(ans, notNullValue())
        assertThat(ans1, nullValue())
    }

    @Test
    fun query_shouldReturnNull_whenUnapprovedRequest() {
        val ans = proxyContentProvider.query(uri, null, null, null, null)

        assertThat(ans, nullValue())
    }

    @Test
    fun query_shouldReturnNull_whenInvalidRequest() {
        val ans = proxyContentProvider.query(Uri.parse("content://authority/abc"), null, null, null, null)

        assertThat(ans, nullValue())
    }

    @Test
    fun insert() {
        val ans = proxyContentProvider.insert(null, null)

        assertThat(ans, nullValue())
    }

    @Test
    fun delete() {
        val ans = proxyContentProvider.delete(null, null, null)

        assertThat(ans, equalTo(0))
    }

    @Test
    fun update() {
        val ans = proxyContentProvider.update(null, null, null, null)

        assertThat(ans, equalTo(0))
    }
}
