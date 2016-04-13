package com.permissionnanny;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.permissionnanny.dagger.MockActivityComponent;
import com.permissionnanny.dagger.MockComponentFactory;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.mockito.Mockito.verify;

public class ConfirmRequestActivityTest extends NannyAppTestCase {

    ActivityController<ConfirmRequestActivity> mController;
    ConfirmRequestActivity mConfirmRequestActivity;
    @Inject ConfirmRequestBinder mConfirmRequestBinder;
    @Mock Menu mMenu;
    @Mock MenuItem mMenuItem;

    @Before
    public void setUp() throws Exception {
        mController = Robolectric.buildActivity(ConfirmRequestActivity.class).withIntent(new Intent());
        mConfirmRequestActivity = mController.get();
        MockActivityComponent contextComponent = MockComponentFactory.getActivityComponent();
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
