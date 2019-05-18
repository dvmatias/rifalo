package com.bluespark.raffleit.screens.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.common.utils.UiHelper
import com.bluespark.raffleit.common.utils.managers.FirebaseSignInGoogleManager
import com.bluespark.raffleit.common.views.dialogs.LoadingDialogFragment
import com.bluespark.raffleit.common.views.dialogs.WarningDialogFragmentImpl
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


class SignInActivity : BaseActivityImpl(), SignInContract.View, View.OnClickListener {

	@Inject
	lateinit var presenter: SignInPresenterImpl
	@Inject
	lateinit var firebaseAuth: FirebaseAuth
	@Inject
	lateinit var firebaseSignInGoogleManager: FirebaseSignInGoogleManager
	@Inject
	lateinit var warningDialogFragment: WarningDialogFragmentImpl

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
		etcv_user_password.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
			if (actionId == EditorInfo.IME_ACTION_GO) {
				onLoginClick()
				UiHelper.hideKeyboardFrom(this@SignInActivity, v)
				return@OnEditorActionListener true
			}
			return@OnEditorActionListener false
		})
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
	 * [SignInContract.View] implementation.
	 */
	override fun onGetHelpClick() {
		Toast.makeText(this, "onGetHelpClick()", Toast.LENGTH_SHORT).show()
	}

	override fun onLoginClick() {
		// On "login" button click, clear errors.
		etcv_user_email.setStatusNormal()
		etcv_user_password.setStatusNormal()
		// Set email and password entered.
		val email: String = etcv_user_email.getText()
		val password: String = etcv_user_password.getText()
		// Validate email and password and trigger login process.
		presenter.validateCredentials(email, password)
	}

	override fun onSignInEmailPasswordSuccess() {
		val firebaseUser = firebaseAuth.currentUser
		val isEmailVerified = (firebaseUser?.isEmailVerified)
		if (firebaseUser != null && firebaseUser.isEmailVerified) {
			// Sign in success, update UI with the signed-in user's information
			val user = firebaseAuth.currentUser
			if (user != null) {
				presenter.addUserToDatabase(user.uid)
			}
		} else if (isEmailVerified != null && !isEmailVerified) {
			showLoading(Constants.HIDE_LOADING)
			setEmailError("You must verify your email.")
		}
	}

	override fun onSignInFacebookClick() {
		Toast.makeText(this, "onSignInFacebookClick()", Toast.LENGTH_SHORT).show()
	}

	override fun onSignInGoogleClick() {
		presenter.signInGoogle()
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

	override fun showUserNotFoundErrorDialog() {
		warningDialogFragment.setup(
			getString(R.string.title_user_not_found_error_dialog),
			getString(R.string.msg_user_not_found_error_dialog),
			getString(R.string.btn_label_user_not_found_error_dialog)
		)
		dialogsManager.showRetainedDialogWithId(warningDialogFragment, LoadingDialogFragment.TAG)
	}

	override fun showWrongPasswordErrorDialog() {
		warningDialogFragment.setup(
			getString(R.string.title_wrong_password_error_dialog),
			getString(R.string.msg_wrong_password_error_dialog),
			getString(R.string.btn_label_wrong_password_error_dialog)
		)
		dialogsManager.showRetainedDialogWithId(warningDialogFragment, LoadingDialogFragment.TAG)
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