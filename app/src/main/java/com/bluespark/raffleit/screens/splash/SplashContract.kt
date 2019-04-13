package com.bluespark.raffleit.screens.splash

import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface SplashContract {

	interface View : BaseView {
		fun viewStartLoadingAnimation()
		fun viewStopLoadingAnimation()
		fun viewShowNoInternetDialog()
		fun flowGoToRegisterScreen()
		fun flowGoToMainScreen()
	}

	interface Presenter : BasePresenter<View> {
		fun actionCheckInternetConnection()
		fun actionCheckCredentials()
	}

}