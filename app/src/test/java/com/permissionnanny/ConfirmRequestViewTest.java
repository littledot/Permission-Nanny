package com.permissionnanny;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.permissionnanny.common.test.NannyTestCase;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(NannyAppTestRunner.class)
public class ConfirmRequestViewTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    ConfirmRequestView target;
    @Mock Activity activity;
    @Mock ConfirmRequestBinder binder;
    @Mock TextView tvTitle;
    @Mock ImageView ivIcon;
    @Mock Button btnAllow;
    @Mock Button btnDeny;
    @Mock TextDialogStubView stub;
    @Mock Drawable icon;
    @Mock Spannable title;

    @Before
    public void setUp() throws Exception {
        target = new ConfirmRequestView(activity, binder, stub);
        target.tvTitle = tvTitle;
        target.ivIcon = ivIcon;
        target.btnAllow = btnAllow;
        target.btnDeny = btnDeny;
    }

    @Test
    public void bindViews() throws Exception {
        when(binder.getDialogTitle()).thenReturn(title);
        when(binder.getDialogIcon()).thenReturn(icon);

        target.bindViews();

        verify(tvTitle).setText(title);
        verify(ivIcon).setImageDrawable(icon);
        verify(ivIcon).setVisibility(View.VISIBLE);
        verify(btnAllow).setText(R.string.dialog_allow);
        verify(btnDeny).setText(R.string.dialog_deny);
        verify(stub).bindViews();
    }

    @Test
    public void bindViewsShouldHideNullIcon() throws Exception {
        when(binder.getDialogIcon()).thenReturn(null);

        target.bindViews();

        verify(ivIcon).setVisibility(View.GONE);
    }

    @Test
    public void bindViewsShouldPrependButtonWithAlwaysWhenRememberPreferencesIsChecked() throws Exception {
        when(binder.getRememberPreference()).thenReturn(true);

        target.bindViews();

        verify(btnAllow).setText(R.string.dialog_always_allow);
        verify(btnDeny).setText(R.string.dialog_always_deny);
    }
}
