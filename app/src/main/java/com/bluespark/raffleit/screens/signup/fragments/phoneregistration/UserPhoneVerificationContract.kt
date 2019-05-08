package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.mvp.BasePresenter
import com.bluespark.raffleit.common.mvp.BaseView

interface UserPhoneVerificationContract {

	interface View : BaseView {
		fun showLoadingDialog(show: Boolean)
		fun onVerifiedPhone(phoneNumber: String)
		fun writeAutoOtpCode(otpCode: String)
	}

	interface Presenter : BasePresenter<View> {
		fun sendOtpCode(phoneNumber: String)
	}
}
