package com.bluespark.raffleit.common.views.dialogs

import android.content.Context
import android.support.v4.app.DialogFragment
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import com.bluespark.raffleit.R

open class MyDialogFragment : DialogFragment() {

	override fun onResume() {
		super.onResume()

		setDimens()
		setBackground()
	}

	/**
	 * Set dialog window width and height
	 */
	private fun setDimens() {
		val windowManager =
			context?.applicationContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
		val display = windowManager.defaultDisplay
		val metrics = DisplayMetrics()
		display.getMetrics(metrics)
		val widthDisplay = metrics.widthPixels
		val width = widthDisplay * 0.8
		val height = LinearLayout.LayoutParams.WRAP_CONTENT
		dialog.window!!.setLayout(width.toInt(), height)
	}

	/**
	 * Set the background resource drawable.
	 */
	private fun setBackground() {
		dialog.window!!.setBackgroundDrawableResource(R.drawable.shpae_dialog_fragment_bgr)
	}

	/**
	 * If 'true' the dialog is cancelable.
	 */
	override fun setCancelable(cancelable: Boolean) {
		dialog.setCancelable(cancelable)
	}


	protected fun applyImmersiveFullScreen() {
		// Draw below status bar, android:statusBarColor attribute must be transparent.
		// Hide navigation bar.
		dialog.window?.decorView!!.systemUiVisibility =
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
					View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
					View.SYSTEM_UI_FLAG_IMMERSIVE
	}
}