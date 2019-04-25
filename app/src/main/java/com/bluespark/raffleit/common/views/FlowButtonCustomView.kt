package com.bluespark.raffleit.common.views

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.views.FlowButtonCustomView.InternalListener
import kotlinx.android.synthetic.main.custom_viewflow_button.view.*

/**
 * This class represents a custom button with a [CardView]
 * as main background and a [TextView] as button label.
 *
 * Has the inner class [InternalListener] to translate clicks
 * on this view.
 *
 * The user can configure custom attributes such as:
 *   - Button label text: [R.styleable.FlowButtonCustomView_labelText]
 *   - Button label color: [R.styleable.FlowButtonCustomView_labelColor]
 *   - Button background color: [R.styleable.FlowButtonCustomView_buttonColor]
 *
 * @author matias.delv.dom@gmail.com
 */
class FlowButtonCustomView : CardView {

	private val listenerAdapter = InternalListener()

	private var _labelText: String =
		resources.getString(R.string.label_flow_button_custom_view_default_string)
	private var _labelColor: Int =
		ContextCompat.getColor(context, R.color.label_flow_button_custom_view_default_color)
	private var _buttonColor: Int? =
		ContextCompat.getColor(context, R.color.button_flow_button_custom_view_default_color)

	/**
	 * Button label text.
	 */
	var labelText: String
		get() = _labelText
		set(value) {
			_labelText = value
			tv_label.text = labelText
		}

	/**
	 * Button label color.
	 */
	var labelColor: Int
		get() = _labelColor
		set(value) {
			_labelColor = value
			tv_label.setTextColor(labelColor)
		}

	/**
	 * Button background color.
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

	/**
	 * Init the view.
	 *
	 * @param attrs Attributes set.
	 * @param defStyle Default style
	 */
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
						_labelColor = typedArray.getColor(
							R.styleable.FlowButtonCustomView_labelColor,
							_labelColor
						)
					}
					R.styleable.FlowButtonCustomView_buttonColor -> {
						//
						_buttonColor = typedArray.getColor(
							R.styleable.FlowButtonCustomView_buttonColor,
							_buttonColor!!
						)
					}
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			labelText = _labelText
			labelColor = _labelColor
			buttonColor = _buttonColor

			isClickable = true
			isFocusable = true
			setOnClickListener(listenerAdapter)

			typedArray.recycle()
		}
	}

	/**
	 * Sets the listener object that is triggered when the view is clicked.
	 *
	 * @param clickListener The instance of the listener to trigger.
	 */
	override fun setOnClickListener(clickListener: OnClickListener) {
		listenerAdapter.setListener(clickListener)
		cv.setOnClickListener(listenerAdapter)
	}

	/**
	 * Internal click listener class. Translates a view’s click listener to
	 * one that is more appropriate for this custom button class.
	 *
	 * @author matias.delv.dom@gmail.com
	 */
	inner class InternalListener : View.OnClickListener {
		private var listener: OnClickListener? = null

		override fun onClick(v: View?) {
			listener?.onClick(this@FlowButtonCustomView)
		}

		fun setListener(clickListener: OnClickListener) {
			listener = clickListener
		}

	}

}
