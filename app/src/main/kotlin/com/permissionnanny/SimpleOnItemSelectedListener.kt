package com.permissionnanny

import android.view.View
import android.widget.AdapterView

open class SimpleOnItemSelectedListener : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
    }
}
