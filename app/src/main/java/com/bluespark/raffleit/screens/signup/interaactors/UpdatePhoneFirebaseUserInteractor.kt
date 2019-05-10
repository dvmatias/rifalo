package com.bluespark.raffleit.screens.signup.interaactors

import android.app.Activity
import android.os.Handler
import com.bluespark.raffleit.common.utils.managers.FirebaseUserManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential


class UpdatePhoneFirebaseUserInteractor(
	private var activity: Activity,
	private var firebaseUserManager: FirebaseUserManager
) {

	var listener: Listener? = null

	companion object {
		@Suppress("unused")
		private val TAG = UpdatePhoneFirebaseUserInteractor::class.java.simpleName
	}

	interface Listener {
		fun onSuccess()
		fun onFail()
	}

	fun execute(
		listener: Listener,
		firebaseUser: FirebaseUser,
		phoneAuthCredential: PhoneAuthCredential
	) {
		this.listener = listener
		Handler().postDelayed({
			firebaseUserManager.updatePhoneNumber(
				updatePhoneNumberListener,
				activity,
				firebaseUser,
				phoneAuthCredential
			)
		}, 500)


	}

	/**
	 * [FirebaseUserManager.Listener.UpdatePhoneNumber] implementation.
	 */
	private val updatePhoneNumberListener =
		object : FirebaseUserManager.Listener.UpdatePhoneNumber {
			override fun onPhoneUpdateSuccess() {
				listener?.onSuccess()
			}

			override fun onPhoneUpdateFail(errorCode: String) {
				listener?.onFail()
			}

		}

}