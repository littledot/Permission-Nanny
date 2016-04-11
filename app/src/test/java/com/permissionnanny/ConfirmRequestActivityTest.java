package com.permissionnanny;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.dagger.MockComponentFactory;
import com.permissionnanny.dagger.MockContextComponent;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.mockito.Mockito.verify;

/**
 *
 */
@RunWith(NannyAppTestRunner.class)
public class ConfirmRequestActivityTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    ActivityController<ConfirmRequestActivity> mController;
    ConfirmRequestActivity mConfirmRequestActivity;
    @Inject ConfirmRequestBinder mConfirmRequestBinder;
    @Mock Menu mMenu;
    @Mock MenuItem mMenuItem;

    @Before
    public void setUp() throws Exception {
        mController = Robolectric.buildActivity(ConfirmRequestActivity.class).withIntent(new Intent());
        mConfirmRequestActivity = mController.get();
        MockContextComponent contextComponent = MockComponentFactory.getContextComponent();
        mConfirmRequestActivity.inject(contextComponent);
        contextComponent.inject(this);
        mController.setup();
    }

    @Test
    public void lifecycle() throws Exception {
        mController.pause().stop().destroy();
        verify(mConfirmRequestBinder).onCreate(null);
    }

    @Test
    public void onBackPressed() throws Exception {
        mConfirmRequestActivity.onBackPressed();

        verify(mConfirmRequestBinder).onBackPressed();
    }
}
