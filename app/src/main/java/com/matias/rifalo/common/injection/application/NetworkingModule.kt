package com.matias.rifalo.common.injection.application

import android.content.Context
import com.matias.rifalo.common.utils.managers.InternetConnectivityManager
import dagger.Module
import dagger.Provides

@Module
class NetworkingModule(val context: Context) {

	@Provides
	fun getInternetConnectivityManager(): InternetConnectivityManager =
		InternetConnectivityManager(context)

}