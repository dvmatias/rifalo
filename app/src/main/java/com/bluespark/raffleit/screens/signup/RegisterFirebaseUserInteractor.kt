package com.bluespark.raffleit.screens.signup

import android.app.Activity
import android.os.Handler
import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.google.firebase.auth.FirebaseAuth

class RegisterFirebaseUserInteractor(
	private var activity: Activity,
	private var firebaseAuth: FirebaseAuth
) {

	interface Listener {
		fun onSuccess()
		fun onFail()
	}

	fun execute(listener: Listener, signUpUser: SignUpUser) {
		Handler().postDelayed({
			firebaseAuth.createUserWithEmailAndPassword(signUpUser.email, signUpUser.password)
				.addOnCompleteListener(
					activity
				) { task ->
					// If sign in fails, display a message to the user. If sign in succeeds
					// the auth state listener will be notified and logic to handle the
					// signed in user can be handled in the listener.
					if (task.isSuccessful) {
						listener.onSuccess()
					} else {
						listener.onFail()
					}
				}
		}, 1000)


	}

	companion object {
		@Suppress("unused")
		private val TAG = RegisterFirebaseUserInteractor::class.java.simpleName
	}

}