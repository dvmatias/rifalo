package com.bluespark.raffleit.screens.signup.fragments.userinfo

import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BasePresenterImpl

class UserInfoPresenterImpl(
	view: UserInfoContract.View?,
	private var validateEmailInteractor: ValidateEmailInteractor,
	private var validatePasswordInteractor: ValidatePasswordInteractor,
	private var validatePasswordConfirmationInteractor: ValidatePasswordConfirmationInteractor
) :
	BasePresenterImpl<UserInfoContract.View>(),
	UserInfoContract.Presenter, ValidateEmailInteractor.Listener,
	ValidatePasswordInteractor.Listener, ValidatePasswordConfirmationInteractor.Listener {

	private var isValidEmail: Boolean
	private var isValidPassword: Boolean
	private var isValidPasswordConfirmation: Boolean

	init {
		bind(view)
		isValidEmail = false
		isValidPassword = false
		isValidPasswordConfirmation = false
	}

	override fun validateUser(signUpUser: SignUpUser) {
		view?.hideErrors()
		validateEmailInteractor.execute(this, signUpUser.email)
		validatePasswordInteractor.execute(this, signUpUser.password)
		validatePasswordConfirmationInteractor.execute(
			this,
			signUpUser.passwordConfirmation,
			signUpUser.password
		)

		if (isValidEmail && isValidPassword && isValidPasswordConfirmation) {
			view?.onValidEmailAndPassword()
		}
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

}