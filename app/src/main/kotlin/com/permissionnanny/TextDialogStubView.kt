package com.permissionnanny

import android.view.ViewStub
import android.widget.TextView
import org.jetbrains.anko.find

/**

 */
open class TextDialogStubView(private val binder: ConfirmRequestBinder) {

    internal lateinit var tvReason: TextView

    open fun inflateViewStub(stub: ViewStub) {
        stub.layoutResource = R.layout.dialog_text
        val view = stub.inflate()
        tvReason = view.find(R.id.tvReason)
    }

    open fun bindViews() {
        tvReason.text = binder.dialogBody
    }
}
