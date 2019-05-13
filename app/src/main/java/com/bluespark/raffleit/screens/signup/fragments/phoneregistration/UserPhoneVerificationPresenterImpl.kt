package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.mvp.BasePresenterImpl
import com.google.firebase.auth.PhoneAuthCredential

class UserPhoneVerificationPresenterImpl(
	view: UserPhoneVerificationContract.View,
	private val sendFirebaseOtpInteractor: SendFirebaseOtpInteractor,
	private val createPhoneAuthCredentialInteractor: CreatePhoneAuthCredentialInteractor
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

	override fun verifyOtpCode(otpCode: String) {
		if (isValidOtpCode(otpCode)) {
			createPhoneAuthCredentialInteractor.execute(
				createPhoneAuthCredentialInteractorListener,
				verificationId!!,
				otpCode
			)
		}
	}

	/**
	 * Validate if the otp code string entered by the user is valid. If not, displays the proper
	 * inline error
	 */
	override fun isValidOtpCode(otpCode: String?): Boolean {
		val isValidOtp = !otpCode.isNullOrEmpty() && otpCode.length >= 6

		if (!isValidOtp)
			view?.showOtpInlineError("Invalid otp code. Try again.")

		return isValidOtp
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
			this@UserPhoneVerificationPresenterImpl.verificationId = verificationId
			view.showLoadingDialog(Constants.HIDE_LOADING)
		}

	}

	/**
	 * [CreatePhoneAuthCredentialInteractor.Listener] implementation
	 */
	private val createPhoneAuthCredentialInteractorListener =
		object : CreatePhoneAuthCredentialInteractor.Listener {
			override fun onPhoneAuthCredentialCreated(phoneAuthCredential: PhoneAuthCredential) {
				view.onPhoneAuthCredentialCreated(phoneAuthCredential)
			}
		}

}