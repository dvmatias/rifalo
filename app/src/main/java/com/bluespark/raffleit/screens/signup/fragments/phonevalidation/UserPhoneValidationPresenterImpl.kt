package com.bluespark.raffleit.screens.signup.fragments.phonevalidation

import android.os.Handler
import android.util.Log
import com.bluespark.raffleit.common.mvp.BasePresenterImpl
import com.bluespark.raffleit.common.utils.managers.PhoneManager
import com.bluespark.raffleit.screens.splash.SplashCheckNetworkInteractor

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
	 * It receives [countryNameCode] and [phoneNumber] and validate for they validity. If booth
	 * are valid values, then it try to register the user's phone number. For non valid fields
	 * it call [view] methods to show corresponding inline error.
	 */
	override fun validatePhoneNumber(countryCode: String, phoneNumber: String) {
		this.countryCode = countryCode
		this.phoneNumber = phoneNumber

		if (!isValidCountry()) {// INVALID country.
			view?.showWrongCountryError("Error on country")
		} else if (!isValidPhoneNumber()) // VALID country - INVALID number
			view?.showWrongNumberError("Error on number")
		else // VALID country - VALID number
			registerPhoneNumber()
	}

	/**
	 * It uses [phoneManager] to validate the [phoneNumber] along with the [countryNameCode].
	 * It shall not be called if the [countryNameCode] isn't valid.
	 */
	override fun isValidPhoneNumber(): Boolean {
		return phoneManager.isValidNumber(phoneNumber, countryCode)
	}

	/**
	 * It uses [phoneManager] to validate the [countryNameCode].
	 */
	override fun isValidCountry(): Boolean {
		return phoneManager.isValidCountryName(countryCode)
	}

	/**
	 * It is meant to register user's phone number. It must came back with a PIN number in
	 * order to verify in further registrations steps.
	 */
	override fun registerPhoneNumber() {
		view?.showLoadingDialog(true)
		// TODO replace for phone registration logic.
		Handler().postDelayed({
			view?.showLoadingDialog(false)
			view?.onValidPhone()
		}, 3000)
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
