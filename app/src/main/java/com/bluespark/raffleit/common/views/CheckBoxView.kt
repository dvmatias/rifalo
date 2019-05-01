package com.bluespark.raffleit.common.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.bluespark.raffleit.R
import android.opengl.ETC1.getWidth
import android.util.Log
import android.util.DisplayMetrics




/**
 * TODO: document your custom view class.
 */
class CheckBoxView : View {

	private var _labelString: String? = null // TODO: use a default from R.string...
	private var _baseColor: Int = Color.RED // TODO: use a default from R.color...
	private var _tintColor: Int = Color.BLUE // TODO: use a default from R.color...

	private var textPaint: TextPaint? = null
	private var textWidth: Float = 0f
	private var textHeight: Float = 0f

	/**
	 * The text to draw
	 */
	var labelString: String?
		get() = _labelString
		set(value) {
			_labelString = value
			invalidateTextPaintAndMeasurements()
		}

	/**
	 * The base color (stroke and text)
	 */
	var baseColor: Int
		get() = _baseColor
		set(value) {
			_baseColor = value
			invalidateTextPaintAndMeasurements()
		}

	/**
	 * The background color
	 */
	var tintColor: Int
		get() = _tintColor
		set(value) {
			_tintColor = value
			invalidateTextPaintAndMeasurements()
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
			}

			// Update TextPaint and text measurements from attributes
			invalidateTextPaintAndMeasurements()
		}
	}

	private fun invalidateTextPaintAndMeasurements() {
		if (labelString.isNullOrEmpty())
			return
		textPaint?.let {
			it.textSize = 48f
			it.color = baseColor
			textWidth = it.measureText(labelString)
			textHeight = it.fontMetrics.bottom
		}
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)

		// TODO: consider storing these as member variables to reduce allocations per draw cycle.
		val paddingLeft = paddingLeft
		val paddingTop = paddingTop
		val paddingRight = paddingRight
		val paddingBottom = paddingBottom

		val contentWidth = width - paddingLeft - paddingRight
		val contentHeight = height - paddingTop - paddingBottom

		labelString?.let {
			// Draw the text.
			canvas.drawText(
				it,
				paddingLeft + (contentWidth - textWidth) / 2,
				paddingTop + (contentHeight + textHeight) / 2,
				textPaint
			)
		}

		// Draw the example drawable on top of the text.
		exampleDrawable?.let {
			it.setBounds(
				paddingLeft, paddingTop,
				paddingLeft + contentWidth, paddingTop + contentHeight
			)
			it.draw(canvas)
		}

		canvas.drawColor(Color.RED)
	}

	/**
	 *
	 */
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		Log.v("Chart onMeasure w", View.MeasureSpec.toString(widthMeasureSpec))
		Log.v("Chart onMeasure h", View.MeasureSpec.toString(heightMeasureSpec))

		val desiredWidth = dpToPx(24) + paddingLeft + paddingRight
		val desiredHeight = dpToPx(24) + paddingTop + paddingBottom

		setMeasuredDimension(
			measureDimension(desiredWidth, widthMeasureSpec),
			measureDimension(desiredHeight, heightMeasureSpec)
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

	fun dpToPx(dp: Int): Int {
		val displayMetrics = context.resources.displayMetrics
		return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
	}

	fun pxToDp(px: Int): Int {
		val displayMetrics = context.resources.displayMetrics
		return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
	}

}
