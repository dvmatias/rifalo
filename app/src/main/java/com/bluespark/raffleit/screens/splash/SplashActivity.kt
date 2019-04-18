package com.bluespark.raffleit.screens.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.screens.main.MainActivity
import com.bluespark.raffleit.screens.singin.SignInActivity
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
		super.applyImmersiveFullScreen()
		setContentView(R.layout.activity_splash)
		getPresentationComponent().inject(this)
		// Draw below status bar, android:statusBarColor attribute must be transparent.
		// Hide navigation bar.
		window.decorView.systemUiVisibility =
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
					View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
					View.SYSTEM_UI_FLAG_IMMERSIVE

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