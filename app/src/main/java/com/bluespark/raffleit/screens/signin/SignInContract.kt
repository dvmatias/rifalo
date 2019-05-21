package com.bluespark.raffleit.screens.signin

import com.bluespark.raffleit.common.model.objects.UserFirebase
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
		fun showNoInternetDialog(show: Boolean)
		fun setEmailError(errorMsg: String)
		fun setPasswordError(errorMsg: String)
		fun showUserNotFoundErrorDialog()
		fun showWrongPasswordErrorDialog()
		// Flow
		fun goToMainScreen()
		fun goToSignUpScreen()
		fun showLoadingDialog(show: Boolean)
		// Results
		fun onSignInEmailPasswordSuccess()
		fun onSignInGoogleSuccess()
	}

	interface Presenter : BasePresenter<View> {
		fun signInGoogle()
		fun signInFacebook()
		fun signInEmailAndPassword(email: String, password: String)
		fun validateCredentials(email: String?, password: String?)
		fun manageEmailError(errorMsg: String)
		fun managePasswordError(errorMsg: String)
		fun addUserToDatabase(userId: String)
	}

}