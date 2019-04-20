package com.bluespark.raffleit.common.model.`object`

import android.net.Uri

class UserFirebase(
	var uid: String,
	var providerId: String,
	var displayName: String?,
	var email: String?,
	var photoUrl: Uri?,
	var phoneNumber: String?,
	var isEmailVerified: Boolean
)