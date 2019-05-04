package com.bluespark.raffleit.common.views

import android.content.Context
import android.graphics.Color
import android.support.annotation.Nullable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.LinearLayout
import com.bluespark.raffleit.R
import kotlinx.android.synthetic.main.custom_view_edit_text.view.*

class EditTextCustomView : LinearLayout, TextWatcher {

	private var _titleText: String =
		context.resources.getString(R.string.title_custom_view_edit_text_default_string)

	private var _titleColor: String =
		context.resources.getString(R.string.title_custom_view_edit_default_color)

	private var _hintText: String =
		context.resources.getString(R.string.hint_custom_view_edit_text_default_string)

	private var _errorText: String = ""

	private var _inputType: Int = 1

	private var _imeOptions: Int = 1

	private var titleText: String
		get() = _titleText
		set(value) {
			_titleText = value
			tv_title.text = _titleText
		}

	private var hintText: String
		get() = _hintText
		set(value) {
			_hintText = value
			et.hint = _hintText
		}

	private var titleColor: String
		get() = _titleColor
		set(value) {
			_titleColor = value
			tv_title.setTextColor(Color.parseColor(_titleColor))
		}

	private var errorText: String
		get() = _errorText
		set(value) {
			_errorText = value
			tv_error.text = _errorText
		}

	private var inputType: Int
		get() = _inputType
		set(value) {
			_inputType = value
			et.inputType = _inputType
		}

	private var imeOptions: Int
		get() = _imeOptions
		set(value) {
			_imeOptions = value
			et.imeOptions = _imeOptions
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

	@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
	private fun init(attrs: AttributeSet?, defStyle: Int) {
		View.inflate(context, R.layout.custom_view_edit_text, this)
		// Load attributes
		val typedArray = context.obtainStyledAttributes(
			attrs, R.styleable.EditTextCustomView, defStyle, 0
		)

		try {
			val indexCount = typedArray.indexCount
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
					R.styleable.EditTextCustomView_android_inputType -> {
						//
						_inputType =
							typedArray.getInt(R.styleable.EditTextCustomView_android_inputType, 1)
						inputType = _inputType
					}
					R.styleable.EditTextCustomView_android_imeOptions -> {
						//
						_imeOptions =
							typedArray.getInt(R.styleable.EditTextCustomView_android_imeOptions, 1)
						imeOptions = _imeOptions
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
			et.addTextChangedListener(this)

			setStatusNormal()

			typedArray.recycle()
		}
	}

	@Suppress("unused")
	fun setStatusNormal() {
		v_bgr.setBackgroundResource(R.drawable.bgr_sign_in_activity_edit_text_normal)
		hideError()
	}

	@Suppress("unused")
	fun setStatusError(@Nullable errorMsg: String) {
		v_bgr.setBackgroundResource(R.drawable.bgr_sign_in_activity_edit_text_error)
		_errorText = errorMsg
		if (!_errorText.isBlank()) {
			errorText = _errorText
			showError()
		}
	}

	@Suppress("unused")
	fun setStatusValid() {
		v_bgr .setBackgroundResource(R.drawable.bgr_sign_in_activity_edit_text_valid)
	}

	private fun hideError() {
		tv_error.visibility = View.INVISIBLE
	}

	private fun showError() {
		tv_error.visibility = View.VISIBLE
		animateError()
	}

	private fun animateError() {
		val animShake = AnimationUtils.loadAnimation(context, R.anim.shake_inline_error)
		animShake.interpolator = BounceInterpolator()
		tv_error.startAnimation(animShake)
	}

	fun setText(text: String) {
		et.setText(text)
	}

	fun getText(): String {
		return et.text.toString()
	}

	/**
	 * [TextWatcher] implementation.
	 */

	override fun afterTextChanged(s: Editable?) {

	}

	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

	}

	override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
		// Set to normal status.
		setStatusNormal()
	}
}