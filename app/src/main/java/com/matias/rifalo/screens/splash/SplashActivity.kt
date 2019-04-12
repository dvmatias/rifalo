package com.matias.rifalo.screens.splash

import android.os.Bundle
import android.view.View
import com.matias.rifalo.R
import com.matias.rifalo.common.mvp.BaseActivityImpl

class SplashActivity : BaseActivityImpl() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)

		// Draw below status bar, android:statusBarColor attribute must be transparent.
		window.decorView.systemUiVisibility =
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

	}

}