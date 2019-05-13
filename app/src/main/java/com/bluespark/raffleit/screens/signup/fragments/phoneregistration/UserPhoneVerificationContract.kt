package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView
import com.google.firebase.auth.PhoneAuthCredential

interface UserPhoneVerificationContract {

	interface View : BaseView {
		fun showOtpInlineError(errorMsg: String)
		fun showLoadingDialog(show: Boolean)
		fun onVerifiedPhone(phoneNumber: String)
		fun writeAutoOtpCode(otpCode: String)
		fun onPhoneAuthCredentialCreated(phoneAuthCredential: PhoneAuthCredential)
	}

	interface Presenter : BasePresenter<View> {

		fun sendOtpCode(phoneNumber: String)
		fun verifyOtpCode(otpCode: String)
		fun isValidOtpCode(otpCode: String?): Boolean
	}
}
