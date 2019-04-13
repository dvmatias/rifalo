package com.matias.rifalo.common.injection.presentation

import com.matias.rifalo.screens.splash.SplashActivity
import dagger.Subcomponent

@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {

	fun inject(activity: SplashActivity)

}