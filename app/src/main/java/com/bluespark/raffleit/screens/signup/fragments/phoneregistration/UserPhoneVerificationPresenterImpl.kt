package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.mvp.BasePresenterImpl
import com.bluespark.raffleit.screens.splash.SplashCheckNetworkInteractor

class UserPhoneVerificationPresenterImpl(
	view: UserPhoneVerificationContract.View,
	private var checkNetworkInteractor: SplashCheckNetworkInteractor,
	private val sendFirebaseOtpInteractor: SendFirebaseOtpInteractor
) :
	BasePresenterImpl<UserPhoneVerificationContract.View>(),
	UserPhoneVerificationContract.Presenter {

	var verificationId: String? = null

	init {
		bind(view)
	}

	/**
	 * [UserPhoneVerificationContract.Presenter] implementation.
	 */

	/**
	 * Send an OTP code to the phone number.
	 *
	 * @param phoneNumber Phone number to send the OTP code verification.
	 */
	override fun sendOtpCode(phoneNumber: String) {
		view?.showLoadingDialog(Constants.SHOW_LOADING)
		sendFirebaseOtpInteractor.execute(sendOtpInteractorListener, phoneNumber)
	}

	/**
	 * [SendFirebaseOtpInteractor.Listener] implementation.
	 */
	private val sendOtpInteractorListener = object : SendFirebaseOtpInteractor.Listener {
		override fun onVerificationCompleted(otpCode: String?) {
			if (otpCode != null) {
				view.writeAutoOtpCode(otpCode)
			}
			view.showLoadingDialog(Constants.HIDE_LOADING)
		}

		override fun onVerificationFailed() {
			// TODO
			view.showLoadingDialog(Constants.HIDE_LOADING)
		}

		override fun onCodeSent(verificationId: String?) {
			// TODO
			view.showLoadingDialog(Constants.HIDE_LOADING)
			this@UserPhoneVerificationPresenterImpl.verificationId = verificationId
		}

	}
}