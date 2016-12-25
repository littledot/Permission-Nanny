package com.permissionnanny

import android.view.View
import android.view.ViewStub
import android.widget.TextView
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify

class TextDialogStubViewTest : NannyAppTestCase() {

    private lateinit var textDialogStubView: TextDialogStubView
    @Mock private lateinit var confirmRequestBinder: ConfirmRequestBinder
    @Mock private lateinit var viewStub: ViewStub
    @Mock private lateinit var vRoot: View
    @Mock private lateinit var tvReason: TextView

    @Before
    fun setUp() {
        textDialogStubView = TextDialogStubView(confirmRequestBinder)
        given(viewStub.inflate()).willReturn(vRoot)
        given(vRoot.findViewById(R.id.tvReason)).willReturn(tvReason)
    }

    @Test
    fun inflateViewStub() {
        given(confirmRequestBinder.dialogBody).willReturn("dialog body")
        textDialogStubView.inflateViewStub(viewStub)
        textDialogStubView.bindViews()

        verify(viewStub).layoutResource = R.layout.dialog_text
        verify(tvReason).text = "dialog body"
    }
}
