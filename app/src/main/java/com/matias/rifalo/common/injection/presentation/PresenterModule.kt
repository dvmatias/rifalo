package com.matias.rifalo.common.injection.presentation

import com.matias.rifalo.common.mvp.BaseView
import com.matias.rifalo.screens.splash.SplashCheckNetworkInteractor
import com.matias.rifalo.screens.splash.SplashContract
import com.matias.rifalo.screens.splash.SplashPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

	@Provides
	fun getSplashPresenterImpl(view: BaseView, checkNetworkInteractor: SplashCheckNetworkInteractor): SplashPresenterImpl =
		SplashPresenterImpl(view as SplashContract.View, checkNetworkInteractor)

}