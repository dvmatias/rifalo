package com.bluespark.raffleit.screens.signin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import kotlinx.android.synthetic.main.view_login_btn.view.*
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
		val email: String = et_user_name.text.toString()
		val password: String = et_user_password.text.toString()

		if (TextUtils.isEmpty(email)) {
			Toast.makeText(applicationContext, "Please enter email...", Toast.LENGTH_LONG).show()
			return
		}
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
			return
		}

		firebaseAuth.signInWithEmailAndPassword(email, password)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_LONG)
						.show()
//					progressBar.setVisibility(View.GONE)

					goToMainScreen()
				} else {
					Toast.makeText(
						applicationContext,
						"Login failed! Please try again later",
						Toast.LENGTH_LONG
					).show()
//					progressBar.setVisibility(View.GONE)
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

	override fun goToSignUpScreen() {
		val goToSignUpScreenIntent = Intent(this, SignUpActivity::class.java)
		startActivity(goToSignUpScreenIntent)
	}
}