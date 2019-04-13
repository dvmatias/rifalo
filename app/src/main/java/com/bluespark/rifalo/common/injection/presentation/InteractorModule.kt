package com.bluespark.rifalo.common.injection.presentation

import com.bluespark.rifalo.common.utils.managers.InternetConnectivityManager
import com.bluespark.rifalo.screens.splash.SplashCheckCredentialsInteractor
import com.bluespark.rifalo.screens.splash.SplashCheckNetworkInteractor
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

	@Provides
	fun getSplashCheckNetworkInteractor(internetConnectivityManager: InternetConnectivityManager)
			: SplashCheckNetworkInteractor =
		SplashCheckNetworkInteractor(internetConnectivityManager)

	@Provides
	fun getSplashCheckCredentialsInteractor(): SplashCheckCredentialsInteractor =
		SplashCheckCredentialsInteractor()

}