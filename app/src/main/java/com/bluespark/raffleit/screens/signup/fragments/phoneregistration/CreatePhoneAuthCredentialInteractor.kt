package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import android.os.Handler
import com.bluespark.raffleit.common.utils.managers.FirebaseSignInPhoneManager
import com.google.firebase.auth.PhoneAuthCredential

class CreatePhoneAuthCredentialInteractor(private var firebaseSignInPhoneManager: FirebaseSignInPhoneManager) {

	var listener: CreatePhoneAuthCredentialInteractor.Listener? = null

	interface Listener {
		fun onPhoneAuthCredentialCreated(phoneAuthCredential: PhoneAuthCredential)
	}

	fun execute(listener: Listener, verificationId: String, otpCode: String) {
		this.listener = listener
		Handler().postDelayed({
			firebaseSignInPhoneManager.getPhoneAuthCredential(
				phoneAuthCreationListener,
				verificationId,
				otpCode
			)
		}, 500)
	}

	companion object {
		@Suppress("unused")
		private val TAG = CreatePhoneAuthCredentialInteractor::class.java.simpleName
	}

	/**
	 * [FirebaseSignInPhoneManager.Listener.PhoneCredential] implementation.
	 *
	 * Interface that hears the send of the OTP code to the phone number.
	 */
	private val phoneAuthCreationListener =
		object : FirebaseSignInPhoneManager.Listener.PhoneCredential {
			override fun onPhoneAuthCredentialCreated(phoneAuthCredential: PhoneAuthCredential) {
				listener?.onPhoneAuthCredentialCreated(phoneAuthCredential)
			}
		}

}