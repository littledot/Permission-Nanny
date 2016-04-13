package com.permissionnanny.lib.request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.lib.NannyLibTestCase;
import com.permissionnanny.lib.request.simple.SimpleListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class PermissionEventTest extends NannyLibTestCase {

    PermissionEvent mEventFilter;
    Intent mIntent;
    @Mock SimpleListener mSimpleListener;
    @Mock Context mContext;
    @Captor ArgumentCaptor<Bundle> mBundleCaptor;

    @Before
    public void setUp() throws Exception {
        mEventFilter = new PermissionEvent(mSimpleListener);
        mIntent = new Intent();
    }

    @Test
    public void process() throws Exception {
        mIntent.putExtra("key", "val");

        mEventFilter.process(mContext, mIntent);

        verify(mSimpleListener).onResponse(mBundleCaptor.capture());
        assertThat(mBundleCaptor.getValue().getString("key"), is("val"));
    }
}
