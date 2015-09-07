package com.permissionnanny;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(NannyAppTestRunner.class)
public class ConfirmRequestViewTest {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    ConfirmRequestView target;
    @Mock Activity activity;
    @Mock ConfirmRequestBinder binder;
    @Mock TextDialogStub stub;
    @Mock ImageView ivIcon;
    @Mock TextView tvTitle;
    @Mock Drawable icon;
    @Mock Spannable title;

    @Before
    public void setUp() throws Exception {
        target = new ConfirmRequestView(activity, binder, stub);
        target.tvTitle = tvTitle;
        target.ivIcon = ivIcon;
    }

    @Test
    public void bindViews() throws Exception {
        when(binder.getDialogTitle()).thenReturn(title);
        when(binder.getDialogIcon()).thenReturn(icon);

        target.bindViews();

        verify(tvTitle).setText(title);
        verify(ivIcon).setImageDrawable(icon);
        verify(ivIcon).setVisibility(View.VISIBLE);
        verify(stub).bindViews();
    }

    @Test
    public void bindViewsShouldHideNullIcon() throws Exception {
        when(binder.getDialogIcon()).thenReturn(null);

        target.bindViews();

        verify(ivIcon).setVisibility(View.GONE);
    }
}
