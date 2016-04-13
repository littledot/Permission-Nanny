package com.permissionnanny;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.permissionnanny.dagger.MockComponentFactory;
import com.permissionnanny.dagger.MockContextComponent;
import com.permissionnanny.data.AppPermissionManager;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.WifiRequest;
import com.permissionnanny.simple.WifiOperation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class ConfirmRequestBinderTest extends NannyAppTestCase {

    ConfirmRequestBinder mBinder;
    MockContextComponent mComponent;
    RequestParams mRequestParams;
    @Mock Activity mActivity;
    @Mock NannyBundle mNannyBundle;
    @Mock PackageManager mPackageManager;
    @Mock ApplicationInfo mAppInfo;
    @Mock Drawable mIcon;
    @Mock ProxyExecutor mProxyExecutor;
    @Mock AppPermissionManager mAppPermissionManager;

    @Before
    public void setUp() throws Exception {
        mComponent = MockComponentFactory.getContextComponent();
        mComponent.inject(this);
        when(mActivity.getPackageManager()).thenReturn(mPackageManager);
        mRequestParams = new RequestParams();
    }

    @Test
    public void getDialogTitleShouldReturnAppLabel() throws Exception {
        when(mNannyBundle.getSenderIdentity()).thenReturn("3rd.party.app");
        when(mPackageManager.getApplicationInfo("3rd.party.app", 0)).thenReturn(mAppInfo);
        when(mPackageManager.getApplicationLabel(mAppInfo)).thenReturn("3rd Party App");
        mRequestParams.opCode = WifiRequest.GET_CONNECTION_INFO;
        when(mNannyBundle.getRequest()).thenReturn(mRequestParams);
        when(mActivity.getText(WifiOperation.getOperation(WifiRequest.GET_CONNECTION_INFO).mDialogTitle))
                .thenReturn("wants your connection info.");
        mBinder = new ConfirmRequestBinder(mActivity, mNannyBundle, mProxyExecutor, mAppPermissionManager);

        Spanned ans = mBinder.getDialogTitle();

        assertThat(ans.toString(), is("3rd Party App wants your connection info."));
        Object[] spans = ans.getSpans(0, ans.length(), Object.class);
        assertThat(spans.length, is(1));
        assertThat(((StyleSpan) spans[0]).getStyle(), is(Typeface.BOLD));
    }

    @Test
    public void getDialogTitleShouldReturnPackageNameWhenAppLabelCannotBeFound() throws Exception {
        when(mNannyBundle.getSenderIdentity()).thenReturn("3rd.party.app");
        when(mPackageManager.getApplicationInfo("3rd.party.app", 0)).thenReturn(mAppInfo);
        when(mPackageManager.getApplicationLabel(mAppInfo)).thenReturn(null);
        mRequestParams.opCode = WifiRequest.GET_CONNECTION_INFO;
        when(mNannyBundle.getRequest()).thenReturn(mRequestParams);
        when(mActivity.getText(WifiOperation.getOperation(WifiRequest.GET_CONNECTION_INFO).mDialogTitle))
                .thenReturn("wants your connection info.");
        mBinder = new ConfirmRequestBinder(mActivity, mNannyBundle, mProxyExecutor, mAppPermissionManager);

        Spanned ans = mBinder.getDialogTitle();

        assertThat(ans.toString(), is("3rd.party.app wants your connection info."));
        Object[] spans = ans.getSpans(0, ans.length(), Object.class);
        assertThat(spans.length, is(1));
        assertThat(((StyleSpan) spans[0]).getStyle(), is(Typeface.BOLD));
    }

    @Test
    public void getDialogTitleShouldReturnPackageNameWhenAppInfoCannotBeFound() throws Exception {
        when(mNannyBundle.getSenderIdentity()).thenReturn("3rd.party.app");
        when(mPackageManager.getApplicationInfo("3rd.party.app", 0)).thenReturn(null);
        when(mPackageManager.getApplicationLabel(mAppInfo)).thenReturn(null);
        mRequestParams.opCode = WifiRequest.GET_CONNECTION_INFO;
        when(mNannyBundle.getRequest()).thenReturn(mRequestParams);
        when(mActivity.getText(WifiOperation.getOperation(WifiRequest.GET_CONNECTION_INFO).mDialogTitle))
                .thenReturn("wants your connection info.");
        mBinder = new ConfirmRequestBinder(mActivity, mNannyBundle, mProxyExecutor, mAppPermissionManager);

        Spanned ans = mBinder.getDialogTitle();

        assertThat(ans.toString(), is("3rd.party.app wants your connection info."));
        Object[] spans = ans.getSpans(0, ans.length(), Object.class);
        assertThat(spans.length, is(1));
        assertThat(((StyleSpan) spans[0]).getStyle(), is(Typeface.BOLD));
    }

    @Test
    public void getDialogIconShouldReturnAppIcon() throws Exception {
        when(mNannyBundle.getSenderIdentity()).thenReturn("3rd.party.app");
        when(mPackageManager.getApplicationInfo("3rd.party.app", 0)).thenReturn(mAppInfo);
        when(mPackageManager.getApplicationIcon(mAppInfo)).thenReturn(mIcon);
        mRequestParams.opCode = WifiRequest.GET_CONNECTION_INFO;
        when(mNannyBundle.getRequest()).thenReturn(mRequestParams);
        mBinder = new ConfirmRequestBinder(mActivity, mNannyBundle, mProxyExecutor, mAppPermissionManager);

        Drawable ans = mBinder.getDialogIcon();

        assertThat(ans, sameInstance(mIcon));
    }

    @Test
    public void getDialogIconShouldReturnNullWhenAppIconCannotBeFound() throws Exception {
        when(mNannyBundle.getSenderIdentity()).thenReturn("3rd.party.app");
        when(mPackageManager.getApplicationInfo("3rd.party.app", 0)).thenReturn(mAppInfo);
        when(mPackageManager.getApplicationIcon(mAppInfo)).thenReturn(null);
        mRequestParams.opCode = WifiRequest.GET_CONNECTION_INFO;
        when(mNannyBundle.getRequest()).thenReturn(mRequestParams);
        mBinder = new ConfirmRequestBinder(mActivity, mNannyBundle, mProxyExecutor, mAppPermissionManager);

        Drawable ans = mBinder.getDialogIcon();

        assertThat(ans, nullValue());
    }
}
