package com.permissionnanny.content;

import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import com.permissionnanny.NannyAppTestRunner;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.lib.request.RequestParams;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(NannyAppTestRunner.class)
public class ProxyContentProviderTest extends NannyTestCase {

    ProxyContentProvider mProxyContentProvider;
    RequestParams mRequestParams;
    Uri mUri;
    @Mock ProviderInfo mProviderInfo;

    @Before
    public void setUp() throws Exception {
        mRequestParams = new RequestParams();
        mUri = Uri.parse("content://authority/123");
        mProxyContentProvider = new ProxyContentProvider();
        mProxyContentProvider.attachInfo(RuntimeEnvironment.application, mProviderInfo);
    }

    @Test
    public void query() throws Exception {
        ProxyContentProvider.approvedRequests.put(123L, mRequestParams);

        Cursor ans = mProxyContentProvider.query(mUri, null, null, null, null);

        assertThat(ans, notNullValue());
    }

    @Test
    public void query_shouldReturnNull_whenDuplicateRequests() throws Exception {
        ProxyContentProvider.approvedRequests.put(123L, mRequestParams);

        Cursor ans = mProxyContentProvider.query(mUri, null, null, null, null);
        Cursor ans1 = mProxyContentProvider.query(mUri, null, null, null, null);

        assertThat(ans, notNullValue());
        assertThat(ans1, nullValue());
    }

    @Test
    public void query_shouldReturnNull_whenUnapprovedRequest() throws Exception {
        Cursor ans = mProxyContentProvider.query(mUri, null, null, null, null);

        assertThat(ans, nullValue());
    }

    @Test
    public void query_shouldReturnNull_whenInvalidRequest() throws Exception {
        Cursor ans = mProxyContentProvider.query(Uri.parse("content://authority/abc"), null, null, null, null);

        assertThat(ans, nullValue());
    }

    @Test
    public void insert() throws Exception {
        Uri ans = mProxyContentProvider.insert(null, null);

        assertThat(ans, nullValue());
    }

    @Test
    public void delete() throws Exception {
        int ans = mProxyContentProvider.delete(null, null, null);

        assertThat(ans, is(0));
    }

    @Test
    public void update() throws Exception {
        int ans = mProxyContentProvider.update(null, null, null, null);

        assertThat(ans, is(0));
    }
}
