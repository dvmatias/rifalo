package com.bluespark.raffleit.screens.signup

import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface SignUpContract {

	interface View : BaseView {

		//UI feedback
		fun setFlowButtonLabel(label: String)
		fun showLoading(show: Boolean)

		//User actions
		fun onBackButtonClicked()

		fun onFlowButtonClicked()

		//Flow
		fun goToValidatePhoneFragment()

		fun goToSignUpUserInfoFragment()

	}

	interface Presenter : BasePresenter<View> {

		fun fetchCountryCodes()
		fun setSignUpUser(signUpUser: SignUpUser)

	}

}