package com.bluespark.raffleit.common.model.objects

/**
 * Model - Object to create a User entry in "user" Database.
 */
class UserFirebase(
	var userId: String,
	var person: Person,
	var google: String,
	var facebook: String,
	var email: String,
	var phone: String,
	var photoUrl: String,
	var providers: MutableList<String>
) {

	class Person(
		var name: String,
		var lastName: String,
		var birthDate: String,
		var country: String
	)
}