package com.bluespark.raffleit.screens.signup.fragments.userinfo

import android.content.Context
import com.bluespark.raffleit.R

class ValidatePasswordConfirmationInteractor(private var context: Context) {

	interface Listener {
		fun onValidPasswordConfirmation()
		fun onInvalidPasswordConfirmation(errorMsg: String)
	}

	fun execute(
		listener: ValidatePasswordConfirmationInteractor.Listener,
		passwordConfirmation: String?,
		password: String?
	) {
		if (passwordConfirmation.isNullOrEmpty()) {
			listener.onInvalidPasswordConfirmation(
				context.getString(R.string.msg_empty_password_confirmation_error))
		} else {
			listener.run {
				if (passwordConfirmation.equals(password, false))
					onValidPasswordConfirmation()
				else
					onInvalidPasswordConfirmation(context.getString(
						R.string.msg_invalid_password_confirmation_error))
			}
		}
	}
}