package com.bluespark.raffleit.common.utils.managers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class InternetConnectivityManager(private var context: Context) {

	fun checkInternetConnectionStatus(): Boolean {
		val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

		return activeNetwork?.isConnected == true
	}

}