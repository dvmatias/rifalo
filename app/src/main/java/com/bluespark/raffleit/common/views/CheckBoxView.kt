package com.bluespark.raffleit.common.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.bluespark.raffleit.R
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
	private lateinit var strokePaint: Paint
	private lateinit var circlePaint: Paint
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
				setBackgroundColor(Color.TRANSPARENT)
			}
			// Setup a stroke paint object.
			strokePaint = Paint().apply {
				color =ContextCompat.getColor(context, R.color.sadasdasdasdas)
				isAntiAlias = true
				style = Paint.Style.STROKE
				strokeWidth = 5F
			}
			// Setup a stroke paint object.
			circlePaint = Paint().apply {
				color = Color.TRANSPARENT
				isAntiAlias = true
			}
//			exampleDrawable = context.getDrawable(R.drawable.b)
			exampleDrawable = null

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

		canvas.drawColor(Color.TRANSPARENT)

		// TODO: consider storing these as member variables to reduce allocations per draw cycle.
		val paddingLeft = paddingLeft + convertDpToPx(2).toInt()
		val paddingTop = paddingTop + convertDpToPx(2).toInt()
		val paddingRight = paddingRight + convertDpToPx(2).toInt()
		val paddingBottom = paddingBottom + convertDpToPx(2).toInt()

		val contentWidth = width - paddingLeft - paddingRight
		val contentHeight = height - paddingTop - paddingBottom

//		labelString?.let {
//			// Draw the text.
//			canvas.drawText(
//				it,
//				paddingLeft + (contentWidth - textWidth) / 2,
//				paddingTop + (contentHeight + textHeight) / 2,
//				textPaint
//			)
//		}

		canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(), convertDpToPx(14), circlePaint)

		// Draw the example drawable on top of the text.
		exampleDrawable?.let {
			it.setBounds(
				paddingLeft, paddingTop,
				paddingLeft + contentWidth, paddingTop + contentHeight
			)
			it.draw(canvas)
		}

		canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(), convertDpToPx(14), strokePaint)


	}

	/**
	 *
	 */
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		Log.v("Chart onMeasure w", View.MeasureSpec.toString(widthMeasureSpec))
		Log.v("Chart onMeasure h", View.MeasureSpec.toString(heightMeasureSpec))

		val desiredWidth = convertDpToPx(32) + paddingLeft + paddingRight
		val desiredHeight = convertDpToPx(32) + paddingTop + paddingBottom

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

	fun convertDpToPx(dp: Int): Float {
		val displayMetrics = context.resources.displayMetrics
		return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
	}

	fun convertPxToDp(px: Int): Int {
		val displayMetrics = context.resources.displayMetrics
		return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
	}

}
