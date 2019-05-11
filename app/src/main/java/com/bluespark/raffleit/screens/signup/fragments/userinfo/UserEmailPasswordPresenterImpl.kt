package com.bluespark.raffleit.screens.signup.fragments.userinfo

import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.mvp.BasePresenterImpl
import com.bluespark.raffleit.screens.signup.interactors.CreateUserWithEmailAndPasswordInteractor
import com.google.firebase.auth.FirebaseUser

class UserEmailPasswordPresenterImpl(
	view: UserEmailPasswordContract.View?,
	private var validateEmailInteractor: ValidateEmailInteractor,
	private var validatePasswordInteractor: ValidatePasswordInteractor,
	private var validatePasswordConfirmationInteractor: ValidatePasswordConfirmationInteractor,
	private var createUserWithEmailAndPasswordInteractor: CreateUserWithEmailAndPasswordInteractor
) :
	BasePresenterImpl<UserEmailPasswordContract.View>(),
	UserEmailPasswordContract.Presenter {

	private var isValidEmail: Boolean
	private var isValidPassword: Boolean
	private var isValidPasswordConfirmation: Boolean

	init {
		bind(view)
		isValidEmail = false
		isValidPassword = false
		isValidPasswordConfirmation = false
	}

	/**
	 * Validate all three fields. If all three of them are valid, then try to create a
	 * firebase user.
	 *
	 * @param email [String] user input email.
	 * @param password [String] user input password.
	 * @param passwordConfirmation [String] user input password confirmation.
	 */
	override fun validateEmailAndPassword(
		email: String?,
		password: String?,
		passwordConfirmation: String?
	) {
		view?.hideErrors()
		validateEmailInteractor.execute(validateEmailInteractorListener, email)
		validatePasswordInteractor.execute(validatePasswordInteractorListener, password)
		validatePasswordConfirmationInteractor.execute(
			validatePasswordConfirmationInteractorListener,
			passwordConfirmation,
			password
		)
		// Valid email, valid password and password confirmation match password.
		if (isValidEmail && isValidPassword && isValidPasswordConfirmation) {
			createUserWithEmailAndPassword(email!!, password!!)
		}
	}

	/**
	 * Creates a new user account associated with the specified email address and password.
	 */
	override fun createUserWithEmailAndPassword(email: String, password: String) {
		view?.showLoading(Constants.SHOW_LOADING)
		createUserWithEmailAndPasswordInteractor.execute(
			createUserWithEmailAndPasswordInteractorListener,
			email,
			password
		)
	}

	/**
	 * [ValidateEmailInteractor.Listener] implementation.
	 */
	private val validateEmailInteractorListener = object : ValidateEmailInteractor.Listener {
		override fun onValidEmail() {
			isValidEmail = true
		}

		override fun onInvalidEmail(errorMsg: String) {
			isValidEmail = false
			view?.setEmailError(errorMsg)
		}
	}

	/**
	 * [ValidatePasswordInteractor.Listener] implementation.
	 */
	private val validatePasswordInteractorListener = object : ValidatePasswordInteractor.Listener {
		override fun onValidPassword() {
			isValidPassword = true
		}

		override fun onInvalidPassword(errorMsg: String) {
			isValidPassword = false
			view?.setPasswordError(errorMsg)
		}
	}

	/**
	 * [ValidatePasswordConfirmationInteractor.Listener] implementation.
	 */
	private val validatePasswordConfirmationInteractorListener =
		object : ValidatePasswordConfirmationInteractor.Listener {
			override fun onValidPasswordConfirmation() {
				isValidPasswordConfirmation = true
			}

			override fun onInvalidPasswordConfirmation(errorMsg: String) {
				isValidPasswordConfirmation = false
				view?.setPasswordConfirmationError(errorMsg)
			}
		}

	/**
	 * [CreateUserWithEmailAndPasswordInteractor.Listener] implementation.
	 */
	private val createUserWithEmailAndPasswordInteractorListener =
		object : CreateUserWithEmailAndPasswordInteractor.Listener {
			override fun onSuccess(firebaseUser: FirebaseUser) {
				view?.showLoading(Constants.HIDE_LOADING)
				view?.onFirebaseUserCreated()
			}

			override fun onFail(errorCode: String) {
				view?.showLoading(Constants.HIDE_LOADING)
				if (!errorCode.isEmpty()) {
					view?.showUserCreationErrorDialog(errorCode)
				}
			}
		}

}