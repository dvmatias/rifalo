package com.bluespark.raffleit.common.utils.managers

import android.support.annotation.NonNull
import android.util.Log
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

/**
 * TODO desc.
 */
class FirebaseSignInPhoneManager(private var firebaseAuth: FirebaseAuth) {

	private var verificationListener: Listener.Verification? = null

	companion object {
		private val TAG = FirebaseSignInPhoneManager::class.java.simpleName
	}

	interface Listener {
		interface Verification {
			fun onVerificationCompleted(otpCode: String?)
			fun onVerificationFailed()
			fun onCodeSent(verificationId: String?)
		}

		interface SignIn {
			fun onPhoneSignInSuccess(phoneAuthCredential: PhoneAuthCredential)
			fun onPhoneSignInFail(errorMsg: String)
		}
	}

	fun sendOtp(@NonNull phoneNumber: String, @NonNull verificationListener: Listener.Verification) {
		this.verificationListener = verificationListener
		PhoneAuthProvider.getInstance().verifyPhoneNumber(
			phoneNumber,
			15,
			TimeUnit.SECONDS,
			TaskExecutors.MAIN_THREAD,
			callback
		)
	}

	private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
		override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
			Log.d(TAG, "onVerificationCompleted()")
			//Getting the code sent by SMS. It can be null.
			val otpCode = phoneAuthCredential.smsCode
			verificationListener!!.onVerificationCompleted(otpCode)
		}

		override fun onVerificationFailed(e: FirebaseException) {
			Log.d(TAG, "onVerificationFailed()")
			verificationListener!!.onVerificationFailed()
		}

		override fun onCodeSent(
			verificationId: String?,
			forceResendingToken: PhoneAuthProvider.ForceResendingToken?
		) {
			super.onCodeSent(verificationId, forceResendingToken)
			Log.d(TAG, "onCodeSent()")
			verificationListener!!.onCodeSent(verificationId)
		}
	}

	fun firebaseSignInWithPhone(
		activity: BaseActivityImpl,
		verificationId: String,
		otpCode: String
	) {
		if (activity !is FirebaseSignInPhoneManager.Listener.SignIn)
			throw RuntimeException("Calling Activity must implement FirebaseSignInPhoneManager.Listener.SignIn.")
		val listener: FirebaseSignInPhoneManager.Listener.SignIn = activity
		//creating the credential
		val credential = PhoneAuthProvider.getCredential(verificationId, otpCode)
		listener.onPhoneSignInSuccess(credential)
//		firebaseAuth.signInWithCredential(credential)
//			.addOnCompleteListener(activity) { task ->
//				if (task.isSuccessful) {
//					//verification successful.
//					// TODO manage login results.
//					listener.onPhoneSignInSuccess(credential)
//				} else {
//					//verification unsuccessful
//					// TODO manage login results... display an error message
//					var errorMsg = "Invalid code entered..."
//					if (task.exception is FirebaseAuthInvalidCredentialsException) {
//
//					} else {
//
//					}
//
//					listener.onPhoneSignInFail(errorMsg)
//				}
//			}

	}

}