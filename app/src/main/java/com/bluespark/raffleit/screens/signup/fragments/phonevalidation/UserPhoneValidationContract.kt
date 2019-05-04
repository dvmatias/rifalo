package com.bluespark.raffleit.screens.signup.fragments.phonevalidation

import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface UserPhoneValidationContract {

	interface View : BaseView {
		// UI Feedback
		fun enableTermsAndConditions(isEnabled: Boolean)
		fun showSelectedCountry(country: Country)
		fun showLoadingDialog(show: Boolean)
		fun showNoConnectionErrorDialog()
		fun showWrongCountryError(errorMsg: String)
		fun showWrongNumberError(errorMsg: String)
		// Flow.
		fun onValidPhone()
	}

	interface Presenter : BasePresenter<View> {

		fun checkInternetConnectionStatus()
		fun validatePhoneNumber(countryCode: String, phoneNumber: String)
		fun isValidPhoneNumber(): Boolean
		fun isValidCountry(): Boolean
		fun registerPhoneNumber()

	}
}
