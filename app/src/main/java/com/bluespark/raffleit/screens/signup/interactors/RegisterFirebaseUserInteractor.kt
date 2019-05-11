package com.bluespark.raffleit.screens.signup.interactors

import android.app.Activity
import android.os.Handler
import com.bluespark.raffleit.common.utils.managers.FirebaseEmailPasswordManager
import com.google.firebase.auth.FirebaseUser


class RegisterFirebaseUserInteractor(
	private var activity: Activity,
	private var firebaseEmailPasswordManager: FirebaseEmailPasswordManager
) {

	var listener: Listener? = null

	interface Listener {
		fun onSuccess(firebaseUser: FirebaseUser)
		fun onFail(errorCode: String)
	}

	fun execute(
		listener: Listener,
		email: String,
		password: String
	) {
		this.listener = listener
		Handler().postDelayed({
			firebaseEmailPasswordManager.createUserWithEmailAndPassword(
				createUserListener,
				activity,
				email,
				password
			)
		}, 500)
	}

	/**
	 * [FirebaseEmailPasswordManager.Listener.CreateUserListener] implementation.
	 */
	private val createUserListener =
		object : FirebaseEmailPasswordManager.Listener.CreateUserListener {
			override fun onUserCreationSuccess(firebaseUser: FirebaseUser) {
				listener?.onSuccess(firebaseUser)
			}

			override fun onUserCreationFail(errorCode: String) {
				listener?.onFail(errorCode)
			}
		}

	companion object {
		@Suppress("unused")
		private val TAG = RegisterFirebaseUserInteractor::class.java.simpleName
	}

}