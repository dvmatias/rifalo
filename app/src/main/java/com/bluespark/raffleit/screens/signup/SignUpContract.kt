package com.bluespark.raffleit.screens.signup

import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface SignUpContract {

	interface View : BaseView {
		// UI feedback
		fun setFlowButtonLabel(label: String)
		fun showLoadingDialog(show: Boolean)
		fun showSelectedCountry()
		fun showAgreementWarningDialog()
		// User actions
		fun onBackButtonClicked()
		fun onFlowButtonClicked()
		// Flow
		fun goToValidatePhoneFragment()
		fun goToChooseCountryScreen()
		fun goToRegisterPhoneFragment()
		//
		fun setSelectedCountry(country: Country)
		fun registerUser()

	}

	interface Presenter : BasePresenter<View> {

		fun fetchCountryCodes()
		fun registerFirebaseUser(signUpUser: SignUpUser)

	}

}