package com.bluespark.rifalo.common.injection.presentation

import com.bluespark.rifalo.screens.splash.SplashActivity
import dagger.Subcomponent

@Subcomponent(
	modules = [
		PresentationModule::class,
		PresenterModule::class,
		InteractorModule::class
	]
)
interface PresentationComponent {

	fun inject(activity: SplashActivity)

}