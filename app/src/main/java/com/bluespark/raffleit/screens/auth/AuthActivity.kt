package com.bluespark.raffleit.screens.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject


class AuthActivity : BaseActivityImpl(), View.OnClickListener {

	@Inject
	lateinit var mGoogleSignInClient: GoogleSignInClient

	companion object {
		private val TAG = AuthActivity::class.java.simpleName
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			btn_sign_in_google.id -> signIn()
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_auth)
		// Inject this view.
		getPresentationComponent().inject(this)

		btn_sign_in_google.setOnClickListener(this)
	}

	private fun signIn() {
		val signInIntent = mGoogleSignInClient.signInIntent
		startActivityForResult(signInIntent, Constants.RC_SIGN_IN)
	}

	private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
		try {
			val account = completedTask.getResult(ApiException::class.java)

			// Signed in successfully, show auth
//			updateUI(account) TODO
			Log.w(TAG, "signInResult:success")

		} catch (e: ApiException) {
			// The ApiException status code indicates the detailed failure reason.
			// Please refer to the GoogleSignInStatusCodes class reference for more information.
			Log.w(TAG, "signInResult:failed code=" + e.statusCode)
//			updateUI(null) TODO
		}

	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		// Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
		if (requestCode == Constants.RC_SIGN_IN) {
			// The Task returned from this call is always completed, no need to attach
			// a listener.
			val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
			handleSignInResult(task)
		}
	}

}