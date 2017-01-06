package com.permissionnanny

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.permissionnanny.dagger.MockComponentFactory
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import org.robolectric.util.ActivityController
import javax.inject.Inject

class ConfirmRequestActivityTest : NannyAppTestCase() {

    private lateinit var controller: ActivityController<ConfirmRequestActivity>
    private lateinit var confirmRequestActivity: ConfirmRequestActivity
    @Inject internal lateinit var confirmRequestBinder: ConfirmRequestBinder
    @Mock internal lateinit var menu: Menu
    @Mock internal lateinit var menuItem: MenuItem

    @Before
    fun setUp() {
        controller = Robolectric.buildActivity(ConfirmRequestActivity::class.java).withIntent(Intent())
        confirmRequestActivity = controller.get()
        val contextComponent = MockComponentFactory.activityComponent
        confirmRequestActivity.setTestComponent(contextComponent)
        contextComponent.inject(this)
        controller.setup()
    }

    @Test
    fun lifecycle() {
        controller.pause().stop().destroy()

        verify(confirmRequestBinder).onCreate(null)
    }

    @Test
    fun onBackPressed() {
        confirmRequestActivity.onBackPressed()

        verify(confirmRequestBinder).onBackPressed()
    }
}
