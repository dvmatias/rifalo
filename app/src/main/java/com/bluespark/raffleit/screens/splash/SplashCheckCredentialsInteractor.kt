package com.bluespark.raffleit.screens.splash

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn

class SplashCheckCredentialsInteractor(private var context: Context) {

	interface Listener {
		fun onUserSignedIn()
		fun onUserNotLoggedIn()
	}

	fun execute(listener: SplashCheckCredentialsInteractor.Listener) {
		Log.d(TAG, "MABEL execute()")
		// Check for existing Google Sign In account, if the user is already signed in
		// the GoogleSignInAccount will be non-null.
		val account = GoogleSignIn.getLastSignedInAccount(context)
		if (account != null) listener.onUserSignedIn() else listener.onUserNotLoggedIn()
	}

	companion object {
		private val TAG = SplashCheckCredentialsInteractor::class.java.simpleName
	}

}