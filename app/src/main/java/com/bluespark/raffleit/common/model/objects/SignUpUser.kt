package com.bluespark.raffleit.common.model.objects

data class SignUpUser(
	var email: String,
	var password: String,
	var phone: Phone?
) {

	inner class Phone(
		var number: String,
		var regionCode: String
	)
}