package com.bluespark.raffleit.common.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.annotation.Nullable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Checkable
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.utils.UiHelper


/**
 * Custom Check Box View.
 *
 * Class to paint a checkable object.
 *
 * @author matias.delv.dom@gmail.com
 */
class CheckBoxView : View, Checkable {

	// Default attributes values.
	private var _baseColor: Int =
		ContextCompat.getColor(context, R.color.color_check_box_view_tint_default)
	private var _tintColor: Int =
		ContextCompat.getColor(context, R.color.color_check_box_view_base_default)
	// Paint objects.
	private lateinit var strokePaint: Paint
	private lateinit var circlePaint: Paint
	// Default background drawable.
	private var bgrDrawable: Drawable? = null
	// On checked listener instance.
	private var onCheckedChangedListener: OnCheckedChangeListener? = null
	// View current checked status.
	private var checked: Boolean = false
	// View padding.
	private var _paddingLeft: Int = paddingLeft + UiHelper.convertDpToPx(context, 4).toInt()
	private var _paddingTop: Int = paddingTop + UiHelper.convertDpToPx(context, 4).toInt()
	private var _paddingRight: Int = paddingRight + UiHelper.convertDpToPx(context, 4).toInt()
	private var _paddingBottom: Int = paddingBottom + UiHelper.convertDpToPx(context, 4).toInt()

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
	 * Init.
	 */
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
			// Setup a stroke paint object.
			strokePaint = Paint().apply {
				color = _baseColor
				isAntiAlias = true
				style = Paint.Style.STROKE
				strokeWidth = 5F
			}
			// Init view.
			initStatus()
			// set a default checked change listener.
			setOnCheckedChangedListener(object : CheckBoxView.OnCheckedChangeListener {
				override fun onCheckedChanged(checkableView: View, isChecked: Boolean) {}
			})
			// Set selectable item background.
			val outValue = TypedValue()
			context.theme.resolveAttribute(
				android.R.attr.selectableItemBackgroundBorderless,
				outValue,
				true
			)
			this.setBackgroundResource(outValue.resourceId)
		}
	}

	/**
	 * Init the view in non checked status.
	 */
	private fun initStatus() {
		checked = false
		setViewStatus()
	}

	/**
	 * Set view status according to [checked] values.
	 */
	private fun setViewStatus() {
		if (checked) setViewStatusChecked() else setViewStatusUnchecked()
		invalidate()
	}

	/**
	 * Set view for [checked] = true.
	 */
	private fun setViewStatusChecked() {
		// Background drawable
		bgrDrawable = ContextCompat.getDrawable(context, R.drawable.img_check_box_view_bgr)
		//Background fill paint object.
		circlePaint = Paint().apply {
			color = _tintColor
			style = Paint.Style.FILL
			isAntiAlias = true
		}
	}

	/**
	 * Set view for [checked] = false.
	 */
	private fun setViewStatusUnchecked() {
		// Background drawable
		bgrDrawable = null
		//Background fill paint object.
		circlePaint = Paint().apply {
			color = Color.TRANSPARENT
			style = Paint.Style.FILL
			isAntiAlias = true
		}
	}

	/**
	 * Draw view.
	 */
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
		bgrDrawable?.let {
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
	 * On measure.
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
			Log.e("CheckBoxView", "The view is too small, the content might get cut")
		}
		return result
	}

	/**
	 * Set [onCheckedChangedListener] listener.
	 */
	@Suppress("unused")
	fun setOnCheckedChangedListener(@Nullable listener: OnCheckedChangeListener?) {
		onCheckedChangedListener = listener
		setOnClickListener { toggle() }
	}

	/**
	 * [Checkable] implementation.
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
	 * @param p0 The new checked state
	 */
	override fun setChecked(p0: Boolean) {
		if (p0 != checked) {
			checked = p0
			setViewStatus()
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
