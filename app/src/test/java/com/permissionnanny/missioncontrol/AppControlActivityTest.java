package com.permissionnanny.missioncontrol;

import android.view.Menu;
import android.view.MenuItem;
import com.permissionnanny.NannyAppTestRunner;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.dagger.MockActivityComponent;
import com.permissionnanny.dagger.MockComponentFactory;
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

@RunWith(NannyAppTestRunner.class)
public class AppControlActivityTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    ActivityController<AppControlActivity> mController;
    AppControlActivity mAppControlActivity;
    @Inject AppControlBinder mAppControlBinder;
    @Mock Menu mMenu;
    @Mock MenuItem mMenuItem;

    @Before
    public void setUp() throws Exception {
        mController = Robolectric.buildActivity(AppControlActivity.class);
        mAppControlActivity = mController.get();
        MockActivityComponent contextComponent = MockComponentFactory.getActivityComponent();
        mAppControlActivity.inject(contextComponent);
        contextComponent.inject(this);
        mController.setup();
    }

    @Test
    public void lifecycle() throws Exception {
        mController.pause().stop().destroy();
        verify(mAppControlBinder).onCreate(null);
        verify(mAppControlBinder).onResume();
        verify(mAppControlBinder).onPause();
    }

    @Test
    public void onCreateOptionsMenu() throws Exception {
        mAppControlActivity.onCreateOptionsMenu(mMenu);

        verify(mAppControlBinder).onCreateOptionsMenu(mMenu);
    }

    @Test
    public void onOptionsItemSelected() throws Exception {
        mAppControlActivity.onOptionsItemSelected(mMenuItem);

        verify(mAppControlBinder).onOptionsItemSelected(mMenuItem);
    }
}
