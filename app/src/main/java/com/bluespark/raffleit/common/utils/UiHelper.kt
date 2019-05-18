package com.bluespark.raffleit.common.utils

import android.content.Context
import android.util.DisplayMetrics
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * TODO
 */
class UiHelper {

	companion object {
		/**
		 * TODO
		 */
		fun convertDpToPx(context: Context, dp: Int): Float {
			val displayMetrics = context.resources.displayMetrics
			return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
		}

		/**
		 * TODO
		 */
		fun convertPxToDp(context: Context, px: Int): Int {
			val displayMetrics = context.resources.displayMetrics
			return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
		}

		fun hideKeyboardFrom(context: Context, view: View) {
			val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
			imm.hideSoftInputFromWindow(view.windowToken, 0)
		}
	}

}