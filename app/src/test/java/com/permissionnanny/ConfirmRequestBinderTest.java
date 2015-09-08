package com.permissionnanny;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.WifiRequest;
import com.permissionnanny.simple.WifiOperation;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(NannyAppTestRunner.class)
public class ConfirmRequestBinderTest {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    ConfirmRequestBinder target;
    @Mock Activity activity;
    @Mock NannyBundle bundle;
    @Mock PackageManager pm;
    @Mock ApplicationInfo appInfo;
    @Mock Drawable icon;
    RequestParams params;

    @Before
    public void setUp() throws Exception {
        activity = RoboApp.newMockActivity();
        when(activity.getPackageManager()).thenReturn(pm);
        params = new RequestParams();
    }

    @Test
    public void getDialogTitleShouldReturnAppLabel() throws Exception {
        when(bundle.getSenderIdentity()).thenReturn("3rd.party.app");
        when(pm.getApplicationInfo("3rd.party.app", 0)).thenReturn(appInfo);
        when(pm.getApplicationLabel(appInfo)).thenReturn("3rd Party App");
        params.opCode = WifiRequest.GET_CONNECTION_INFO;
        when(bundle.getRequest()).thenReturn(params);
        when(activity.getText(WifiOperation.getOperation(WifiRequest.GET_CONNECTION_INFO).mDialogTitle))
                .thenReturn("wants your connection info.");
        target = new ConfirmRequestBinder(activity, bundle);

        Spanned ans = target.getDialogTitle();

        assertThat(ans.toString(), is("3rd Party App wants your connection info."));
        Object[] spans = ans.getSpans(0, ans.length(), Object.class);
        assertThat(spans.length, is(1));
        assertThat(((StyleSpan) spans[0]).getStyle(), is(Typeface.BOLD));
    }

    @Test
    public void getDialogTitleShouldReturnPackageNameWhenAppLabelCannotBeFound() throws Exception {
        when(bundle.getSenderIdentity()).thenReturn("3rd.party.app");
        when(pm.getApplicationInfo("3rd.party.app", 0)).thenReturn(appInfo);
        when(pm.getApplicationLabel(appInfo)).thenReturn(null);
        params.opCode = WifiRequest.GET_CONNECTION_INFO;
        when(bundle.getRequest()).thenReturn(params);
        when(activity.getText(WifiOperation.getOperation(WifiRequest.GET_CONNECTION_INFO).mDialogTitle))
                .thenReturn("wants your connection info.");
        target = new ConfirmRequestBinder(activity, bundle);

        Spanned ans = target.getDialogTitle();

        assertThat(ans.toString(), is("3rd.party.app wants your connection info."));
        Object[] spans = ans.getSpans(0, ans.length(), Object.class);
        assertThat(spans.length, is(1));
        assertThat(((StyleSpan) spans[0]).getStyle(), is(Typeface.BOLD));
    }

    @Test
    public void getDialogTitleShouldReturnPackageNameWhenAppInfoCannotBeFound() throws Exception {
        when(bundle.getSenderIdentity()).thenReturn("3rd.party.app");
        when(pm.getApplicationInfo("3rd.party.app", 0)).thenReturn(null);
        when(pm.getApplicationLabel(appInfo)).thenReturn(null);
        params.opCode = WifiRequest.GET_CONNECTION_INFO;
        when(bundle.getRequest()).thenReturn(params);
        when(activity.getText(WifiOperation.getOperation(WifiRequest.GET_CONNECTION_INFO).mDialogTitle))
                .thenReturn("wants your connection info.");
        target = new ConfirmRequestBinder(activity, bundle);

        Spanned ans = target.getDialogTitle();

        assertThat(ans.toString(), is("3rd.party.app wants your connection info."));
        Object[] spans = ans.getSpans(0, ans.length(), Object.class);
        assertThat(spans.length, is(1));
        assertThat(((StyleSpan) spans[0]).getStyle(), is(Typeface.BOLD));
    }

    @Test
    public void getDialogIconShouldReturnAppIcon() throws Exception {
        when(bundle.getSenderIdentity()).thenReturn("3rd.party.app");
        when(pm.getApplicationInfo("3rd.party.app", 0)).thenReturn(appInfo);
        when(pm.getApplicationIcon(appInfo)).thenReturn(icon);
        params.opCode = WifiRequest.GET_CONNECTION_INFO;
        when(bundle.getRequest()).thenReturn(params);
        target = new ConfirmRequestBinder(activity, bundle);

        Drawable ans = target.getDialogIcon();

        assertThat(ans, sameInstance(icon));
    }

    @Test
    public void getDialogIconShouldReturnNullWhenAppIconCannotBeFound() throws Exception {
        when(bundle.getSenderIdentity()).thenReturn("3rd.party.app");
        when(pm.getApplicationInfo("3rd.party.app", 0)).thenReturn(appInfo);
        when(pm.getApplicationIcon(appInfo)).thenReturn(null);
        params.opCode = WifiRequest.GET_CONNECTION_INFO;
        when(bundle.getRequest()).thenReturn(params);
        target = new ConfirmRequestBinder(activity, bundle);

        Drawable ans = target.getDialogIcon();

        assertThat(ans, nullValue());
    }
}
