package com.bluespark.raffleit.screens.splash

import android.util.Log
import com.bluespark.raffleit.common.mvp.BasePresenterImpl

class SplashPresenterImpl(
	view: SplashContract.View?,
	private var checkNetworkInteractor: SplashCheckNetworkInteractor,
	private var checkCredentialsInteractor: SplashCheckCredentialsInteractor
) : BasePresenterImpl<SplashContract.View>(),
	SplashContract.Presenter {

	init {
		super.bind(view)
	}

	/**
	 * [SplashContract.Presenter] interface implementation.
	 */

	override fun actionCheckInternetConnection() {
		checkNetworkInteractor.execute(networkListener)
	}

	override fun actionCheckCredentials() {
		checkCredentialsInteractor.execute(credentialsListener)
	}

	/**
	 * [SplashCheckNetworkInteractor.Listener] interface implementation.
	 */
	private var networkListener = object : SplashCheckNetworkInteractor.Listener {
		override fun onInternetConnected() {
			Log.d(SplashPresenterImpl::class.java.simpleName, "MABEL Internet Connected!")
			actionCheckCredentials()
		}

		override fun onInternetNotConnected() {
			Log.d(SplashPresenterImpl::class.java.simpleName, "MABEL Internet Not Connected!")
			view?.viewShowNoInternetDialog()
		}

	}

	/**
	 * [SplashCheckCredentialsInteractor.Listener] interface implementation.
	 */
	private var credentialsListener = object : SplashCheckCredentialsInteractor.Listener {
		override fun onUserSignedIn() {
			view?.flowGoToMainScreen()
		}

		override fun onUserNotLoggedIn() {
			view?.flowGoToAuthScreen()
		}

	}

}