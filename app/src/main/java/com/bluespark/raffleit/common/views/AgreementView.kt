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
			}
			check_box_view.isEnabled = value
			alpha = if (value) 1F else 0.5F

		}

	override fun onClick(v: View?) {
		when (v?.id) {
			check_box_view.id -> setChecked(check_box_view.isChecked)
		}
	}

	interface Listener {
		fun onAgreementAccepted()
		fun onAgreementRejected()
	}

	init {
		inflate(context, R.layout.view_agreement, this)

		check_box_view.isEnabled = false
		setClickListener()
		_isEnabled = false
		check_box_view.setOnCheckedChangedListener(object: CheckBoxView.OnCheckedChangeListener{
			override fun onCheckedChanged(checkableView: View, isChecked: Boolean) {
				setChecked(isChecked)
			}

		})
	}

	private fun setClickListener() {
		if (context is Listener)
			this.listener = this.context as Listener
		else
			throw IllegalAccessException("Calling Activity must implement AgreementView.Listener interface.")
	}

	private fun setCheckStatus(isChecked: Boolean) {
		if (isChecked) {
			listener.onAgreementAccepted()
		} else {
			listener.onAgreementRejected()
		}
	}

	private fun setChecked(isChecked: Boolean) {
		setCheckStatus(isChecked)
	}

}