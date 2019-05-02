package com.bluespark.raffleit.common.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_CLICKED
import android.widget.Checkable
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.utils.UiHelper


/**
 * TODO: document your custom view class.
 */
class CheckBoxView : View, Checkable {

	private var _labelString: String? = null // TODO: use a default from R.string...
	private var _baseColor: Int = Color.RED // TODO: use a default from R.color...
	private var _tintColor: Int = Color.BLUE // TODO: use a default from R.color...

	private var textPaint: TextPaint? = null
	private lateinit var strokePaint: Paint
	private lateinit var circlePaint: Paint

	private var onCheckedChangedListener: OnCheckedChangeListener? = null
	private var checked: Boolean = false

	private var _paddingLeft: Int = paddingLeft + UiHelper.convertDpToPx(context, 4).toInt()
	private var _paddingTop: Int = paddingTop + UiHelper.convertDpToPx(context, 4).toInt()
	private var _paddingRight: Int = paddingRight + UiHelper.convertDpToPx(context, 4).toInt()
	private var _paddingBottom: Int = paddingBottom + UiHelper.convertDpToPx(context, 4).toInt()

	/**
	 * The text to draw
	 */
	var labelString: String?
		get() = _labelString
		set(value) {
			_labelString = value
		}

	/**
	 * The base color (stroke and text)
	 */
	var baseColor: Int
		get() = _baseColor
		set(value) {
			_baseColor = value
		}

	/**
	 * The background color
	 */
	var tintColor: Int
		get() = _tintColor
		set(value) {
			_tintColor = value
		}

	/**
	 * In the example view, this drawable is drawn above the text.
	 */
	var exampleDrawable: Drawable? = null

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
		// Load attributes
		val a = context.obtainStyledAttributes(
			attrs, R.styleable.CheckBoxView, defStyle, 0
		)

		try {
			val indexCount = a.indexCount
			for (i in 0 until indexCount) {
				val attr = a.getIndex(i)

				when (attr) {
					R.styleable.CheckBoxView_labelString -> {
						//
						_labelString = a.getString(
							R.styleable.CheckBoxView_labelString
						)
					}
					R.styleable.CheckBoxView_baseColor -> {
						//
						_baseColor = a.getColor(
							R.styleable.CheckBoxView_baseColor,
							baseColor
						)
					}
					R.styleable.CheckBoxView_tintColor -> {
						//
						_tintColor = a.getColor(
							R.styleable.CheckBoxView_tintColor,
							tintColor
						)
					}
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		} finally {

			a.recycle()

			// Set up a default TextPaint object
			textPaint = TextPaint().apply {
				flags = Paint.ANTI_ALIAS_FLAG
				textAlign = Paint.Align.LEFT
				setBackgroundColor(Color.TRANSPARENT)
			}
			// Setup a stroke paint object.
			strokePaint = Paint().apply {
				color = ContextCompat.getColor(context, R.color.sadasdasdasdas)
				isAntiAlias = true
				style = Paint.Style.STROKE
				strokeWidth = 5F
			}
			// Setup a stroke paint object.
			circlePaint = Paint().apply {
				color = Color.TRANSPARENT
				isAntiAlias = true
			}
			exampleDrawable = null

			sendAccessibilityEvent(TYPE_VIEW_CLICKED)

			val outValue = TypedValue()
			context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true)
			this.setBackgroundResource(outValue.resourceId)
		}
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)

		canvas.drawColor(Color.TRANSPARENT)

		val contentWidth = width - _paddingLeft - _paddingRight
		val contentHeight = height - _paddingTop - _paddingBottom

		canvas.drawCircle(
			(width / 2).toFloat(),
			(height / 2).toFloat(),
			UiHelper.convertDpToPx(context, 14),
			circlePaint
		)

		// Draw the example drawable on top of the text.
		exampleDrawable?.let {
			it.setBounds(
				_paddingLeft, _paddingTop,
				_paddingLeft + contentWidth, _paddingTop + contentHeight
			)
			it.draw(canvas)
		}

		canvas.drawCircle(
			(width / 2).toFloat(),
			(height / 2).toFloat(),
			UiHelper.convertDpToPx(context, 14),
			strokePaint
		)

	}

	/**
	 *
	 */
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val desiredWidth = UiHelper.convertDpToPx(context, 32) + _paddingLeft + _paddingRight
		val desiredHeight = UiHelper.convertDpToPx(context, 32) + _paddingTop + _paddingBottom

		setMeasuredDimension(
			measureDimension(desiredWidth.toInt(), widthMeasureSpec),
			measureDimension(desiredHeight.toInt(), heightMeasureSpec)
		)
	}

	private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
		var result: Int
		val specMode = View.MeasureSpec.getMode(measureSpec)
		val specSize = View.MeasureSpec.getSize(measureSpec)

		if (specMode == View.MeasureSpec.EXACTLY) {
			result = specSize
		} else {
			result = desiredSize
			if (specMode == View.MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize)
			}
		}

		if (result < desiredSize) {
			Log.e("ChartView", "The view is too small, the content might get cut")
		}
		return result
	}

	/**
	 * TODO
	 */

	fun setOnCheckedChangedListener(listener: OnCheckedChangeListener) {
		onCheckedChangedListener = listener
		setOnClickListener { toggle() }
	}

	/**
	 * TODO
	 */

	/**
	 * @return The current checked state of the view
	 */
	override fun isChecked(): Boolean {
		return checked
	}

	/**
	 * Change the checked state of the view to the inverse of its current state
	 *
	 */
	override fun toggle() {
		isChecked = !checked
	}

	/**
	 * Change the checked state of the view
	 *
	 * @param checked The new checked state
	 */
	override fun setChecked(p0: Boolean) {
		if (p0 != checked) {
			checked = p0
			refreshDrawableState()

			onCheckedChangedListener?.onCheckedChanged(this, checked)
		}
	}

	/**
	 * Interface definition for a callback to be invoked when the checked state of this View is
	 * changed.
	 */
	interface OnCheckedChangeListener {

		/**
		 * Called when the checked state of a compound button has changed.
		 *
		 * @param checkableView The view whose state has changed.
		 * @param isChecked     The new checked state of checkableView.
		 */
		fun onCheckedChanged(checkableView: View, isChecked: Boolean)
	}
}
