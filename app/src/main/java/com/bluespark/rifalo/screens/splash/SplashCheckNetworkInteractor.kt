package com.bluespark.rifalo.screens.splash

import android.util.Log
import com.bluespark.rifalo.common.utils.managers.InternetConnectivityManager

class SplashCheckNetworkInteractor(var internetConnectivityManager: InternetConnectivityManager) {

	interface Listener {
		fun onInternetConnected()
		fun onInternetNotConnected()
	}

	fun execute(listener: SplashCheckNetworkInteractor.Listener) {
		Log.d(TAG, "MABEL execute()")
		val isInternetConnected = internetConnectivityManager.checkInternetConnectionStatus()
		when(isInternetConnected) {
			true -> listener.onInternetConnected()
			false -> listener.onInternetNotConnected()
		}
	}

	companion object {
		private val TAG = SplashCheckNetworkInteractor::class.java.simpleName
	}

}