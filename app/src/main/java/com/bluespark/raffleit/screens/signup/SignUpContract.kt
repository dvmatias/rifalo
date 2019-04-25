package com.bluespark.raffleit.screens.signup

import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface SignUpContract {

	interface View : BaseView {

		/*
		 User actions
		 */

		fun onBackButtonClicked()
		fun onFlowButtonClicked()

		/*
		 UI feedback
		 */

		fun setFlowButtonLabel(label: String)
		fun setEmailError(errorMsg: String)
		fun setPasswordError(errorMsg: String)
		fun setPasswordConfirmationError(errorMsg: String)

		/*
		 Flow
		 */

		fun goToValidatePhoneFragment()
		fun goToSignUpUserInfoFragment()
	}

	interface Presenter : BasePresenter<View> {

		fun isValidUser(signUpUser: SignUpUser)
		fun isValidEmail(email: String?): Pair<Boolean, String>
		fun isValidPassword(password: String?): Pair<Boolean, String>
		fun isValidPasswordConfirmation(passwordConfirmation: String?): Pair<Boolean, String>

	}

}