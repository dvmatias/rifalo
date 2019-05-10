package com.bluespark.raffleit.common.utils.managers

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class FirebaseEmailPasswordManager(private var firebaseAuth: FirebaseAuth) {

	companion object {
		@SuppressWarnings("unused")
		private val TAG = FirebaseEmailPasswordManager::class.java.simpleName
	}

	interface Listener {
		interface CreateUserListener {
			fun onSuccess()
			fun onFail(errorCode: String)
		}

		interface SignInListener {
			fun onSuccess()
			fun onFail(errorCode: String)
		}
	}

	/**
	 * Creates a new user account associated with the specified email address and password.
	 * On successful creation of the user account, this user will also be signed in to your
	 * application.
	 * User account creation can fail if the account already exists or the password is invalid.
	 *
	 * Note: The email address acts as a unique identifier for the user and enables an email-based
	 * password reset. This function will create a new user account and set the initial user
	 * password.
	 *
	 * Error Codes:
	 * auth/email-already-in-use: Thrown if there already exists an account with the given email address.
	 * auth/invalid-email: Thrown if the email address is not valid.
	 * auth/operation-not-allowed: Thrown if email/password accounts are not enabled. Enable email/password accounts in the Firebase Console, under the Auth tab.
	 * auth/weak-password: Thrown if the password is not strong enough.
	 *
	 * @param email [String] The user's email address.
	 * @param password [String] The user's chosen password.
	 *
	 * @return Promise<UserCredential>
	 */
	@Suppress("unused")
	fun createUserWithEmailAndPassword(
		listener: Listener.CreateUserListener?,
		activity: Activity,
		email: String,
		password: String
	) {
		firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
			activity
		) { task ->
			if (task.isSuccessful) {
				Log.d(TAG, "createUserWithEmailAndPassword:success")
				listener?.onSuccess()
			} else {
				val errorCode =
					when ((task.exception as FirebaseAuthUserCollisionException).errorCode) {
						"ERROR_EMAIL_ALREADY_IN_USE" -> "auth/email-already-in-use"
						"ERROR_INVALID_EMAIL" -> "auth/invalid-email"
						"ERROR_OPERATION_NOT_ALLOWED" -> "auth/operation-not-allowed"
						"ERROR_WEAK_PASSWORD" -> "auth/weak-password"
						else -> ""
					}
				Log.d(TAG, "createUserWithEmailAndPassword:fail - errorCode = $errorCode")
				listener?.onFail(errorCode)
			}
		}
	}

	/**
	 * Gets the list of possible sign in methods for the given email address. This is useful to
	 * differentiate methods of sign-in for the same provider, eg. EmailAuthProvider which has 2
	 * methods of sign-in, email/password and email/link.
	 *
	 * Error Codes:
	 * auth/invalid-email: Thrown if the email address is not valid.
	 *
	 * @param email [String] email address to evaluate sign in methods.
	 *
	 * @return Promise<Array<string>>
	 */
	@Suppress("unused")
	fun fetchSignInMethodsForEmail(email: String) {

	}

	/**
	 * Asynchronously signs in using an email and password.
	 * Fails with an error if the email address and password do not match.
	 *
	 * Note: The user's password is NOT the password used to access the user's email account.
	 * The email address serves as a unique identifier for the user, and the password is used to
	 * access the user's account in your Firebase project.
	 *
	 * Error Codes:
	 * auth/invalid-email: Thrown if the email address is not valid.
	 * auth/user-disabled: Thrown if the user corresponding to the given email has been disabled.
	 * auth/user-not-found: Thrown if there is no user corresponding to the given email.
	 * auth/wrong-password: Thrown if the password is invalid for the given email, or the account
	 * corresponding to the email does not have a password set.
	 *
	 * @param email [String] The user's email address.
	 * @param password [String] The user's chosen password.
	 *
	 * @return Promise<UserCredential>
	 */
	@Suppress("unused")
	fun signInWithEmailAndPassword(
		listener: Listener.SignInListener?,
		activity: Activity,
		email: String,
		password: String
	) {
		firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
			activity
		) { task ->
			if (task.isSuccessful) {
				Log.d(TAG, "signInWithEmailAndPassword:success")
				listener?.onSuccess()
			} else {
				val errorCode =
					when ((task.exception as FirebaseAuthUserCollisionException).errorCode) {
						"ERROR_INVALID_EMAIL" -> "auth/invalid-email"
						"ERROR_USER_DISABLED" -> "auth/user-disabled"
						"ERROR_USER_NOT_FOUND" -> "auth/user-not-found"
						"ERROR_WRONG_PASSWORD" -> "auth/wrong-password"
						else -> ""
					}
				Log.d(TAG, "signInWithEmailAndPassword:fail - errorCode = $errorCode")
				listener?.onFail(errorCode)
			}
		}
//
//
//
//
//		firebaseAuth.signInWithEmailAndPassword(email, password)
//			.addOnCompleteListener { task ->
//				if (task.isSuccessful) {
//					Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_LONG)
//						.show()
//					showLoadingDialog(false)
//
//					goToMainScreen()
//				} else {
//					Toast.makeText(
//						applicationContext,
//						"Login failed! Please try again later",
//						Toast.LENGTH_LONG
//					).show()
//					showLoadingDialog(false)
//				}
//			}
	}

}