package com.matias.rifalo.common.injection.presentation

import com.matias.rifalo.common.utils.managers.InternetConnectivityManager
import com.matias.rifalo.screens.splash.SplashCheckNetworkInteractor
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

	@Provides
	fun getSplashCheckNetworkInteractor(internetConnectivityManager: InternetConnectivityManager)
			: SplashCheckNetworkInteractor =
		SplashCheckNetworkInteractor(internetConnectivityManager)

}