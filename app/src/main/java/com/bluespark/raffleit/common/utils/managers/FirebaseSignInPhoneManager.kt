package com.bluespark.raffleit.common.utils.managers

import android.content.Intent
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class FirebaseSignInPhoneManager(private var firebaseAuth: FirebaseAuth) {

	private var verificationListener: Listener.Verification? = null
	private var phoneAuthCredential: PhoneAuthCredential? = null

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
			fun onPhoneSignInSuccess()
			fun onPhoneSignInFail()
		}
	}

	fun sendOtp(@NonNull phoneNumber: String, @NonNull verificationListener: Listener.Verification) {
		this.verificationListener = verificationListener
		PhoneAuthProvider.getInstance().verifyPhoneNumber(
			phoneNumber,
			60,
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
		//creating the credential
		val credential = PhoneAuthProvider.getCredential(verificationId, otpCode)

		firebaseAuth.signInWithCredential(credential)
			.addOnCompleteListener(activity
			) { task ->
				if (task.isSuccessful) {
					//verification successful we will start the profile activity
		//						val intent = Intent(ta, ProfileActivity::class.java)
		//						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
		//						startActivity(intent)
					// TODO log out phone.
					// TODO manage login results.

				} else {

					// TODO manage login results.
					//verification unsuccessful.. display an error message

					var message = "Somthing is wrong, we will fix it soon..."

					if (task.exception is FirebaseAuthInvalidCredentialsException) {
						message = "Invalid code entered..."
					}


				}
			}

	}

}