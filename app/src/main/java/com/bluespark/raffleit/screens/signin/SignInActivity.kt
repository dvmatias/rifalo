package com.bluespark.raffleit.screens.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.common.utils.managers.FirebaseSignInGoogleManager
import com.bluespark.raffleit.screens.main.MainActivity
import com.bluespark.raffleit.screens.signup.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.view_sign_in_facebook_btn.view.*
import kotlinx.android.synthetic.main.view_sign_in_google_btn.view.*
import javax.inject.Inject


class SignInActivity : BaseActivityImpl(), SignInContract.View, View.OnClickListener,
	FirebaseSignInGoogleManager.Listener {

	@Inject
	lateinit var presenter: SignInPresenterImpl
	@Inject
	lateinit var firebaseAuth: FirebaseAuth
	@Inject
	lateinit var firebaseSignInGoogleManager: FirebaseSignInGoogleManager

	companion object {
		@Suppress("unused")
		private val TAG = SignInActivity::class.java.simpleName
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			tv_get_help.id -> onGetHelpClick()
			login_btn.id -> onLoginClick()
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
		login_btn.setOnClickListener(this)
		facebook.sign_in_facebook_btn.setOnClickListener(this)
		google.sign_in_google_btn.setOnClickListener(this)
		tv_sign_up.setOnClickListener(this)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		// Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
		if (requestCode == Constants.RC_SIGN_IN_GOOGLE) {
			// The Task returned from this call is always completed, no need to attach
			// a listener.
			val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
			firebaseSignInGoogleManager.handleSignInGoogleResult(this, task)
		}
	}

	/**
	 * [FirebaseSignInGoogleManager.Listener] implementation
	 */

	override fun onGoogleSignInSuccess() {
		goToMainScreen()
	}

	override fun onGoogleSignInFail() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	/**
	 * [SignInContract.View] implementation.
	 */
	override fun onGetHelpClick() {
		Toast.makeText(this, "onGetHelpClick()", Toast.LENGTH_SHORT).show()
	}

	override fun onLoginClick() {
		val email: String = etcv_user_email.getText()
		val password: String = etcv_user_password.getText()
		presenter.validateCredentials(email, password)
	}

	override fun onSignInEmailPassword(email: String, password: String) {
		firebaseAuth.signInWithEmailAndPassword(email, password)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					// Login successful.
					val firebaseUser = firebaseAuth.currentUser
					if (firebaseUser != null && firebaseUser.isEmailVerified) {
						goToMainScreen()
					}
				} else {
					// Login fail.
					showLoadingDialog(false)
				}
			}
	}

	override fun onSignInFacebookClick() {
		Toast.makeText(this, "onSignInFacebookClick()", Toast.LENGTH_SHORT).show()
	}

	override fun onSignInGoogleClick() {
		Toast.makeText(this, "onSignInGoogleClick()", Toast.LENGTH_SHORT).show()
		firebaseSignInGoogleManager.signIn(this)
	}

	override fun onSignUpClick() {
		goToSignUpScreen()
	}



	override fun showLoadingDialog(show: Boolean) {
		super.showLoading(show)
	}

	override fun showNoInternetDialog(show: Boolean) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun setEmailError(errorMsg: String) {
		etcv_user_email.setStatusError(errorMsg)
	}

	override fun setPasswordError(errorMsg: String) {
		etcv_user_password.setStatusError(errorMsg)
	}

	override fun goToMainScreen() {
		val goToMainScreenIntent = Intent(this, MainActivity::class.java)
		startActivity(goToMainScreenIntent)
		finish()
	}

	override fun goToSignUpScreen() {
		val goToSignUpScreenIntent = Intent(this, SignUpActivity::class.java)
		startActivity(goToSignUpScreenIntent)
	}
}