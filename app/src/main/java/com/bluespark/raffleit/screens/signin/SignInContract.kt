package com.bluespark.raffleit.screens.signin

import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface SignInContract {

	interface View : BaseView {
		// UI user actions
		fun onGetHelpClick()
		fun onLoginClick()
		fun onSignInEmailPassword(email: String, password: String)
		fun onSignInFacebookClick()
		fun onSignInGoogleClick()
		fun onSignUpClick()
		// UI feedback
		fun showLoading(show: Boolean)
		fun showNoInternetDialog(show: Boolean)
		fun setEmailError(errorMsg: String)
		fun setPasswordError(errorMsg: String)
		// Flow
		fun goToMainScreen()
		fun goToSignUpScreen()
	}

	interface Presenter : BasePresenter<View> {
		fun signInGoogle()
		fun signInFacebook()
		fun validateCredentials(email: String?, password: String?)
		fun manageEmailError(errorMsg: String)
		fun managePasswordError(errorMsg: String)
	}

}