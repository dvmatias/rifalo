package com.bluespark.raffleit.common.utils.managers

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.*

/**
 * TODO desc.
 */
class FirebaseUserManager(private var firebaseAuth: FirebaseAuth) {

	companion object {
		private val TAG = FirebaseUserManager::class.java.simpleName
	}

	interface Listener {
		interface UpdatePhoneNumber {
			fun onPhoneUpdateSuccess()
			fun onPhoneUpdateFail(errorCode: String)
		}
		interface SendEmailVerification {
			fun onEmailSendSuccess()
			fun onEmailSendFail()
		}
	}

	/**
	 *
	 */
	fun sendEmailVerification(
		listener: Listener.SendEmailVerification?,
		activity: Activity,
		actionCodeSettings: ActionCodeSettings?) {
		firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener(activity) { task ->
			if (task.isSuccessful) {
				listener?.onEmailSendSuccess()
			} else {
				listener?.onEmailSendFail()
			}
		}
	}


	/**
	 * Updates the user's phone number.
	 *
	 * Error Codes:
	 * auth/invalid-verification-code: Thrown if the verification code of the credential is not valid.
	 * auth/invalid-verification-id: Thrown if the verification ID of the credential is not valid.
	 *
	 * @param listener [Listener.UpdatePhoneNumber]
	 * @param activity [Activity]
	 * @param firebaseUser [FirebaseUser]
	 * @param phoneCredential [AuthCredential]
	 */
	fun updatePhoneNumber(
		listener: Listener.UpdatePhoneNumber?,
		activity: Activity,
		firebaseUser: FirebaseUser,
		phoneCredential: PhoneAuthCredential
	) {
		firebaseUser.updatePhoneNumber(phoneCredential).addOnCompleteListener(activity) { task ->
			if (task.isSuccessful) {
				Log.d(TAG, "updatePhoneNumber:success")
				listener?.onPhoneUpdateSuccess()
			} else {
				val errorCode =
					when ((task.exception as FirebaseAuthUserCollisionException).errorCode) {
						"ERROR_INVALID_VERIFICATION_CODE" -> "auth/invalid-verification-code"
						"ERROR_INVALID_VERIFICATION_ID" -> "auth/invalid-verification-id"
						else -> ""
					}
				Log.d(TAG, "updatePhoneNumber:fail - errorCode = $errorCode")
				listener?.onPhoneUpdateFail(errorCode)
			}
		}
	}

}