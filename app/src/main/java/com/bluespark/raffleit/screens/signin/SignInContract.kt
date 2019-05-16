package com.bluespark.raffleit.screens.signin

import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface SignInContract {

	interface View : BaseView {
		// UI user actions
		fun onGetHelpClick()
		fun onLoginClick()
		fun onSignInEmailPasswordSuccess()
		fun onSignInEmailPasswordError(errorCode: String)
		fun onSignInFacebookClick()
		fun onSignInGoogleClick()
		fun onSignUpClick()
		// UI feedback
		fun showNoInternetDialog(show: Boolean)
		fun setEmailError(errorMsg: String)
		fun setPasswordError(errorMsg: String)
		fun showUserNotFoundError(errorCode: String)
		// Flow
		fun goToMainScreen()
		fun goToSignUpScreen()
		fun showLoadingDialog(show: Boolean) {

		}
	}

	interface Presenter : BasePresenter<View> {
		fun signInGoogle()
		fun signInFacebook()
		fun signInEmailAndPassword(email: String, password: String)
		fun validateCredentials(email: String?, password: String?)
		fun manageEmailError(errorMsg: String)
		fun managePasswordError(errorMsg: String)
	}

}