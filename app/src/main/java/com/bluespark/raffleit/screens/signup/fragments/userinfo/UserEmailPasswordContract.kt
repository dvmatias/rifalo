package com.bluespark.raffleit.screens.signup.fragments.userinfo

import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface UserEmailPasswordContract {

	interface View : BaseView {

		// UI feedback
		fun setEmailError(errorMsg: String)
		fun showLoading(show: Boolean)
		fun setPasswordError(errorMsg: String)
		fun setPasswordConfirmationError(errorMsg: String)
		fun showUserCreationErrorDialog(errorCode: String)
		fun goToValidatePhoneFragment()
		fun hideErrors()

		// Flow.
		fun onValidEmailAndPassword(email: String, password: String)

	}

	interface Presenter : BasePresenter<View> {

		fun validateEmailAndPassword(
			email: String?,
			password: String?,
			passwordConfirmation: String?
		)
		fun createUserWithEmailAndPassword(email: String, password: String)
		fun manageEmailError(errorMsg: String)
		fun managePasswordError(errorMsg: String)
		fun managePasswordConfirmationError(errorMsg: String)

	}

}