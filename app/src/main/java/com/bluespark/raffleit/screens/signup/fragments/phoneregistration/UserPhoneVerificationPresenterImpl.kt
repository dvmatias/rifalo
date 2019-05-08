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
			view.showLoadingDialog(Constants.HIDE_LOADING)
			if (otpCode != null) {
				view.setAutoOtpCode(otpCode)
			}
		}

		override fun onVerificationFailed() {
			// TODO
		}

		override fun onCodeSent(verificationId: String?) {
			// TODO
		}

	}

	/**
	 * [SplashCheckNetworkInteractor.Listener] implementation.
	 */
	private val networkInteractorListener = object : SplashCheckNetworkInteractor.Listener {
		override fun onInternetConnected() {
			TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
		}

		override fun onInternetNotConnected() {
			TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
		}

	}
}