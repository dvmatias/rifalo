package com.bluespark.raffleit.screens.signup.fragments.phonevalidation

import android.util.Log
import com.bluespark.raffleit.common.mvp.BasePresenterImpl
import com.bluespark.raffleit.common.utils.managers.PhoneManager
import com.bluespark.raffleit.screens.splash.SplashCheckNetworkInteractor

/**
 * TODO desc.
 */
class UserPhoneValidationPresenterImpl(
	view: UserPhoneValidationContract.View,
	private val phoneManager: PhoneManager
) :
	BasePresenterImpl<UserPhoneValidationContract.View>(), UserPhoneValidationContract.Presenter {

	private var countryCode: String = "XX"
	private var phoneNumber: String = ""

	init {
		bind(view)
	}

	/**
	 * [UserPhoneValidationContract.Presenter] implementation.
	 */

	/**
	 * It receives [countryCode] and [phoneNumber] and validate for they validity. If booth
	 * are valid values, then it try to register the user's phone number. For non valid fields
	 * it call [view] methods to show corresponding inline error.
	 */
	override fun validatePhoneNumber(
		countryCode: String,
		phoneNumber: String,
		dialCode: String
	) {
		this.countryCode = countryCode
		this.phoneNumber = phoneNumber
		val validCountry = phoneManager.isValidCountryName(countryCode)
		val validNumber = phoneManager.isValidNumber(phoneNumber, countryCode)

		if (!validCountry || !validNumber) {
			view?.showInlinePhoneError(validCountry, validNumber)
		} else {
			view?.hideInlinePhoneError()
			view?.onValidPhone(dialCode + phoneNumber)
		}
	}

}