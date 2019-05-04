package com.bluespark.raffleit.screens.signup.fragments.userinfo

import android.content.Context
import com.bluespark.raffleit.R
import com.bluespark.raffleit.common.Constants
import java.util.regex.Pattern

class ValidateEmailInteractor(private var context: Context) {

	interface Listener {
		fun onValidEmail()
		fun onInvalidEmail(errorMsg: String)
	}

	fun execute(listener: ValidateEmailInteractor.Listener, email: String?) {
		if (email.isNullOrEmpty()) {
			listener.onInvalidEmail(context.getString(R.string.msg_empty_email_error))
		} else {
			val matcher =
				Pattern.compile(Constants.REGEX_EMAIL_PATTERN_VALIDATOR, Pattern.CASE_INSENSITIVE)
					.matcher(email)

			listener.run {
				if (matcher.matches()) onValidEmail() else onInvalidEmail(
					context.getString(R.string.msg_invalid_email_error)
				)
			}
		}
	}
}