package com.bluespark.raffleit.common.injection.presentation

import android.content.Context
import com.bluespark.raffleit.common.utils.managers.InternetConnectivityManager
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidateEmailInteractor
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidatePasswordConfirmationInteractor
import com.bluespark.raffleit.screens.signup.fragments.userinfo.ValidatePasswordInteractor
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

	@Provides
	fun getValidateEmailInteractor(context: Context): ValidateEmailInteractor =
		ValidateEmailInteractor(context)

	@Provides
	fun getValidatePasswordInteractor(context: Context): ValidatePasswordInteractor =
		ValidatePasswordInteractor(context)

	@Provides
	fun getValidatePasswordConfirmationInteractor(context: Context): ValidatePasswordConfirmationInteractor =
		ValidatePasswordConfirmationInteractor(context)

}