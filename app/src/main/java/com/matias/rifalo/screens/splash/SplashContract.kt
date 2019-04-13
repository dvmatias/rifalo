package com.matias.rifalo.screens.splash

import com.matias.rifalo.common.mvp.BaseInteractor
import com.matias.rifalo.common.mvp.BasePresenter
import com.matias.rifalo.common.mvp.BaseView

interface SplashContract {

	interface View : BaseView {
		fun viewStartLoadingAnimation()
		fun viewStopLoadingAnimation()
		fun viewShowNoInternetDialog()
		fun flowGoToRegisterScreen()
		fun flowGoToMainScreen()
	}

	interface Presenter<V : SplashContract.View> : BasePresenter<V> {
		fun actionCheckCredentials()
	}

}