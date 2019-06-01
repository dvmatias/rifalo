package com.bluespark.raffleit.common.model.objects

import android.net.Uri

/**
 * Model - Object to create a User entry in "user" Database.
 */
class UserFirebase(
	var userId: String,
	var person: Person,
	var facebook: String,
	var email: String,
	var phone: String,
	var photoUrl: Uri
) {

	class Person(
		var displayName: String,
		var birthDate: String,
		var country: String
	)
}