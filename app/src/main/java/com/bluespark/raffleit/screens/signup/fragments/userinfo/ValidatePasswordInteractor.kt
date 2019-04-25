package com.bluespark.raffleit.screens.signup.fragments.userinfo

import android.content.Context
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.Constants
import java.util.regex.Pattern

class ValidatePasswordInteractor(private var context: Context) {

	interface Listener {
		fun onValidPassword()
		fun onInvalidPassword(errorMsg: String)
	}

	fun execute(listener: ValidatePasswordInteractor.Listener, password: String?) {
		if (password.isNullOrEmpty()) {
			listener.onInvalidPassword(context.getString(R.string.msg_empty_password_error))
		} else {
			val matcher = Pattern.compile(
				Constants.REGEX_PASSWORD_PATTERN_VALIDATOR,
				Pattern.CASE_INSENSITIVE
			)
				.matcher(password)

			listener.run {
				if (matcher.matches()) onValidPassword() else onInvalidPassword(
					context.getString(R.string.msg_invalid_password_error)
				)
			}
		}
	}
}