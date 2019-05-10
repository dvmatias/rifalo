package com.bluespark.raffleit.screens.signup.interactors

import android.app.Activity
import android.os.Handler
import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.utils.managers.FirebaseEmailPasswordManager
import com.google.firebase.auth.FirebaseUser


class RegisterFirebaseUserInteractor(
	private var activity: Activity,
	private var firebaseEmailPasswordManager: FirebaseEmailPasswordManager
) {

	var listener: Listener? = null

	interface Listener {
		fun onSuccess(firebaseUser: FirebaseUser)
		fun onFail()
	}

	fun execute(
		listener: Listener,
		signUpUser: SignUpUser
	) {
		this.listener = listener
		Handler().postDelayed({
			firebaseEmailPasswordManager.createUserWithEmailAndPassword(
				createUserListener,
				activity,
				signUpUser.email,
				signUpUser.password
			)

			//			firebaseAuth.createUserWithEmailAndPassword(signUpUser.email, signUpUser.password)
//				.addOnCompleteListener(
//					activity
//				) { task ->
//					// If sign in fails, display a message to the user. If sign in succeeds
//					// the auth state listener will be notified and logic to handle the
//					// signed in user can be handled in the listener.
//					if (task.isSuccessful) {
//						firebaseAuth.currentUser?.updatePhoneNumber(phoneAuthCredential)!!
//							.addOnCompleteListener(
//								activity
//							) { _task ->
//								if (_task.isSuccessful) {
//									Log.d(TAG, "signInWithCredential:success")
//
//
//								} else {
//
//									(activity as SignUpActivity).laconchadesumadre()
//									Log.d(TAG, "signInWithCredential:fail")
//									if (_task.exception is FirebaseAuthInvalidCredentialsException) {
//										//mVerificationField.setError("Invalid code.");
//
//									} else {
//
//									}
//								}
//							}
//						listener.onUserCreationSuccess()
//					} else {
//						listener.onUserCreationFail()
//					}
//				}
		}, 500)


	}

	/**
	 * [FirebaseEmailPasswordManager.Listener.CreateUserListener] implementation.
	 */
	private val createUserListener =  object: FirebaseEmailPasswordManager.Listener.CreateUserListener {
		override fun onUserCreationSuccess(firebaseUser: FirebaseUser) {
			listener?.onSuccess(firebaseUser)
		}

		override fun onUserCreationFail(errorCode: String) {
			listener?.onFail()
		}
	}

	companion object {
		@Suppress("unused")
		private val TAG = RegisterFirebaseUserInteractor::class.java.simpleName
	}

}