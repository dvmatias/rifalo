package com.bluespark.rifalo.screens.splash

import com.bluespark.rifalo.common.mvp.BasePresenter
import com.bluespark.rifalo.common.mvp.BaseView

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