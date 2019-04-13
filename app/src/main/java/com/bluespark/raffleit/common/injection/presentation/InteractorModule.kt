package com.bluespark.raffleit.common.injection.presentation

import android.content.Context
import com.bluespark.raffleit.common.utils.managers.InternetConnectivityManager
import com.bluespark.raffleit.screens.splash.SplashCheckCredentialsInteractor
import com.bluespark.raffleit.screens.splash.SplashCheckNetworkInteractor
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

	@Provides
	fun getSplashCheckNetworkInteractor(internetConnectivityManager: InternetConnectivityManager)
			: SplashCheckNetworkInteractor =
		SplashCheckNetworkInteractor(internetConnectivityManager)

	@Provides
	fun getSplashCheckCredentialsInteractor(context: Context): SplashCheckCredentialsInteractor =
		SplashCheckCredentialsInteractor(context)

}