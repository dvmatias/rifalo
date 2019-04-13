package com.bluespark.raffleit.screens.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.screens.singin.SignInActivity
import com.bluespark.raffleit.screens.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseActivityImpl(), SplashContract.View {

	companion object {
		@Suppress("unused")
		private val TAG = SplashActivity::class.java.simpleName
	}

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
		presenter.actionCheckInternetConnection()
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

	override fun flowGoToSignInScreen() {
		val goToSignInScreenIntent = Intent(this, SignInActivity::class.java)
		startActivity(goToSignInScreenIntent)
		finish()
	}

	override fun flowGoToMainScreen() {
		val goToMainScreenIntent = Intent(this, MainActivity::class.java)
		startActivity(goToMainScreenIntent)
		finish()
	}
}