package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import android.os.Handler
import com.bluespark.raffleit.common.utils.managers.FirebaseSignInPhoneManager

class SendFirebaseOtpInteractor(private var firebaseSignInPhoneManager: FirebaseSignInPhoneManager) {

	var listener: SendFirebaseOtpInteractor.Listener? = null

	interface Listener {
		fun onVerificationCompleted(otpCode: String?)
		fun onVerificationFailed()
		fun onCodeSent(verificationId: String?)
	}

	fun execute(listener: Listener, phoneNumber: String) {
		this.listener = listener
		Handler().postDelayed({
			firebaseSignInPhoneManager.sendOtp(phoneNumber, phoneVerificationListener)
		}, 500)
	}

	companion object {
		@Suppress("unused")
		private val TAG = SendFirebaseOtpInteractor::class.java.simpleName
	}

	/**
	 * [FirebaseSignInPhoneManager.Listener.Verification] implementation.
	 *
	 * Interface that hears the send of the OTP code to the phone number.
	 */
	private val phoneVerificationListener =
		object : FirebaseSignInPhoneManager.Listener.Verification {
			override fun onVerificationCompleted(otpCode: String?) {
				listener!!.onVerificationCompleted(otpCode)
			}

			override fun onVerificationFailed() {
				listener!!.onVerificationFailed()
			}

			override fun onCodeSent(verificationId: String?) {
				listener!!.onCodeSent(verificationId)
			}
		}

}