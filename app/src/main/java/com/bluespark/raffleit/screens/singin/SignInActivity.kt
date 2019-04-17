package com.bluespark.raffleit.screens.singin

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*
import javax.inject.Inject


class SignInActivity : BaseActivityImpl(), View.OnClickListener {

	@Inject
	lateinit var mGoogleSignInClient: GoogleSignInClient

	private lateinit var mAuth: FirebaseAuth

	companion object {
		private val TAG = SignInActivity::class.java.simpleName
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			btn_sign_in_google.id -> signIn()
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_sign_in)
		// Inject this view.
		getPresentationComponent().inject(this)
		// Draw below status bar, android:statusBarColor attribute must be transparent.
		window.decorView.systemUiVisibility =
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

		// TODO: uncomment
//		btn_sign_in_google.setOnClickListener(this)

		mAuth = FirebaseAuth.getInstance()

	}

	private fun signIn() {
		val signInIntent = mGoogleSignInClient.signInIntent
		startActivityForResult(signInIntent, Constants.RC_SIGN_IN)
	}

	private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
		try {
			val account = completedTask.getResult(ApiException::class.java)
			firebaseAuthWithGoogle(account)
			// Signed in successfully, show auth
			Log.w(TAG, "signInResult:success")
		} catch (e: ApiException) {
			// The ApiException status code indicates the detailed failure reason.
			// Please refer to the GoogleSignInStatusCodes class reference for more information.
			Log.w(TAG, "signInResult:failed code=" + e.statusCode)
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

	private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
		val accountId = account?.id!!
		Log.d(TAG, "firebaseAuthWithGoogle(): $accountId")

		val credential = GoogleAuthProvider.getCredential(account.idToken, null)
		mAuth.signInWithCredential(credential)
			.addOnCompleteListener(
				this
			) { task ->
				if (task.isSuccessful) {
					// Sign in success, update UI with the signed-in user's information
					Log.d(TAG, "signInWithCredential:success")
					val user = mAuth.currentUser
					//						updateUI(user)
//					This code clears which account is connected to the app. To sign in again, the user must choose their account again.
//					mGoogleSignInClient.signOut()
//						.addOnCompleteListener(this) {
//							finish()
//						}

				} else {
					// If sign in fails, display a message to the user.
					Log.w(TAG, "signInWithCredential:failure", task.exception)
				}
			}
	}

}