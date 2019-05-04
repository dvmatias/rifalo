package com.bluespark.raffleit.common.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluespark.raffleit.R
import kotlinx.android.synthetic.main.fragment_warning_dialog.*

/**
 * TODO dec.
 */

class WarningDialogFragmentImpl : MyDialogFragment() {
	private var title: String? = null
	private var msg: String? = null
	private var labelButton: String? = null

	companion object {
		@JvmStatic
		fun newInstance() = WarningDialogFragmentImpl()

		val TAG: String
			get() {
				val tag = WarningDialogFragmentImpl::class.java.simpleName
				return if (tag.length <= 23) tag else tag.substring(0, 23)
			}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (msg == null)
			throw IllegalAccessException("WarningDialogFragmentImpl: You must set (at least) a message for this dialog. Use setup() method.")
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_warning_dialog, container, false)
	}

	override fun onResume() {
		super.onResume()
		isCancelable = false
		applyImmersiveFullScreen()

		setupTitle()
		setupMessage()
		setupButton()
	}

	private fun setupTitle() {
		if (title.isNullOrEmpty()) tv_title.visibility = View.GONE else {
			tv_title.visibility = View.VISIBLE
			tv_title.text = title
		}
	}

	private fun setupMessage() {
		if (msg.isNullOrEmpty()) tv_msg.visibility = View.GONE else {
			tv_msg.visibility = View.VISIBLE
			tv_msg.text = msg
		}
	}

	private fun setupButton() {
		tv_btn.apply {
			if (labelButton.isNullOrEmpty())
				visibility = View.GONE
			else {
				visibility = View.VISIBLE
				text = labelButton
				setOnClickListener { dismiss() }
			}
		}
	}

	fun setup(title: String, msg: String, labelButton: String) {
		this.title = title
		this.msg = msg
		this.labelButton = labelButton
	}
}