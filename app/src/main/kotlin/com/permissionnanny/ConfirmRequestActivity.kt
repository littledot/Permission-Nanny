package com.permissionnanny

import android.os.Bundle
import javax.inject.Inject

/**

 */
class ConfirmRequestActivity : BaseActivity() {

    @Inject internal lateinit var binder: ConfirmRequestBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent().inject(this)
        binder.preOnCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        binder.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binder.onBackPressed()
    }
}
