package com.bluespark.raffleit.common.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.bluespark.raffleit.R
import kotlinx.android.synthetic.main.custom_view_edit_text.view.*

class EditTextCustomView : LinearLayout {

	private var _titleText: String =
		context.resources.getString(R.string.custom_view_edit_text_title_default)

	private var _titleColor: String =
		context.resources.getString(R.string.custom_view_edit_title_color_default)

	private var _hintText: String =
		context.resources.getString(R.string.custom_view_edit_text_hint_default)

	private var titleText: String
		get() = _titleText
		set(value) {
			_titleText = value
		}

	private var hintText: String
		get() = _hintText
		set(value) {
			_hintText = value
		}

	private var titleColor: String
		get() = _titleColor
		set(value) {
			_titleColor = value
		}

	constructor(context: Context) : super(context) {
		init(null, 0)
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		init(attrs, 0)
	}

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
		context,
		attrs,
		defStyleAttr
	) {
		init(attrs, defStyleAttr)
	}

	private fun init(attrs: AttributeSet?, defStyle: Int) {
		View.inflate(context, R.layout.custom_view_edit_text, this)
		// Load attributes
		val typedArray = context.obtainStyledAttributes(
			attrs, R.styleable.EditTextCustomView, defStyle, 0
		)
		try {
			val indexCount = typedArray.getIndexCount()
			for (i in 0 until indexCount) {
				val attr = typedArray.getIndex(i)

				when (attr) {
					R.styleable.EditTextCustomView_titleText -> {
						//
						_titleText = typedArray.getString(R.styleable.EditTextCustomView_titleText)
						if (!_titleText.isBlank())
							titleText = _titleText
					}
					R.styleable.EditTextCustomView_titleColor -> {
						//
						_titleColor =
							typedArray.getString(R.styleable.EditTextCustomView_titleColor)
						if (!_titleColor.isBlank())
							titleColor = _titleColor
					}
					R.styleable.EditTextCustomView_hintText -> {
						//
						_hintText = typedArray.getString(R.styleable.EditTextCustomView_hintText)
						if (!_hintText.isBlank())
							hintText = _hintText
					}
				}
			}
			//      TODO: Manage rest of attributes.
			//		<attr name="titleSize" format="dimension" />
			//		<attr name="errorText" format="string" />
			//		<attr name="errorColor" format="color" />
			//		<attr name="errorSize" format="dimension" />
			//		<attr name="hintColor" format="color" />
		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			tv_title.text = titleText
			tv_title.setTextColor(Color.parseColor(titleColor))
			et_user_input.hint = hintText

			hideError()

			typedArray.recycle()
		}
	}

	private fun hideError() {
		tv_error.visibility = View.GONE
	}

}