package com.bluespark.raffleit.screens.splash

import android.content.Intent
import android.os.Bundle
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.screens.main.MainActivity
import com.bluespark.raffleit.screens.signin.SignInActivity
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

	}

	override fun onResume() {
		super.onResume()
		presenter.actionCheckInternetConnection()
	}

	/**
	 * [SplashContract.View] implementation.
	 */

	override fun startLoadingAnimation() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun stopLoadingAnimation() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun showNoInternetDialog() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun goToSignInScreen() {
		val goToSignInScreenIntent = Intent(this, SignInActivity::class.java)
		startActivity(goToSignInScreenIntent)
		finish()
	}

	override fun goToMainScreen() {
		val goToMainScreenIntent = Intent(this, MainActivity::class.java)
		startActivity(goToMainScreenIntent)
		finish()
	}
}