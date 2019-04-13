package com.matias.rifalo.screens.splash

import android.os.Bundle
import android.view.View
import com.matias.rifalo.R
import com.matias.rifalo.common.mvp.BaseActivityImpl
import javax.inject.Inject

class SplashActivity : BaseActivityImpl(), SplashContract.View {

	@Inject
	lateinit var presenter: SplashPresenterImpl

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)
		// Inject this view.
		getPresentationComponent().inject(this)
		// Draw below status bar, android:statusBarColor attribute must be transparent.
		window.decorView.systemUiVisibility =
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

	}

	override fun onResume() {
		super.onResume()
	}

	/**
	 * [SplashContract.View] implementation.
	 */

	override fun viewStartLoadingAnimation() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun viewStopLoadingAnimation() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun viewShowNoInternetDialog() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun flowGoToRegisterScreen() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun flowGoToMainScreen() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}