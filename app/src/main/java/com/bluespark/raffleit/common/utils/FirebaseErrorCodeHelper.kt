package com.bluespark.raffleit.common.utils

import com.google.firebase.auth.FirebaseAuthException

class FirebaseErrorCodeHelper {


	companion object {
		const val ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND"
		const val ERROR_WRONG_PASSWORD = "ERROR_WRONG_PASSWORD"

		private val TAG = FirebaseErrorCodeHelper::class.java.simpleName

		fun getFirebaseError(exception: Exception?): Pair<String, String?> {
			return Pair(getFirebaseErrorCode(exception), getFirebaseErrorMessage(exception))
		}

		fun getFirebaseErrorCode(exception: Exception?): String {
			return (exception as FirebaseAuthException).errorCode
		}

		fun getFirebaseErrorMessage(exception: Exception?): String? {
			if (exception?.message.isNullOrEmpty()) return ""
			return (exception as FirebaseAuthException).message
		}
	}
}