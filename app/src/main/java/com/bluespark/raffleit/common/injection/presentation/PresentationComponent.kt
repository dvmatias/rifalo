package com.bluespark.raffleit.common.injection.presentation

import com.bluespark.raffleit.screens.main.MainActivity
import com.bluespark.raffleit.screens.signin.SignInActivity
import com.bluespark.raffleit.screens.signup.SignUpActivity
import com.bluespark.raffleit.screens.signup.fragments.userinfo.SignUpUserInfoFragment
import com.bluespark.raffleit.screens.splash.SplashActivity
import dagger.Subcomponent

@Subcomponent(
	modules = [
		PresentationModule::class,
		PresenterModule::class,
		InteractorModule::class,
		AdaptersModule:: class
	]
)
interface PresentationComponent {

	fun inject(activity: SplashActivity)
	fun inject(activity: SignInActivity)
	fun inject(activity: SignUpActivity)
	fun inject(activity: SignUpUserInfoFragment)
	fun inject(activity: MainActivity)

}