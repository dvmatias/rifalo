package com.bluespark.raffleit.common.utils

import android.content.Context
import android.util.DisplayMetrics

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
	}

}