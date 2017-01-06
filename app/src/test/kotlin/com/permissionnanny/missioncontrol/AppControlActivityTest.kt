package com.permissionnanny.missioncontrol

import android.view.Menu
import android.view.MenuItem
import com.permissionnanny.NannyAppTestCase
import com.permissionnanny.dagger.MockComponentFactory
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import org.robolectric.util.ActivityController
import javax.inject.Inject

class AppControlActivityTest : NannyAppTestCase() {

    private lateinit var controller: ActivityController<AppControlActivity>
    private lateinit var appControlActivity: AppControlActivity
    @Inject lateinit var appControlBinder: AppControlBinder
    @Mock internal lateinit var menu: Menu
    @Mock internal lateinit var menuItem: MenuItem

    @Before
    fun setUp() {
        controller = Robolectric.buildActivity(AppControlActivity::class.java)
        appControlActivity = controller.get()
        val contextComponent = MockComponentFactory.activityComponent
        appControlActivity.setTestComponent(contextComponent)
        contextComponent.inject(this)
        controller.setup()
    }

    @Test
    fun lifecycle() {
        controller.pause().stop().destroy()
        verify(appControlBinder).onCreate(null)
        verify(appControlBinder).onResume()
        verify(appControlBinder).onPause()
    }

    @Test
    fun onCreateOptionsMenu() {
        appControlActivity.onCreateOptionsMenu(menu)

        verify(appControlBinder).onCreateOptionsMenu(menu)
    }

    @Test
    fun onOptionsItemSelected() {
        appControlActivity.onOptionsItemSelected(menuItem)

        verify(appControlBinder).onOptionsItemSelected(menuItem)
    }
}
