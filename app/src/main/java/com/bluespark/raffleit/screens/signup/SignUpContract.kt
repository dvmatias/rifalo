package com.bluespark.raffleit.screens.signup

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

	}

}