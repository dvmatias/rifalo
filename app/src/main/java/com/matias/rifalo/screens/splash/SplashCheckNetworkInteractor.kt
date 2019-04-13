package com.matias.rifalo.screens.splash

import com.matias.rifalo.common.utils.managers.InternetConnectivityManager

class SplashCheckNetworkInteractor(var internetConnectivityManager: InternetConnectivityManager) {

	private lateinit var listener: Listener

	interface Listener {
		fun onInternetConnected()
		fun onInternetNotConnected()
	}

	fun execute(listener: SplashCheckNetworkInteractor.Listener) {
		val isInternetConnected = internetConnectivityManager.checkInternetConnectionStatus()
		when(isInternetConnected) {
			true -> listener.onInternetConnected()
			false -> listener.onInternetNotConnected()
		}
	}

}