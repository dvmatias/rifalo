package com.bluespark.raffleit.screens.signin

import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface SignInContract {

	interface View : BaseView {
		// UI user actions
		fun onGetHelpClick()
		fun onLoginClick()
		fun onSignInFacebookClick()
		fun onSignInGoogleClick()
		fun onSignUpClick()
		// UI feedback
		fun showLoading(show: Boolean)
		fun showNoInternetDialog(show: Boolean)
		// Flow
		fun goToMainScreen()
		fun goToRegisterUserScreen()
	}

	interface Presenter : BasePresenter<View> {
		fun signInGoogle()
		fun signInFacebook()
		fun login()
	}

}