package com.bluespark.raffleit.screens.signup.interactors

import android.app.Activity
import android.os.Handler
import com.bluespark.raffleit.common.model.objects.SignUpUser
import com.bluespark.raffleit.common.utils.managers.FirebaseUserManager
import com.google.firebase.auth.FirebaseUser


class SendVerificationEmailInteractor(
	private var activity: Activity,
	private var firebaseUserManager: FirebaseUserManager
) {

	var listener: SendVerificationEmailInteractor.Listener? = null

	interface Listener {
		fun onSuccess()
		fun onFail()
	}

	fun execute(listener: Listener) {
		this.listener = listener
		Handler().postDelayed({
			firebaseUserManager.sendEmailVerification(
				sendEmailVerification,
				activity,
				null
			)
		}, 500)


	}

	/**
	 * [FirebaseUserManager.Listener.SendEmailVerification] implementation.
	 */
	private val sendEmailVerification =
		object : FirebaseUserManager.Listener.SendEmailVerification {
			override fun onEmailSendSuccess() {
				listener?.onSuccess()
			}

			override fun onEmailSendFail() {
				listener?.onFail()
			}
		}

	companion object {
		@Suppress("unused")
		private val TAG = SendVerificationEmailInteractor::class.java.simpleName
	}

}