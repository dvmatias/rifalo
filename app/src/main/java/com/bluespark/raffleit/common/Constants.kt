package com.bluespark.raffleit.common

class Constants {

	companion object {
		const val SHOW_LOADING = true
		const val HIDE_LOADING = false
		const val DELAY_CHECK_SYSTEM_UI_VISIBILITY = 1300L
		const val RC_SIGN_IN_GOOGLE = 131
		const val REGEX_EMAIL_PATTERN_VALIDATOR =
			("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
					+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
					+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
					+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
					+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
					+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
		const val REGEX_PASSWORD_PATTERN_VALIDATOR =
			("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$")
		const val EXTRA_KEY_SELECTED_COUNTRY = "selected_country"
		const val EXTRA_KEY_COUNTRY_LIST = "country_list"
		const val REQUEST_CODE_CHOOSE_COUNTRY_ACTIVITY = 1543
	}

}