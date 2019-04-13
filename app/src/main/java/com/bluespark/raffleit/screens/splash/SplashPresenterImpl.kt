package com.bluespark.raffleit.screens.splash

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
			actionCheckCredentials()
		}

		override fun onInternetNotConnected() {
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