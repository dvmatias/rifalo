package com.bluespark.raffleit.screens.splash

import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface SplashContract {

	interface View : BaseView {
		// UI feedback
		fun startLoadingAnimation()
		fun stopLoadingAnimation()
		fun showNoInternetDialog()
		// Flow
		fun goToSignInScreen()
		fun goToMainScreen()
	}

	interface Presenter : BasePresenter<View> {
		fun actionCheckInternetConnection()
		fun actionCheckCredentials()
	}

}