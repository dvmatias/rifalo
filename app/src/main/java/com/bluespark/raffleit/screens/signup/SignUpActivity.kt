package com.bluespark.raffleit.screens.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.Constants.Companion.EXTRA_KEY_COUNTRY_LIST
import com.bluespark.raffleit.common.Constants.Companion.EXTRA_KEY_SELECTED_COUNTRY
import com.bluespark.raffleit.common.Constants.Companion.REQUEST_CODE_CHOOSE_COUNTRY_ACTIVITY
import com.bluespark.raffleit.common.model.objects.Country
import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.common.utils.managers.FirebaseSignInPhoneManager
import com.bluespark.raffleit.common.views.AgreementView
import com.bluespark.raffleit.common.views.CountryCodeSelector
import com.bluespark.raffleit.common.views.dialogs.LoadingDialogFragment
import com.bluespark.raffleit.common.views.dialogs.WarningDialogFragmentImpl
import com.bluespark.raffleit.screens.choosecountry.ChooseCountryActivity
import com.bluespark.raffleit.screens.signup.fragments.phoneregistration.UserPhoneVerificationFragment
import com.bluespark.raffleit.screens.signup.fragments.phonevalidation.UserPhoneValidationFragment
import com.bluespark.raffleit.screens.signup.fragments.userinfo.UserInfoFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject


class SignUpActivity : BaseActivityImpl(), SignUpContract.View, View.OnClickListener,
	UserInfoFragment.Listener, UserPhoneValidationFragment.Listener,
	CountryCodeSelector.Listener, AgreementView.Listener, UserPhoneVerificationFragment.Listener,
	FirebaseSignInPhoneManager.Listener.SignIn {

	@Inject
	lateinit var presenter: SignUpPresenterImpl
	@Inject
	lateinit var adapter: SignUpFragmentAdapter
	@Inject
	lateinit var warningDialogFragment: WarningDialogFragmentImpl
	@Inject
	lateinit var gson: Gson
	@Inject
	lateinit var firebaseAuth: FirebaseAuth

	private lateinit var selectedCountry: Country
	private var isAgreementAccepted: Boolean = false
	private var signUpUser: SignUpUser = SignUpUser("", "", null)

	companion object {
		@Suppress("unused")
		private val TAG = SignUpActivity::class.java.simpleName
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		super.applyImmersiveFullScreen()
		setContentView(R.layout.activity_sign_up)
		getPresentationComponent().inject(this)

		setFlowButtonLabel(getString(R.string.label_btn_next))
		setListeners()
		setupPager()
		setSelectedCountry(
			Country(
				"XX",
				"+0",
				"Default",
				"https://firebasestorage.googleapis.com/v0/b/rifalo-805c2.appspot.com/o/images_country_codes%2Fcountry_code_default.png?alt=media&token=f3e29d6e-3aa3-4901-9d0e-7f31b26b21ce"
			)
		)
	}

	override fun onResume() {
		super.onResume()
		presenter.fetchCountryCodes()
	}

	private fun setListeners() {
		back_btn.setOnClickListener(this)
		flow_btn.setOnClickListener(this)
	}

	private fun setupPager() {
		pager.adapter = adapter
	}

	private fun getCurrentFragment(): Fragment = adapter.getItem(pager.currentItem)

	/**
	 * [SignUpContract.View] implementation.
	 */

	/**
	 * Set main button label.
	 */
	override fun setFlowButtonLabel(label: String) {
		flow_btn.labelText = label
	}

	/**
	 * The phone has been verified trough OTP code.
	 */
	override fun onVerifiedPhone(phoneNumber: String) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	/**
	 * Show/Hide loading fragment.
	 */
	override fun onLoading(show: Boolean) {
		super.showLoading(show)
	}

	override fun showSelectedCountry() {
		val currentFragment = getCurrentFragment()
		if (currentFragment is UserPhoneValidationFragment)
			currentFragment.showSelectedCountry(this.selectedCountry)
	}

	/**
	 * Back button clicked.
	 */
	override fun onBackButtonClicked() {
		Toast.makeText(applicationContext, "Back clicked!", Toast.LENGTH_SHORT).show()
	}

	/**
	 * Flow button click.
	 */
	override fun onFlowButtonClicked() {
		val currentFragment = getCurrentFragment()
		if (currentFragment is UserInfoFragment)
			currentFragment.validateEmailAndPassword()
		if (currentFragment is UserPhoneValidationFragment)
			currentFragment.validatePhone()
		if (currentFragment is UserPhoneVerificationFragment)
			currentFragment.verifyOtp()
	}

	override fun goToValidatePhoneFragment() {
		pager.setCurrentItem(1, true)
		setFlowButtonLabel(getString(R.string.label_btn_validate_phone))
	}

	override fun goToChooseCountryScreen() {
		val intent = Intent(this, ChooseCountryActivity::class.java)
		intent.putExtra(
			EXTRA_KEY_SELECTED_COUNTRY,
			gson.toJson(selectedCountry)
		)
		intent.putExtra(
			EXTRA_KEY_COUNTRY_LIST,
			gson.toJson(presenter.countryList)
		)
		startActivityForResult(intent, REQUEST_CODE_CHOOSE_COUNTRY_ACTIVITY)
	}

	override fun goToRegisterPhoneFragment(phoneNumber: String) {
		if (isAgreementAccepted) {
			pager.setCurrentItem(2, true)
			setFlowButtonLabel(getString(R.string.label_btn_verify_phone))
			val currentFragment = getCurrentFragment()
			if (currentFragment is UserPhoneVerificationFragment)
				currentFragment.sendOtpCode(phoneNumber)
		} else {
			showAgreementWarningDialog()
		}
	}

	override fun setSelectedCountry(country: Country) {
		this.selectedCountry = country
		showSelectedCountry()
	}

	/**
	 * Method called when an [SignUpUser] has been created by the user. This method must be called
	 * once the user validate his info trough [UserInfoFragment], [UserPhoneValidationFragment] and
	 * [UserPhoneVerificationFragment], once the user enter the OTP number and click [flow_btn].
	 */
	override fun registerUser(phoneAuthCredential: PhoneAuthCredential) {
		presenter.registerFirebaseUser(signUpUser, phoneAuthCredential)
	}

	override fun showAgreementWarningDialog() {
		warningDialogFragment.setup(
			getString(R.string.title_warning_agreement),
			getString(R.string.msg_warning_agreement),
			getString(R.string.label_btn_warning_agreement)
		)
		dialogsManager.showRetainedDialogWithId(warningDialogFragment, LoadingDialogFragment.TAG)
	}

	/**
	 * Dialog warning to communicate user that a verification email was sent to the
	 * provided user email in the registration process.
	 */
	override fun showEmailVerificationDialog() {
		warningDialogFragment.let {
			it.setup(
				"Verify Email Address",
				"Your email address must be verified before you can sign in. You will receive an email to the email address provided. Please follow the instructions.",
				"ok"
			)
			it.setOnClickListener(object : WarningDialogFragmentImpl.ButtonClickListener {
				override fun onOkButtonClick() {
					it.dismiss()
					finish()
				}
			})
		}
		dialogsManager.showRetainedDialogWithId(warningDialogFragment, LoadingDialogFragment.TAG)
	}

	/**
	 * Dialog warning to communicate user that the user creation failed.
	 */
	override fun showUserCreationErrorDialog(errorCode: String) {
		val errorMsg =
			when (errorCode) {
				"auth/email-already-in-use" -> "auth/email-already-in-use"
				"auth/invalid-email" -> "auth/invalid-email"
				"auth/operation-not-allowed" -> "auth/operation-not-allowed"
				"auth/weak-password" -> "auth/operation-not-allowed"
				else -> ""
			}
		warningDialogFragment.let {
			it.setup(
				"Sign In Error",
				errorMsg,
				"ok"
			)
			it.setOnClickListener(object : WarningDialogFragmentImpl.ButtonClickListener {
				override fun onOkButtonClick() {
					it.dismiss()
					finish()
				}
			})
		}
		dialogsManager.showRetainedDialogWithId(warningDialogFragment, LoadingDialogFragment.TAG)
	}

	/**
	 * [UserInfoFragment.Listener] implementation.
	 */

	override fun onValidEmailAndPassword(email: String, password: String) {
		// Create an user with valid email and password, the phone is set in next fragment.
		signUpUser.email = email
		signUpUser.password = password
		goToValidatePhoneFragment()
	}

	/**
	 * [UserPhoneValidationFragment.Listener] implementation.
	 */

	override fun onValidPhone(phoneNumber: String) {
		goToRegisterPhoneFragment(phoneNumber)
	}

	override fun showLoadingDialog(show: Boolean) {
		super.showLoading(show)
	}

	/**
	 * [UserPhoneVerificationFragment.Listener] implementation.
	 */


	/**
	 * [CountryCodeSelector.Listener] implementation.
	 */

	override fun onCountryClick() {
		goToChooseCountryScreen()
	}

	override fun onPhoneEmpty() {
		// Disable terms and conditions view.
		val currentFragment = getCurrentFragment()
		if (currentFragment is UserPhoneValidationFragment)
			currentFragment.enableTermsAndConditions(false)
	}

	override fun onPhoneNotEmpty() {
		// Enable terms and conditions view.
		val currentFragment = getCurrentFragment()
		if (currentFragment is UserPhoneValidationFragment)
			currentFragment.enableTermsAndConditions(true)
	}

	/**
	 * [FirebaseSignInPhoneManager.Listener.SignIn] implementation.
	 */

	override fun onPhoneSignInSuccess(phoneAuthCredential: PhoneAuthCredential) {
		registerUser(phoneAuthCredential)
	}

	override fun onPhoneSignInFail(errorMsg: String) {
		// TODO
	}

	/**
	 *
	 */

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.back_btn -> onBackButtonClicked()
			R.id.flow_btn -> onFlowButtonClicked()
		}
	}

	/**
	 * On Activity Result.
	 */

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == REQUEST_CODE_CHOOSE_COUNTRY_ACTIVITY) {
			val selectedCountry = when (resultCode) {
				Activity.RESULT_OK -> {
					gson.fromJson(
						data?.getStringExtra(EXTRA_KEY_SELECTED_COUNTRY),
						Country::class.java
					)
				}
				Activity.RESULT_CANCELED -> this.selectedCountry
				else -> Country(
					"XX",
					"+0",
					"Default",
					"https://firebasestorage.googleapis.com/v0/b/rifalo-805c2.appspot.com/o/images_country_codes%2Fcountry_code_default.png?alt=media&token=f3e29d6e-3aa3-4901-9d0e-7f31b26b21ce"
				)
			}
			setSelectedCountry(selectedCountry)
		}
	}

	/**
	 * [AgreementView.Listener] implementation.
	 */

	override fun onAgreementAccepted() {
		isAgreementAccepted = true
	}

	override fun onAgreementRejected() {
		isAgreementAccepted = false
	}

	fun laconchadesumadre() {
		Handler().postDelayed({
			val authStateListener =
				FirebaseAuth.AuthStateListener { firebaseAuth ->
					if (firebaseAuth.currentUser == null) {
						//Do anything here which needs to be done after signout is complete
						Log.d(TAG, "signInWithCredential:fail")
					} else {
						Log.d(TAG, "signInWithCredential:fail")
					}
				}

//Init and attach
			firebaseAuth = FirebaseAuth.getInstance()
			firebaseAuth.addAuthStateListener(authStateListener)

//Call signOut()
			firebaseAuth.signOut()
		}, 5000)
	}
}
