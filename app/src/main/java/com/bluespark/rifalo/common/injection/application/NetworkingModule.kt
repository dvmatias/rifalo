package com.bluespark.rifalo.common.injection.application

import android.content.Context
import com.bluespark.rifalo.common.utils.managers.InternetConnectivityManager
import dagger.Module
import dagger.Provides

@Module
class NetworkingModule(val context: Context) {

	@Provides
	fun getInternetConnectivityManager(): InternetConnectivityManager =
		InternetConnectivityManager(context)

}