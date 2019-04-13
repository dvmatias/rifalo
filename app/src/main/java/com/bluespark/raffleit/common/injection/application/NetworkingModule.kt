package com.bluespark.raffleit.common.injection.application

import android.content.Context
import com.bluespark.raffleit.common.utils.managers.InternetConnectivityManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkingModule(val context: Context) {

	@Provides
	fun getInternetConnectivityManager(): InternetConnectivityManager =
		InternetConnectivityManager(context)

	/**
	 * Configure sign-in to request the user's ID, email address, and basic
	 * profile. ID and basic profile are included in DEFAULT_SIGN_IN.
	 */
	@Singleton
	@Provides
	fun getGoogleSignInOptions(): GoogleSignInOptions =
		GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestEmail()
			.build()

	/**
	 * Build a GoogleSignInClient with the options specified by googleSignInOptions.
	 */
	@Singleton
	@Provides
	fun getGoogleSignInClient(googleSignInOptions: GoogleSignInOptions): GoogleSignInClient =
		GoogleSignIn.getClient(context, googleSignInOptions)

}