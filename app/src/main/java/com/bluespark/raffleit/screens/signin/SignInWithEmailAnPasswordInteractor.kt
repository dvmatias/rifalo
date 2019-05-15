package com.bluespark.raffleit.screens.signin

import android.app.Activity
import android.support.annotation.UiThread
import com.bluespark.raffleit.common.utils.managers.FirebaseEmailPasswordManager

class SignInWithEmailAnPasswordInteractor(
	private var activity: Activity,
	private var firebaseEmailPasswordManager: FirebaseEmailPasswordManager
) {

	private lateinit var listener: Listener

	interface Listener {
		fun onSuccess()
		fun onFail(errorCode: String)
	}

	@UiThread
	fun execute(
		listener: SignInWithEmailAnPasswordInteractor.Listener,
		email: String,
		password: String
	) {
		this.listener = listener
		firebaseEmailPasswordManager.signInWithEmailAndPassword(
			signInWithEmailAndPasswordListener,
			activity,
			email,
			password
		)
	}

	private val signInWithEmailAndPasswordListener =
		object : FirebaseEmailPasswordManager.Listener.SignInListener {
			override fun onEmailPasswordSignInSuccess() {
				listener.onSuccess()
			}

			override fun onEmailPasswordSignInFail(errorCode: String) {
				listener.onFail(errorCode)
			}
		}
}