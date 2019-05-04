package com.bluespark.raffleit.screens.splash

import android.os.Handler
import android.util.Log
import com.bluespark.raffleit.common.utils.managers.InternetConnectivityManager

class SplashCheckNetworkInteractor(var internetConnectivityManager: InternetConnectivityManager) {

	interface Listener {
		fun onInternetConnected()
		fun onInternetNotConnected()
	}

	fun execute(listener: SplashCheckNetworkInteractor.Listener) {
		Handler().postDelayed({
			val isInternetConnected = internetConnectivityManager.checkInternetConnectionStatus()
			when(isInternetConnected) {
				true -> listener.onInternetConnected()
				false -> listener.onInternetNotConnected()
			}
		}, 1500)
	}

	companion object {
		private val TAG = SplashCheckNetworkInteractor::class.java.simpleName
	}

}