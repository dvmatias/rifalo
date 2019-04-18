package com.bluespark.raffleit.screens.singin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.screens.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.view_login_btn.view.*
import kotlinx.android.synthetic.main.view_sign_in_facebook_btn.view.*
import kotlinx.android.synthetic.main.view_sign_in_google_btn.view.*
import javax.inject.Inject


class SignInActivity : BaseActivityImpl(), SignInContract.View, View.OnClickListener {

	@Inject
	lateinit var googleSignInClient: GoogleSignInClient
	@Inject
	lateinit var presenter: SignInPresenterImpl

	private lateinit var firebaseAuth: FirebaseAuth

	companion object {
		private val TAG = SignInActivity::class.java.simpleName
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			tv_get_help.id -> onGetHelpClick()
			login.login_btn.id -> onLoginClick()
			facebook.sign_in_facebook_btn.id -> onSignInFacebookClick()
			google.sign_in_google_btn.id -> onSignInGoogleClick()
			tv_sign_up.id -> onSignUpClick()
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		super.applyImmersiveFullScreen()
		setContentView(R.layout.activity_sign_in)
		getPresentationComponent().inject(this)

		tv_get_help.setOnClickListener(this)
		login.login_btn.setOnClickListener(this)
		facebook.sign_in_facebook_btn.setOnClickListener(this)
		google.sign_in_google_btn.setOnClickListener(this)
		tv_sign_up.setOnClickListener(this)

		firebaseAuth = FirebaseAuth.getInstance()
	}

	private fun signIn() {
		val signInIntent = googleSignInClient.signInIntent
		startActivityForResult(signInIntent, Constants.RC_SIGN_IN_GOOGLE)
	}

	private fun handleSignInGoogleResult(completedTask: Task<GoogleSignInAccount>) {
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
		if (requestCode == Constants.RC_SIGN_IN_GOOGLE) {
			// The Task returned from this call is always completed, no need to attach
			// a listener.
			val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
			handleSignInGoogleResult(task)
		}
	}

	private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
		val accountId = account?.id!!
		Log.d(TAG, "firebaseAuthWithGoogle(): $accountId")

		val credential = GoogleAuthProvider.getCredential(account.idToken, null)
		firebaseAuth.signInWithCredential(credential)
			.addOnCompleteListener(
				this
			) { task ->
				if (task.isSuccessful) {
					// Sign in success, update UI with the signed-in user's information
					Log.d(TAG, "signInWithCredential:success")
					val user = firebaseAuth.currentUser
					//						updateUI(user)



				} else {
					// If sign in fails, display a message to the user.
					Log.w(TAG, "signInWithCredential:failure", task.exception)
				}
			}
	}

	/**
	 * [SignInContract.View] implementation.
	 */
	override fun onGetHelpClick() {
		Toast.makeText(this, "onGetHelpClick()", Toast.LENGTH_SHORT).show()
	}

	override fun onLoginClick() {
		Toast.makeText(this, "onLoginClick()", Toast.LENGTH_SHORT).show()
	}

	override fun onSignInFacebookClick() {
		Toast.makeText(this, "onSignInFacebookClick()", Toast.LENGTH_SHORT).show()
	}

	override fun onSignInGoogleClick() {
		Toast.makeText(this, "onSignInGoogleClick()", Toast.LENGTH_SHORT).show()
	}

	override fun onSignUpClick() {
		Toast.makeText(this, "onSignUpClick()", Toast.LENGTH_SHORT).show()
	}

	override fun showLoading(show: Boolean) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun showNoInternetDialog(show: Boolean) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun goToMainScreen() {
		val goToMainScreenIntent = Intent(this, MainActivity::class.java)
		startActivity(goToMainScreenIntent)
		finish()
	}

}