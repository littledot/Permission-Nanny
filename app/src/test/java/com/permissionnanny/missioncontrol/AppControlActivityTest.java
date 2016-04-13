package com.permissionnanny.missioncontrol;

import android.view.Menu;
import android.view.MenuItem;
import com.permissionnanny.NannyAppTestCase;
import com.permissionnanny.dagger.MockActivityComponent;
import com.permissionnanny.dagger.MockComponentFactory;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.mockito.Mockito.verify;

public class AppControlActivityTest extends NannyAppTestCase {

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
