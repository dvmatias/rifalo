package com.bluespark.raffleit.common.injection.application

import android.content.Context
import com.bluespark.raffleit.common.utils.managers.InternetConnectivityManager
import dagger.Module
import dagger.Provides

@Module
class NetworkingModule(val context: Context) {

	@Provides
	fun getInternetConnectivityManager(): InternetConnectivityManager =
		InternetConnectivityManager(context)

}