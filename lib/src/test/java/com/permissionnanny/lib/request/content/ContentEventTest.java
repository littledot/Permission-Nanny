package com.permissionnanny.lib.request.content;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.test.mock.MockContentProvider;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.common.test.NannyTestRunner;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyLibTestRunner;
import com.permissionnanny.lib.request.RequestParams;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowContentResolver;

import static com.permissionnanny.common.test.AndroidMatchers.equalToBundle;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(NannyLibTestRunner.class)
public class ContentEventTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyTestRunner.newTestRules(this);

    ContentEvent mEventFilter;
    RequestParams mRequestParams;
    Intent mIntent;
    Bundle mBundle;
    @Mock ContentListener mContentListener;
    @Captor ArgumentCaptor<Bundle> mBundleArgumentCaptor;
    @Captor ArgumentCaptor<Cursor> mCursorArgumentCaptor;
    @Mock MockContentProvider mContentProvider;
    @Mock Cursor mCursor;

    @Before
    public void setUp() throws Exception {
        mRequestParams = new RequestParams();
        mIntent = new Intent();
        mBundle = new Bundle();
        when(mContentProvider.query(Nanny.getProxyContentProvider().buildUpon().appendPath("123").build(),
                null, null, null, null)).thenReturn(mCursor);
        ShadowContentResolver.registerProvider(Nanny.getProxyContentProvider().getAuthority(), mContentProvider);

        mEventFilter = new ContentEvent(mRequestParams, mContentListener);
    }

    @Test
    public void process_shouldReturnCursor_whenSelect() throws Exception {
        setupProcess(ContentRequest.SELECT);

        mEventFilter.process(RuntimeEnvironment.application, mIntent);

        verify(mContentListener).onResponse(mBundleArgumentCaptor.capture(), mCursorArgumentCaptor.capture());
        assertThat(mCursorArgumentCaptor.getValue(), sameInstance(mCursor));
        assertThat(mBundleArgumentCaptor.getValue(), equalToBundle(mIntent.getExtras()));
    }

    @Test
    public void process_shouldReturnNull_whenSelectStatusCodeIsNotOk() throws Exception {
        setupProcess(ContentRequest.SELECT);
        mIntent.putExtra(Nanny.STATUS_CODE, Nanny.SC_FORBIDDEN);

        mEventFilter.process(RuntimeEnvironment.application, mIntent);

        assertProcess_nullCursor();
    }

    @Test
    public void process_shouldReturnNull_whenInsert() throws Exception {
        setupProcess(ContentRequest.INSERT);

        mEventFilter.process(RuntimeEnvironment.application, mIntent);

        assertProcess_nullCursor();
    }

    @Test
    public void process_shouldReturnNull_whenUpdate() throws Exception {
        setupProcess(ContentRequest.UPDATE);

        mEventFilter.process(RuntimeEnvironment.application, mIntent);

        assertProcess_nullCursor();
    }

    @Test
    public void process_shouldReturnNull_whenDelete() throws Exception {
        setupProcess(ContentRequest.DELETE);

        mEventFilter.process(RuntimeEnvironment.application, mIntent);

        assertProcess_nullCursor();
    }

    private void setupProcess(String opCode) {
        mRequestParams.opCode = opCode;
        mBundle.putLong(ContentRequest.SELECT, 123);
        mIntent.putExtra(Nanny.STATUS_CODE, Nanny.SC_OK);
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);
    }

    private void assertProcess_nullCursor() {
        verify(mContentListener).onResponse(mBundleArgumentCaptor.capture(), mCursorArgumentCaptor.capture());
        assertThat(mCursorArgumentCaptor.getValue(), nullValue());
        assertThat(mBundleArgumentCaptor.getValue(), equalToBundle(mIntent.getExtras()));
    }
}
