package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface UserPhoneVerificationContract {

	interface View : BaseView {
		fun showOtpInlineError(errorMsg: String)
		fun showLoadingDialog(show: Boolean)
		fun onVerifiedPhone(phoneNumber: String)
		fun writeAutoOtpCode(otpCode: String)
	}

	interface Presenter : BasePresenter<View> {
		fun isValidOtpCode(otpCode: String?): Boolean
		fun sendOtpCode(phoneNumber: String)
	}
}
