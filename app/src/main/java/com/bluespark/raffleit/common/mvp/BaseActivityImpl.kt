package com.bluespark.raffleit.common.mvp

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bluespark.raffleit.common.injection.application.ApplicationComponent
import com.bluespark.raffleit.common.injection.application.MyApplication
import com.bluespark.raffleit.common.injection.presentation.PresentationComponent
import com.bluespark.raffleit.common.injection.presentation.PresentationModule

/**
 * MVP - Base Activity.
 *
 * Every Activity shall extend this class.
 *
 * @author matias.delv.dom@gmail.com
 */

abstract class BaseActivityImpl : AppCompatActivity(), BaseView {

	private var isInjectorUsed: Boolean = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	fun applyImmersiveFullScreen() {
		// Draw below status bar, android:statusBarColor attribute must be transparent.
		// Hide navigation bar.
		window.decorView.systemUiVisibility =
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
					View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
					View.SYSTEM_UI_FLAG_IMMERSIVE

		window.decorView.setOnSystemUiVisibilityChangeListener {
			checkSystemUiVisibility(it)
		}
	}

	private fun checkSystemUiVisibility(visibility: Int) {
		if (visibility == View.VISIBLE)
			Handler().postDelayed(
				{
					window.decorView.systemUiVisibility =
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
								View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
								View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
								View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
								View.SYSTEM_UI_FLAG_IMMERSIVE
				}, 1000
			)
	}

	protected fun getPresentationComponent(): PresentationComponent {
		if (isInjectorUsed) {
			throw RuntimeException("There is no need to use injector more than once")
		}
		isInjectorUsed = true
		return getApplicationComponent().newPresentationComponent(
			PresentationModule(this, this)
		)
	}

	private fun getApplicationComponent(): ApplicationComponent {
		return (application as MyApplication).getApplicationComponent()
	}

}