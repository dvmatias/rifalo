package com.bluespark.rifalo.common.injection.presentation

import com.bluespark.rifalo.common.mvp.BaseView
import com.bluespark.rifalo.screens.splash.SplashCheckCredentialsInteractor
import com.bluespark.rifalo.screens.splash.SplashCheckNetworkInteractor
import com.bluespark.rifalo.screens.splash.SplashContract
import com.bluespark.rifalo.screens.splash.SplashPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

	@Provides
	fun getSplashPresenterImpl(
		view: BaseView,
		checkNetworkInteractor: SplashCheckNetworkInteractor,
		checkCredentialsInteractor: SplashCheckCredentialsInteractor
	): SplashPresenterImpl =
		SplashPresenterImpl(
			view as SplashContract.View,
			checkNetworkInteractor,
			checkCredentialsInteractor
		)

}