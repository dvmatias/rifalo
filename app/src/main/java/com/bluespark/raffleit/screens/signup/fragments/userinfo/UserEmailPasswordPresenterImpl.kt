package com.bluespark.raffleit.screens.signup.fragments.userinfo

import android.os.Handler
import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BasePresenterImpl
import com.bluespark.raffleit.screens.signup.interactors.RegisterFirebaseUserInteractor
import com.google.firebase.auth.FirebaseUser

class UserEmailPasswordPresenterImpl(
	view: UserEmailPasswordContract.View?,
	private var validateEmailInteractor: ValidateEmailInteractor,
	private var validatePasswordInteractor: ValidatePasswordInteractor,
	private var validatePasswordConfirmationInteractor: ValidatePasswordConfirmationInteractor,
	private var registerFirebaseUserInteractor: RegisterFirebaseUserInteractor
) :
	BasePresenterImpl<UserEmailPasswordContract.View>(),
	UserEmailPasswordContract.Presenter, ValidateEmailInteractor.Listener,
	ValidatePasswordInteractor.Listener, ValidatePasswordConfirmationInteractor.Listener {

	private var isValidEmail: Boolean
	private var isValidPassword: Boolean
	private var isValidPasswordConfirmation: Boolean
	var email: String? = ""
	var password: String? = ""

	init {
		bind(view)
		isValidEmail = false
		isValidPassword = false
		isValidPasswordConfirmation = false
	}

	override fun validateEmailAndPassword(
		email: String?,
		password: String?,
		passwordConfirmation: String?
	) {
		view?.hideErrors()
		validateEmailInteractor.execute(this, email)
		validatePasswordInteractor.execute(this, password)
		validatePasswordConfirmationInteractor.execute(
			this,
			passwordConfirmation,
			password
		)
		this.email = email
		this.password = password
		if (isValidEmail && isValidPassword && isValidPasswordConfirmation) {
			view?.onValidEmailAndPassword(email!!, password!!)
		}
	}

	override fun createUserWithEmailAndPassword(email: String, password: String) {
		view?.showLoading(Constants.SHOW_LOADING)
		registerFirebaseUserInteractor.execute(
			registerFirebaseUserInteractorListener,
			email,
			password
		)
	}

	/**
	 * Manage error on email field.
	 */
	override fun manageEmailError(errorMsg: String) {
		view?.setEmailError(errorMsg)
	}

	/**
	 * Manage error on password field.
	 */
	override fun managePasswordError(errorMsg: String) {
		view?.setPasswordError(errorMsg)
	}

	/**
	 * Manage error on password confirmation field.
	 */
	override fun managePasswordConfirmationError(errorMsg: String) {
		view?.setPasswordConfirmationError(errorMsg)
	}

	/**
	 * [ValidateEmailInteractor.Listener] implementation.
	 */

	override fun onValidEmail() {
		isValidEmail = true
	}

	override fun onInvalidEmail(errorMsg: String) {
		isValidEmail = false
		manageEmailError(errorMsg)
	}

	/**
	 * [ValidatePasswordInteractor.Listener] implementation.
	 */

	override fun onValidPassword() {
		isValidPassword = true
	}

	override fun onInvalidPassword(errorMsg: String) {
		isValidPassword = false
		managePasswordError(errorMsg)
	}

	/**
	 * [ValidatePasswordConfirmationInteractor.Listener] implementation.
	 */

	override fun onValidPasswordConfirmation() {
		isValidPasswordConfirmation = true
	}

	override fun onInvalidPasswordConfirmation(errorMsg: String) {
		isValidPasswordConfirmation = false
		managePasswordConfirmationError(errorMsg)
	}

	/**
	 * [RegisterFirebaseUserInteractor.Listener] implementation.
	 */

	private val registerFirebaseUserInteractorListener =
		object : RegisterFirebaseUserInteractor.Listener {
			override fun onSuccess(firebaseUser: FirebaseUser) {
				view?.showLoading(Constants.HIDE_LOADING)
				view?.goToValidatePhoneFragment()
			}

			override fun onFail(errorCode: String) {
				view?.showLoading(Constants.HIDE_LOADING)
				if (!errorCode.isEmpty()) {
					view?.showUserCreationErrorDialog(errorCode)
				}
			}
		}

}