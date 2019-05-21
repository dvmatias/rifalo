package com.bluespark.raffleit.screens.signin

import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.bluespark.raffleit.common.utils.managers.FirebaseSignInGoogleManager

/**
 * Interactor.
 *
 * This interactor exposes his [execute] method in order to start, finish and give results
 * when an user tries to sign in with a Google Account.
 *
 * @author matias.delv.dom@gmail.com
 */
class SignInWithGoogleInteractor(
	private var activity: AppCompatActivity,
	private var firebaseSignInGoogleManager: FirebaseSignInGoogleManager
) {

	private lateinit var listener: Listener

	companion object {
		@Suppress("unused")
		private val TAG = SignInWithGoogleInteractor::class.java.simpleName
	}

	/**
	 * Interface to be implemented in order to catch [execute] method results.
	 * This interface must be implemented and passed as param into [execute] method.
	 */
	interface Listener {
		fun onSuccess()
		fun onFail(errorCode: String)
	}

	/**
	 * TODO documentation.
	 */
	fun execute(listener: Listener) {
		if (activity !is BaseActivityImpl)
			throw IllegalAccessException("Calling activity $activity must implement of BaseActivityImpl.")

		this.listener = listener
		firebaseSignInGoogleManager.signIn(
			signInGoogleManagerListener,
			activity as BaseActivityImpl
		)
	}

	/**
	 * [FirebaseSignInGoogleManager.Listener] implementation.
	 */
	private val signInGoogleManagerListener: FirebaseSignInGoogleManager.Listener =
		object : FirebaseSignInGoogleManager.Listener {
			override fun onGoogleSignInSuccess() {
				listener.onSuccess()
			}

			override fun onGoogleSignInFail(errorCode: String) {
				listener.onFail("")
				Log.d(TAG, "onGoogleSignInFail():Google sign in fail.")
			}
		}

}