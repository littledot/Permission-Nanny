package com.permissionnanny

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find

/**

 */
class ConfirmRequestView(
        private val activity: Activity,
        private val binder: ConfirmRequestBinder,
        private val textStub: TextDialogStubView) {

    lateinit var tvTitle: TextView
    lateinit var ivIcon: ImageView
    lateinit var vsStub: ViewStub
    lateinit var cbRemember: CheckBox
    lateinit var btnAllow: Button
    lateinit var btnDeny: Button

    fun preOnCreate(state: Bundle?) {
        activity.setTheme(R.style.Nanny_Light_Dialog_NoActionBar)
    }

    fun onCreate(state: Bundle?) {
        activity.setContentView(R.layout.dialog)
        tvTitle = activity.find(R.id.tvTitle)
        ivIcon = activity.find(R.id.ivIcon)
        vsStub = activity.find(R.id.vsStub)
        cbRemember = activity.find(R.id.remember)
        btnAllow = activity.find(R.id.btnPositive)
        btnDeny = activity.find(R.id.btnNegative)
        textStub.inflateViewStub(vsStub)
        if (Build.VERSION.SDK_INT >= 11) {
            activity.setFinishOnTouchOutside(false)
        }
        cbRemember.setText(R.string.dialog_remember)

        cbRemember.setOnCheckedChangeListener { view, isChecked -> onRememberPreferenceChanged(isChecked) }
        btnAllow.setOnClickListener { onAllow() }
        btnDeny.setOnClickListener { onDeny() }
    }

    fun bindViews() {
        tvTitle.text = binder.dialogTitle
        val icon = binder.dialogIcon
        ivIcon.visibility = if (icon == null) View.GONE else View.VISIBLE
        ivIcon.setImageDrawable(icon)
        val remember = binder.rememberPreference
        btnAllow.setText(if (remember) R.string.dialog_always_allow else R.string.dialog_allow)
        btnDeny.setText(if (remember) R.string.dialog_always_deny else R.string.dialog_deny)
        textStub.bindViews()
    }

    internal fun onRememberPreferenceChanged(isChecked: Boolean) {
        binder.changeRememberPreference(isChecked)
    }

    internal fun onAllow() {
        binder.executeAllow()
        activity.finish()
    }

    internal fun onDeny() {
        binder.executeDeny()
        activity.finish()
    }
}
