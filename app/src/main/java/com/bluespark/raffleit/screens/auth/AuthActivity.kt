package com.bluespark.raffleit.screens.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluespark.raffleit.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount




class AuthActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_auth)
		// Configure sign-in to request the user's ID, email address, and basic
		// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestEmail()
			.build()

		// Build a GoogleSignInClient with the options specified by gso.
		val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

	}
}