package com.bluespark.raffleit.common.views

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bluespark.raffleit.R
import kotlinx.android.synthetic.main.custom_viewflow_button.view.*

/**
 * TODO: document your custom view class.
 */
class FlowButtonCustomView : CardView {

	private var _labelText: String =
		resources.getString(R.string.label_flow_button_custom_view_default_string)
	private var _labelColor: Int =
		ContextCompat.getColor(context, R.color.label_flow_button_custom_view_default_color)
	private var _buttonColor: Int? =
		ContextCompat.getColor(context, R.color.button_flow_button_custom_view_default_color)

	/**
	 * Button label text
	 */
	var labelText: String
		get() = _labelText
		set(value) {
			_labelText = value
			tv_label.text = labelText
		}

	/**
	 * Button label color
	 */
	var labelColor: Int
		get() = _labelColor
		set(value) {
			_labelColor = value
			tv_label.setTextColor(labelColor)
		}

	/**
	 * Button background color
	 */
	var buttonColor: Int?
		get() = _buttonColor
		set(value) {
			_buttonColor = value
			cv.setCardBackgroundColor(_buttonColor!!)
		}

	constructor(context: Context) : super(context) {
		init(null, 0)
	}

	constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
		init(attrs, 0)
	}

	constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
		context,
		attrs,
		defStyle
	) {
		init(attrs, defStyle)
	}

	private fun init(attrs: AttributeSet?, defStyle: Int) {
		View.inflate(context, R.layout.custom_viewflow_button, this)
		setBackgroundColor(Color.TRANSPARENT)
		// Load attributes
		val typedArray = context.obtainStyledAttributes(
			attrs, R.styleable.FlowButtonCustomView, defStyle, 0
		)

		try {
			val indexCount = typedArray.getIndexCount()
			for (i in 0 until indexCount) {
				val attr = typedArray.getIndex(i)

				when (attr) {
					R.styleable.FlowButtonCustomView_labelText -> {
						//
						_labelText = typedArray.getString(R.styleable.EditTextCustomView_titleText)
					}
					R.styleable.FlowButtonCustomView_labelColor -> {
						//
						_labelColor = typedArray.getColor(R.styleable.FlowButtonCustomView_labelColor, _labelColor)
					}
					R.styleable.FlowButtonCustomView_buttonColor -> {
						//
						_buttonColor = typedArray.getColor(R.styleable.FlowButtonCustomView_buttonColor, _buttonColor!!)
					}
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			labelText = _labelText
			labelColor = _labelColor
			buttonColor = _buttonColor
			typedArray.recycle()
		}
//
//		_exampleString = a.getString(
//			R.styleable.SignButtonCustomView_exampleString
//		)
//		_exampleColor = a.getColor(
//			R.styleable.SignButtonCustomView_exampleColor,
//			exampleColor
//		)
//		// Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
//		// values that should fall on pixel boundaries.
//		_exampleDimension = a.getDimension(
//			R.styleable.SignButtonCustomView_exampleDimension,
//			exampleDimension
//		)
//
//		if (a.hasValue(R.styleable.SignButtonCustomView_exampleDrawable)) {
//			exampleDrawable = a.getDrawable(
//				R.styleable.SignButtonCustomView_exampleDrawable
//			)
//			exampleDrawable?.callback = this
//		}
//
//		a.recycle()
//
//		// Set up a default TextPaint object
//		textPaint = TextPaint().apply {
//			flags = Paint.ANTI_ALIAS_FLAG
//			textAlign = Paint.Align.LEFT
//		}
//
//		// Update TextPaint and text measurements from attributes
//		invalidateTextPaintAndMeasurements()
	}

//	override fun setOnClickListener(clickListener: OnClickListener) {
//		cv.setOnClickListener(clickListener)
//	}

}
