package com.bluespark.raffleit.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.bluespark.raffleit.R

class EditTextCustomView : LinearLayout {

	constructor(context: Context) : super(context) {
		init()
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		init()
	}

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
		context,
		attrs,
		defStyleAttr
	) {
		init()
	}

	private fun init() {
		View.inflate(context, R.layout.custom_view_edit_text, this)
	}

}