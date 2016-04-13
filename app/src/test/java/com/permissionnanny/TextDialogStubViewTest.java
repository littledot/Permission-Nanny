package com.permissionnanny;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TextDialogStubViewTest extends NannyAppTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    TextDialogStubView mTextDialogStubView;
    @Mock ConfirmRequestBinder mConfirmRequestBinder;
    @Mock ViewStub mViewStub;
    @Mock View vRoot;
    @Mock TextView tvReason;

    @Before
    public void setUp() throws Exception {
        mTextDialogStubView = new TextDialogStubView(mConfirmRequestBinder);
        when(mViewStub.inflate()).thenReturn(vRoot);
        when(vRoot.findViewById(R.id.tvReason)).thenReturn(tvReason);
    }

    @Test
    public void inflateViewStub() throws Exception {
        when(mConfirmRequestBinder.getDialogBody()).thenReturn("dialog body");
        mTextDialogStubView.inflateViewStub(mViewStub);
        mTextDialogStubView.bindViews();

        verify(mViewStub).setLayoutResource(R.layout.dialog_text);
        verify(tvReason).setText("dialog body");
    }
}
