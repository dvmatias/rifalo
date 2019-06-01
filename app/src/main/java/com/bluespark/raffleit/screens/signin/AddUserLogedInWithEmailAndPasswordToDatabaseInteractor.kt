package com.bluespark.raffleit.screens.signin

import android.app.Activity
import android.net.Uri
import android.support.annotation.UiThread
import android.util.Log
import com.bluespark.raffleit.common.model.objects.UserFirebase
import com.bluespark.raffleit.screens.signin.AddUserLogedInWithEmailAndPasswordToDatabaseInteractor.Listener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Interactor
 *
 * This interactor exposes the method [execute] to:
 *   1) Check if the userId entry exists on "user" Database.
 *      a) If the user exists do nothing.
 *      b) If the user do not exists, create a user entry using [UserFirebase] model object.
 *   2) Report fail or success trough [Listener] interface methods if not null.
 *
 * @author matias.delv.dom@gmail.com
 */
class AddUserLogedInWithEmailAndPasswordToDatabaseInteractor(
	private var activity: Activity,
	var firebaseAuth: FirebaseAuth
) {

	private var listener: Listener? = null
	private lateinit var userId: String

	companion object {
		private val TAG =
			AddUserLogedInWithEmailAndPasswordToDatabaseInteractor::class.java.simpleName
	}

	/**
	 * This interface must be implemented and be passed as instance into [execute] method.
	 * If this instance is null, no callback will be triggered on tasks completion.
	 */
	interface Listener {
		fun onSuccess()
		fun onFail(errorCode: String)
	}

	/**
	 * Execute method.
	 *
	 * @param listener
	 * @param userId
	 */
	@UiThread
	fun execute(
		listener: AddUserLogedInWithEmailAndPasswordToDatabaseInteractor.Listener?,
		userId: String
	) {
		this.listener = listener
		this.userId = userId

		FirebaseDatabase.getInstance().reference.child("users").child(userId)
			.addValueEventListener(valueEventListener)

	}

	/**
	 * Listener to check if the user exists on "users" Firebase Database. If the user do not
	 * exists, try to add it. If the user already exists do nothing.
	 */
	private val valueEventListener: ValueEventListener = object : ValueEventListener {
		override fun onCancelled(p0: DatabaseError) {
			// Error trying to verify if the user exists on Database.
			Log.d(TAG, "Check if user exists on DB error: ${p0.message}")
			listener?.onFail("ERROR_DB_1")
		}

		override fun onDataChange(p0: DataSnapshot) {
			// If the user do not exists in database
			if (!p0.exists()) {
				// Remove listener to prevent double call when the user is added to Database.
				FirebaseDatabase.getInstance().reference.child("users").child(userId)
					.removeEventListener(this)
				// Add user to "users" Database
				FirebaseDatabase.getInstance().reference.child("users").child(userId)
					.setValue(getUserFirebase())
					.addOnCompleteListener(activity) { task ->
						if (task.isSuccessful) {
							Log.d(TAG, "success:$userId added to Database")
							listener?.onSuccess()
						} else {
							Log.d(TAG, "fail:$userId not added to Database")
							listener?.onFail("ERROR_DB_2")
						}
					}
			} else {
				Log.d(TAG, "neutral:$userId already exists in DB")
				listener?.onSuccess()
			}
		}
	}

	/**
	 * Create a user object model to create an entry on "user" Database.
	 */
	private fun getUserFirebase(): UserFirebase {
		val user = firebaseAuth.currentUser
		user!!
		return UserFirebase(
			user.uid,
			UserFirebase.Person(
				if (user.displayName.isNullOrEmpty()) "_displayName" else user.displayName!!,
				"_birthDate",
				"_country"
			),
			"_facebook",
			getEmail(user),
			getPhoneNumber(user),
			getPhotoUrl(user)
		)
	}

	private fun getEmail(firebaseUser: FirebaseUser): String {
		var email = "_email"
		for (providerData in firebaseUser.providerData)
			if (!providerData.email.isNullOrEmpty()) email = providerData.email!!

		return email
	}

	private fun getPhotoUrl(firebaseUser: FirebaseUser): Uri {
		var photoUrl = Uri.parse("_photoUrl")
		for (providerData in firebaseUser.providerData)
			if (!providerData.email.isNullOrEmpty()) photoUrl = providerData.photoUrl

		return photoUrl
	}

	private fun getPhoneNumber(firebaseUser: FirebaseUser): String {
		var phoneNumber = "_phoneNumber"
		for (providerData in firebaseUser.providerData)
			if (!providerData.email.isNullOrEmpty()) phoneNumber = providerData.phoneNumber!!

		return phoneNumber
	}

}