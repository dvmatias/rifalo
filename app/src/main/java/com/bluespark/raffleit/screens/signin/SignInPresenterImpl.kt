package com.bluespark.raffleit.screens.signin

import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.mvp.BasePresenterImpl
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidateEmailInteractor
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidatePasswordInteractor

class SignInPresenterImpl(
	view: SignInContract.View?,
	private var validateEmailInteractor: ValidateEmailInteractor,
	private var validatePasswordInteractor: ValidatePasswordInteractor,
	private var signInWithEmailAnPasswordInteractor: SignInWithEmailAnPasswordInteractor
) : BasePresenterImpl<SignInContract.View>(),
	SignInContract.Presenter, ValidateEmailInteractor.Listener,
	ValidatePasswordInteractor.Listener {

	private var isValidEmail: Boolean
	private var isValidPassword: Boolean

	init {
		bind(view)
		isValidEmail = false
		isValidPassword = false
	}

	override fun signInEmailAndPassword(email: String, password: String) {
		signInWithEmailAnPasswordInteractor.execute(
			signInWithEmailAnPasswordInteractorListener,
			email,
			password
		)
	}

	override fun signInGoogle() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun signInFacebook() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun validateCredentials(email: String?, password: String?) {
		validateEmailInteractor.execute(this, email)
		validatePasswordInteractor.execute(this, password)

		if (isValidEmail && isValidPassword) {
			view?.showLoadingDialog(true)
			signInEmailAndPassword(email!!, password!!)
		} else {
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
	 * [SignInWithEmailAnPasswordInteractor.Listener] implementation.
	 */

	private val signInWithEmailAnPasswordInteractorListener =
		object : SignInWithEmailAnPasswordInteractor.Listener {
			override fun onSuccess() {// Login successful.
				view?.onSignInEmailPasswordSuccess()
				view?.showLoadingDialog(Constants.HIDE_LOADING)
			}

			override fun onFail(errorCode: String) {
				view?.showLoadingDialog(Constants.HIDE_LOADING)
			}
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
}