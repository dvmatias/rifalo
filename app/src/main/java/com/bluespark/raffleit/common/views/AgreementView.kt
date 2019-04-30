package com.bluespark.raffleit.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.bluespark.raffleit.R
import kotlinx.android.synthetic.main.view_agreement.view.*

class AgreementView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs),
	View.OnClickListener {

	private lateinit var listener: Listener
	@Suppress("PropertyName")
	var _isEnabled: Boolean = false
		set(value) {
			if (field != value) {
				field = value
				iv_checkbox.isClickable = value
				iv_checkbox.isFocusable = value
			}
		}

	override fun onClick(v: View?) {
		when (v?.id) {
			iv_checkbox.id -> setChecked(!iv_checkbox.isSelected)
		}
	}

	interface Listener {
		fun onAgreementAccepted()
		fun onAgreementRejected()
	}

	init {
		inflate(context, R.layout.view_agreement, this)

		iv_checkbox.setOnClickListener(this)
		setClickListener()
		_isEnabled = false
	}

	private fun setClickListener() {
		if (context is Listener)
			this.listener = this.context as Listener
		else
			throw IllegalAccessException("Calling Activity must implement AgreementView.Listener interface.")
	}

	private fun setCheckStatus(isChecked: Boolean) {
		if (isChecked) {
			setCheckedImage()
			listener.onAgreementAccepted()
		} else {
			setUncheckedImage()
			listener.onAgreementRejected()
		}
	}

	private fun setCheckedImage() {
		iv_checkbox.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle_checked))
	}

	private fun setUncheckedImage() {
		iv_checkbox.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle_unchecked))
	}

	private fun setChecked(isChecked: Boolean) {
		iv_checkbox.isSelected = isChecked
		setCheckStatus(isChecked)
	}

}