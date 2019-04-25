package com.bluespark.raffleit.screens.signup.fragments.userinfo

import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.mvp.BasePresenterImpl

class SignUpUserInfoPresenterImpl(view: SignUpUserInfoContract.View?) :
	BasePresenterImpl<SignUpUserInfoContract.View>(),
	SignUpUserInfoContract.Presenter {

	init {
		bind(view)
	}

	override fun validateUser(signUpUser: SignUpUser) {
		val validEmail = isValidEmail(signUpUser.email)
		val validPassword = isValidPassword(signUpUser.password)
		val validPasswordConfirmation = isValidPasswordConfirmation(signUpUser.passwordConfirmation)

		if (validEmail.first && validPassword.first && validPasswordConfirmation.first) {
			// TODO todo valido
		} else {
			manageErrors(validEmail, validPassword, validPasswordConfirmation)
		}
	}

	/**
	 * Validate the user's email.
	 *
	 * @return [Pair<Boolean, String>] Boolean is true for valid email, false otherwise.
	 *          String value represent an error to be displayed on email edit text.
	 */
	override fun isValidEmail(email: String?): Pair<Boolean, String> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	/**
	 * Validate the user's password.
	 *
	 * @return [Pair<Boolean, String>] Boolean is true for valid password, false otherwise.
	 *          String value represent an error to be displayed on password edit text.
	 */
	override fun isValidPassword(password: String?): Pair<Boolean, String> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	/**
	 * Validate the user's password confirmation.
	 *
	 * @return [Pair<Boolean, String>] Boolean is true for valid password confirmation,
	 *          false otherwise. String value represent an error to be displayed on password
	 *          confirmation edit text.
	 */
	override fun isValidPasswordConfirmation(passwordConfirmation: String?): Pair<Boolean, String> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	/**
	 * Set proper errors.
	 */
	private fun manageErrors(
		validEmail: Pair<Boolean, String>,
		validPassword: Pair<Boolean, String>,
		validPasswordConfirmation: Pair<Boolean, String>
	) {
		if (!validEmail.first) view?.setEmailError(validEmail.second)
		if (!validPassword.first) view?.setPasswordError(validPassword.second)
		if (!validPasswordConfirmation.first) view?.setPasswordConfirmationError(
			validPasswordConfirmation.second
		)
	}
}