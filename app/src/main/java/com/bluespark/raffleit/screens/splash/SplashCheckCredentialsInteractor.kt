package com.bluespark.raffleit.screens.splash

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class SplashCheckCredentialsInteractor(
	private var firebaseAuth: FirebaseAuth
) {

	interface Listener {
		fun onUserSignedIn()
		fun onUserNotLoggedIn()
	}

	fun execute(listener: SplashCheckCredentialsInteractor.Listener) {
		Log.d(TAG, "MABEL execute()")
		// Check for existing user, if the user is already signed in the user will be non-null.
		val user = firebaseAuth.currentUser
		if (user != null) listener.onUserSignedIn() else listener.onUserNotLoggedIn()
	}

	companion object {
		private val TAG = SplashCheckCredentialsInteractor::class.java.simpleName
	}

}