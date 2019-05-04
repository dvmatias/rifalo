package com.bluespark.raffleit.common.views.dialogs

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import com.bluespark.raffleit.R
import android.view.animation.LinearInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import kotlinx.android.synthetic.main.fragment_loading_dialog.*

/**
 * TODO dec.
 */

class LoadingDialogFragment : MyDialogFragment() {

	companion object {
		@JvmStatic
		fun newInstance() = LoadingDialogFragment()

		val TAG: String
			get() {
				val tag = LoadingDialogFragment::class.java.simpleName
				return if (tag.length <= 23) tag else tag.substring(0, 23)
			}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_loading_dialog, container, false)
	}

	override fun onResume() {
		super.onResume()
		isCancelable = false
		applyImmersiveFullScreen()

		animateLoadingImage()
	}

	private fun animateLoadingImage() {
		val rotate = RotateAnimation(
			0f,
			360f,
			Animation.RELATIVE_TO_SELF,
			0.5f,
			Animation.RELATIVE_TO_SELF,
			0.5f
		)
		rotate.duration = 1750
		rotate.interpolator = LinearInterpolator()
		rotate.repeatCount = Animation.INFINITE

		iv_loading.startAnimation(rotate)

		dialog.setCancelable(false)
	}
}
