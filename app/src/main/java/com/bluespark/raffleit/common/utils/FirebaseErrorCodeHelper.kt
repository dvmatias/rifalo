package com.bluespark.raffleit.common.utils

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class FirebaseErrorCodeHelper {

	companion object {

		private val TAG = FirebaseErrorCodeHelper::class.java.simpleName

		fun <T>getErrorCode(exception: T): String {
			if (exception is FirebaseAuthUserCollisionException)
				return getFirebaseAuthUserCollisionExceptionErrorCode(exception.errorCode)
			if (exception is FirebaseAuthInvalidUserException)
				return getFirebaseAuthInvalidUserExceptionErrorCode(exception.errorCode)
			if (exception is FirebaseAuthInvalidCredentialsException)
				return getFirebaseAuthInvalidCredentialsException(exception.errorCode)

			return "exception:$exception not contemplated."
		}

		private fun getFirebaseAuthUserCollisionExceptionErrorCode(errorCode: String): String {
			return when (errorCode) {
				"ERROR_INVALID_EMAIL" -> "auth/invalid-email"
				"ERROR_USER_DISABLED" -> "auth/user-disabled"
				"ERROR_USER_NOT_FOUND" -> "auth/user-not-found"
				"ERROR_WRONG_PASSWORD" -> "auth/wrong-password"
				else -> "FirebaseAuthUserCollisionException:$errorCode not contemplated."
			}
		}

		private fun getFirebaseAuthInvalidUserExceptionErrorCode(errorCode: String): String {
			return when (errorCode) {
				"ERROR_USER_DISABLED" -> "auth/user-disabled"
				"ERROR_USER_NOT_FOUND" -> "auth/user-not-found"
				"ERROR_USER_TOKEN_EXPIRED" -> "auth/user-token-expired"
				"ERROR_INVALID_USER_TOKEN" -> "auth/invalid-user-token"
				else -> "FirebaseAuthInvalidUserException:$errorCode not contemplated."
			}
		}

		private fun getFirebaseAuthInvalidCredentialsException(errorCode: String): String {
			return when (errorCode) {
				"ERROR_INVALID_VERIFICATION_CODE" -> "auth/invalid-verification-code"
				"ERROR_INVALID_VERIFICATION_ID" -> "auth/invalid-verification-id"
				else -> "FirebaseAuthInvalidCredentialsException:$errorCode not contemplated."
			}
		}
	}
}