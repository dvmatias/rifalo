package com.bluespark.raffleit.screens.signup.fragments.phoneregistration

import android.os.Handler
import android.util.Log
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class SendFirebaseOtpInteractor(
) {

	var listener: SendFirebaseOtpInteractor.Listener? = null

	interface Listener {
		fun onVerificationCompleted(otpCode: String?)
		fun onVerificationFailed()
		fun onCodeSent(verificationId: String?)
	}

	fun execute(listener: Listener, phoneNumber: String) {
		this.listener = listener
		Handler().postDelayed({
			PhoneAuthProvider.getInstance().verifyPhoneNumber(
				phoneNumber,
				60,
				TimeUnit.SECONDS,
				TaskExecutors.MAIN_THREAD,
				mCallbacks
			)
		}, 500)
	}

	companion object {
		@Suppress("unused")
		private val TAG = SendFirebaseOtpInteractor::class.java.simpleName
	}

	private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
		override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
			//Getting the code sent by SMS. It can be null.
			val otpCode = phoneAuthCredential.smsCode
			listener?.onVerificationCompleted(otpCode)
		}

		override fun onVerificationFailed(e: FirebaseException) {
			Log.d(SendFirebaseOtpInteractor::class.java.simpleName, "onVerificationFailed()")
			listener?.onVerificationFailed()
		}

		override fun onCodeSent(
			s: String?,
			forceResendingToken: PhoneAuthProvider.ForceResendingToken?
		) {
			super.onCodeSent(s, forceResendingToken)
			listener?.onCodeSent(s)
		}
	}

}