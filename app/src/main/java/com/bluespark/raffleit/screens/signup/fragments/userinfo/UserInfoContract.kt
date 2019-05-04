package com.bluespark.raffleit.screens.signup.fragments.userinfo

import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface UserInfoContract {

	interface View : BaseView {

		// UI feedback
		fun setEmailError(errorMsg: String)
		fun setPasswordError(errorMsg: String)
		fun setPasswordConfirmationError(errorMsg: String)
		fun hideErrors()

		// Flow.
		fun onValidEmailAndPassword()

	}

	interface Presenter : BasePresenter<View> {

		fun validateUser(signUpUser: SignUpUser)
		fun manageEmailError(errorMsg: String)
		fun managePasswordError(errorMsg: String)
		fun managePasswordConfirmationError(errorMsg: String)

	}

}