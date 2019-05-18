package com.bluespark.raffleit.screens.signin

import android.app.Activity
import android.support.annotation.UiThread
import android.util.Log
import com.bluespark.raffleit.common.model.objects.UserFirebase
import com.google.firebase.auth.FirebaseAuth
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
	private var flagTaskCompleted = false

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

		// First, check if the userId is already a node in "users" Database.
		FirebaseDatabase.getInstance().reference.child("users").child(userId)
			.addValueEventListener(object : ValueEventListener {
				override fun onCancelled(p0: DatabaseError) {
					// Error trying to verify if the user exists on Database.
					Log.d(TAG, "Check if user exists on DB error: ${p0.message}")
					listener?.onFail("ERROR_DB_1")
				}

				override fun onDataChange(p0: DataSnapshot) {
					if (!flagTaskCompleted) {
						// If the user do not exists in database
						if (!p0.exists()) {
							// This avoid a second iteration when the user has been added to
							// the Database.
							flagTaskCompleted = true
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

			})

	}

	companion object {
		private val TAG =
			AddUserLogedInWithEmailAndPasswordToDatabaseInteractor::class.java.simpleName
	}

	/**
	 * Create a user object model to create an entry on "user" Database.
	 */
	private fun getUserFirebase(): UserFirebase {
		val user = firebaseAuth.currentUser
		user!!
		return UserFirebase(
			user.uid,
			UserFirebase.Person("_name", "_lastName", "_birthDate", "_country"),
			"_google",
			"_facebook",
			if (user.email.isNullOrEmpty()) "_email" else user.email!!,
			if (user.phoneNumber.isNullOrEmpty()) "_phoneNumber" else user.phoneNumber!!,
			"_photoUrl",
			user.providers!!
		)
	}

}