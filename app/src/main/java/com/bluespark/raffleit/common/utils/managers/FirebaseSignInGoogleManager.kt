package com.bluespark.raffleit.common.utils.managers

import android.util.Log
import com.bluespark.raffleit.common.Constants
import com.bluespark.raffleit.common.mvp.BaseActivityImpl
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseSignInGoogleManager(
	private var firebaseAuth: FirebaseAuth,
	private var googleSignInClient: GoogleSignInClient
) {

	companion object {
		private val TAG = FirebaseSignInGoogleManager::class.java.simpleName
	}

	interface Listener {
		fun onGoogleSignInSuccess()
		fun onGoogleSignInFail()
	}

	fun signIn(activity: BaseActivityImpl) {
		if (activity !is Listener) {
			throw ClassCastException("The $activity class shall implement FirebaseSignInGoogleManager.Listener")
		}
		activity.startActivityForResult(
			googleSignInClient.signInIntent,
			Constants.RC_SIGN_IN_GOOGLE
		)
	}

	fun handleSignInGoogleResult(
		activity: BaseActivityImpl,
		completedTask: Task<GoogleSignInAccount>
	) {
		try {
			val account = completedTask.getResult(ApiException::class.java)
			firebaseSignInWithGoogle(activity, account)
			Log.w(TAG, "signInResult:success")
		} catch (e: ApiException) {
			// The ApiException status code indicates the detailed failure reason.
			// Please refer to the GoogleSignInStatusCodes class reference for more information.
			Log.w(TAG, "signInResult:failed code=" + e.statusCode)
			// TODO Manage result.
		}
	}

	private fun firebaseSignInWithGoogle(
		activity: BaseActivityImpl,
		account: GoogleSignInAccount?
	) {
		val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
		firebaseAuth.signInWithCredential(credential)
			.addOnCompleteListener(
				activity
			) { task ->
				if (task.isSuccessful) {
					// TODO Store user data.
					(activity as Listener).onGoogleSignInSuccess()
				} else {
					// If sign in fails, display a message to the user.
					Log.w(TAG, "signInWithCredential:failure", task.exception)
					// TODO Manage result.
					(activity as Listener).onGoogleSignInFail()
				}
			}
	}
}