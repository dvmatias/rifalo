package com.bluespark.raffleit.common.injection.presentation

import com.bluespark.raffleit.screens.singin.SignInActivity
import com.bluespark.raffleit.screens.splash.SplashActivity
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
	fun inject(activity: SignInActivity)

}