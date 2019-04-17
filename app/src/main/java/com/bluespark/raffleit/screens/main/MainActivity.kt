package com.bluespark.raffleit.screens.main

import android.os.Bundle
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import javax.inject.Inject

class MainActivity : BaseActivityImpl() {

	@Inject
	lateinit var mGoogleSignInClient: GoogleSignInClient

	companion object {
		@Suppress("unused")
		private val TAG = MainActivity::class.java.simpleName
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		// Inject this view.
		getPresentationComponent().inject(this)

		mGoogleSignInClient.signOut()
			.addOnCompleteListener(this) {
				finish()
			}
	}
}