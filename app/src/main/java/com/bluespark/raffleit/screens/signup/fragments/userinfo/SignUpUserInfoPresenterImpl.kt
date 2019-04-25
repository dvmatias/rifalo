package com.bluespark.raffleit.screens.signup.fragments.userinfo

import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BasePresenterImpl

class SignUpUserInfoPresenterImpl(
	view: SignUpUserInfoContract.View?,
	private var validateEmailInteractor: ValidateEmailInteractor,
	private var validatePasswordInteractor: ValidatePasswordInteractor,
	private var validatePasswordConfirmationInteractor: ValidatePasswordConfirmationInteractor
) :
	BasePresenterImpl<SignUpUserInfoContract.View>(),
	SignUpUserInfoContract.Presenter, ValidateEmailInteractor.Listener,
	ValidatePasswordInteractor.Listener, ValidatePasswordConfirmationInteractor.Listener {

	private var validEmail: Boolean
	private var validPassword: Boolean
	private var validPasswordConfirmation: Boolean

	init {
		bind(view)
		validEmail = false
		validPassword = false
		validPasswordConfirmation = false
	}

	override fun validateUser(signUpUser: SignUpUser) {
		validateEmailInteractor.execute(this, signUpUser.email)
		validatePasswordInteractor.execute(this, signUpUser.password)
		validatePasswordConfirmationInteractor.execute(
			this,
			signUpUser.passwordConfirmation,
			signUpUser.password
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
		validEmail = true
	}

	override fun onInvalidEmail(errorMsg: String) {
		validEmail = false
		manageEmailError(errorMsg)
	}

	/**
	 * [ValidatePasswordInteractor.Listener] implementation.
	 */

	override fun onValidPassword() {
		validPassword = true
	}

	override fun onInvalidPassword(errorMsg: String) {
		validPassword = false
		managePasswordError(errorMsg)
	}

	/**
	 * [ValidatePasswordConfirmationInteractor.Listener] implementation.
	 */

	override fun onValidPasswordConfirmation() {
		validPasswordConfirmation = true
	}

	override fun onInvalidPasswordConfirmation(errorMsg: String) {
		validPasswordConfirmation = false
		managePasswordConfirmationError(errorMsg)
	}

}