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
	private var checkNetworkInteractor: SplashCheckNetworkInteractor,
	private val phoneManager: PhoneManager
) :
	BasePresenterImpl<UserPhoneValidationContract.View>(), UserPhoneValidationContract.Presenter,
	SplashCheckNetworkInteractor.Listener {

	private var countryCode: String = "XX"
	private var phoneNumber: String = ""

	init {
		bind(view)
	}

	/**
	 * [UserPhoneValidationContract.Presenter] implementation.
	 */

	override fun checkInternetConnectionStatus() {
		checkNetworkInteractor.execute(this)
	}

	/**
	 * It receives [countryCode] and [phoneNumber] and validate for they validity. If booth
	 * are valid values, then it try to register the user's phone number. For non valid fields
	 * it call [view] methods to show corresponding inline error.
	 */
	override fun validatePhoneNumber(countryCode: String, phoneNumber: String) {
		this.countryCode = countryCode
		this.phoneNumber = phoneNumber
		val validCountry = isValidCountry()
		val validNumber = isValidPhoneNumber()

		if (!validCountry || !validNumber) {
			view?.showInlinePhoneError(validCountry, validNumber)
		} else {
			view?.hideInlinePhoneError()
			view?.onValidPhone()
		}
	}

	/**
	 * It uses [phoneManager] to validate the [phoneNumber] along with the [countryCode].
	 * It shall not be called if the [countryCode] isn't valid.
	 */
	override fun isValidPhoneNumber(): Boolean {
		return phoneManager.isValidNumber(phoneNumber, countryCode)
	}

	/**
	 * It uses [phoneManager] to validate the [countryCode].
	 */
	override fun isValidCountry(): Boolean {
		return phoneManager.isValidCountryName(countryCode)
	}

	/**
	 * [SplashCheckNetworkInteractor.Listener] implementation.
	 */

	override fun onInternetConnected() {
		Log.d(UserPhoneValidationPresenterImpl::class.java.simpleName, "onInternetConnected()")
	}

	override fun onInternetNotConnected() {
		Log.d(UserPhoneValidationPresenterImpl::class.java.simpleName, "onInternetNotConnected()")
	}
}
