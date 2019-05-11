package com.bluespark.raffleit.screens.signup.fragments.userinfo

import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface UserEmailPasswordContract {

	interface View : BaseView {

		/* UI feedback */

		fun hideInlineErrors()
		fun setEmailError(errorMsg: String)
		fun setPasswordConfirmationError(errorMsg: String)
		fun setPasswordError(errorMsg: String)
		fun showLoading(show: Boolean)
		fun showUserCreationErrorDialog(errorCode: String)

		/* Actions */

		fun onFirebaseUserCreated()

	}

	interface Presenter : BasePresenter<View> {

		fun validateEmailAndPassword(
			email: String?,
			password: String?,
			passwordConfirmation: String?
		)
		fun createUserWithEmailAndPassword(email: String, password: String)

	}

}