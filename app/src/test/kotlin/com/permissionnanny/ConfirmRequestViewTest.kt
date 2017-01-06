package com.permissionnanny

import android.app.Activity
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify

class ConfirmRequestViewTest : NannyAppTestCase() {

    private lateinit var target: ConfirmRequestView
    @Mock private lateinit var activity: Activity
    @Mock private lateinit var binder: ConfirmRequestBinder
    @Mock private lateinit var tvTitle: TextView
    @Mock private lateinit var ivIcon: ImageView
    @Mock private lateinit var btnAllow: Button
    @Mock private lateinit var btnDeny: Button
    @Mock private lateinit var stub: TextDialogStubView
    @Mock private lateinit var icon: Drawable
    @Mock private lateinit var title: Spannable

    @Before
    fun setUp() {
        target = ConfirmRequestView(activity, binder, stub)
        target.tvTitle = tvTitle
        target.ivIcon = ivIcon
        target.btnAllow = btnAllow
        target.btnDeny = btnDeny
    }

    @Test
    fun bindViews() {
        given(binder.dialogTitle).willReturn(title)
        given(binder.dialogIcon).willReturn(icon)

        target.bindViews()

        verify(tvTitle).text = title
        verify(ivIcon).setImageDrawable(icon)
        verify(ivIcon).visibility = View.VISIBLE
        verify(btnAllow).setText(R.string.dialog_allow)
        verify(btnDeny).setText(R.string.dialog_deny)
        verify(stub).bindViews()
    }

    @Test
    fun bindViewsShouldHideNullIcon() {
        given(binder.dialogIcon).willReturn(null)

        target.bindViews()

        verify(ivIcon).visibility = View.GONE
    }

    @Test
    fun bindViewsShouldPrependButtonWithAlwaysWhenRememberPreferencesIsChecked() {
        binder.rememberPreference = true

        target.bindViews()

        verify(btnAllow).setText(R.string.dialog_always_allow)
        verify(btnDeny).setText(R.string.dialog_always_deny)
    }
}
